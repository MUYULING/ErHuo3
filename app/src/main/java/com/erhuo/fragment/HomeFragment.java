package com.erhuo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.erhuo.adapter.ComHomeAdapter;
import com.erhuo.erhuo3.MainActivity;
import com.erhuo.erhuo3.R;
import com.erhuo.util.CommodityHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Gary on 2017/11/25.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private CommodityHome[] coms = {new CommodityHome("apple", R.drawable.ida), new CommodityHome("banana", R.drawable.ida)};
    private List<CommodityHome> comList = new ArrayList<>();
    private ComHomeAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.home_add);
        MainActivity activity = (MainActivity) getActivity();
        for(int i = 0; i < coms.length; i++){
            comList.add(coms[i]);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new ComHomeAdapter(comList);
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                Toast.makeText(activity, "hahha", Toast.LENGTH_SHORT).show();
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_home);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity activity = (MainActivity) getActivity();
                Toast.makeText(activity, "hahha", Toast.LENGTH_SHORT).show();
                //refreshItem();
                for(int i = coms.length - 1; i >= 0; i--){
                    comList.add(coms[i]);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        Button button1 = (Button) view.findViewById(R.id.refresh);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getItem();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void refreshItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainActivity activity = (MainActivity) getActivity();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = coms.length - 1; i >= 0; i--){
                            comList.add(coms[i]);
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void getItem() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://123.207.161.20/suntong/mainPage.php?pages=1")
                .build();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        parseJSONWithJSONObject(responseData);
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String user_name = jsonObject.getString("user_name");
                String type = jsonObject.getString("type");
                Log.d("WebWebWeb", "name: " + name);
                Log.d("WebWebWeb", "user_name: " + user_name);
                Log.d("WebWebWeb", "type: " + type);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

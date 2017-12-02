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
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Gary on 2017/11/25.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private List<CommodityHome> comList = new ArrayList<>();
    private ComHomeAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;

    private List<CommodityHome> tmpList = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.home_add);
        MainActivity activity = (MainActivity) getActivity();
        if(comList.size() == 0){
            refreshItem(true);
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
                refreshItem(false);
            }
        });
        return view;
    }

    private void refreshItem(final boolean isFirst){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getItem();
                MainActivity activity = (MainActivity) getActivity();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        comList.clear();
                        comList.addAll(tmpList);
                        adapter.notifyDataSetChanged();
                        if(!isFirst){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        }).start();
    }

    private void getItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/suntong/mainPage.php?pages=30")
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity activity = (MainActivity) getActivity();
                            Toast.makeText(activity, "无网络连接", Toast.LENGTH_LONG).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            tmpList.clear();
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String user_name = jsonObject.getString("user_name");
                int comId = jsonObject.getInt("com_id");
                String type = jsonObject.getString("type");
                String images = jsonObject.getString("images");
                double price = jsonObject.getDouble("price");
                String upTime = jsonObject.getString("up_time");
                String downTime = jsonObject.getString("down_time");
                String description = jsonObject.getString("description");
                CommodityHome msg = new CommodityHome(user_name, name, comId, price, type, description, images, upTime, downTime);
                tmpList.add(msg);
                Log.d("WebWebWeb", "name: " + name);
                Log.d("WebWebWeb", "user_name: " + user_name);
                Log.d("WebWebWeb", "type: " + type);
                Log.d("WebWebWeb", "com_id：" + comId);
                Log.d("WebWebWeb", "price: " + price);
                Log.d("WebWebWeb", "up_time: " + upTime);
                Log.d("WebWebWeb", "down_time: " + downTime);
                Log.d("WebWebWeb", "description: " + description);
                Log.d("WebWebWeb", "image: " + images);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

package com.erhuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erhuo.adapter.ComHomeAdapter;
import com.erhuo.erhuo3.MainActivity;
import com.erhuo.erhuo3.R;
import com.erhuo.erhuo3.SearchActivity;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class SearchFragment extends Fragment {

    private List<CommodityHome> comList = new ArrayList<>();
    private ComHomeAdapter adapter;


    private View view;
    private ImageView imageview;
    private TextView textview;

    private String key;
    private List<CommodityHome> tmpList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        imageview = (ImageView) view.findViewById(R.id.search);
        textview = (TextView) view.findViewById(R.id.search_text);
        imageview.setImageResource(R.drawable.ic_search);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        MainActivity activity2 = (MainActivity) getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity2));
        adapter = new ComHomeAdapter(comList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    textview.setText(returnedData);
                    Log.d("Search", returnedData);
                    key = returnedData;
                }
                break;
            default:
                break;
        }
        if(key != null && key != ""){
            getSearchResult();
        }
    }

    private void getSearchResult(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/suntong/searchCom.php?search=" + key)
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainActivity activity = (MainActivity) getActivity();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        comList.clear();
                        comList.addAll(tmpList);
                        adapter.notifyDataSetChanged();
                    }
                });
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

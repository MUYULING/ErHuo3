package com.erhuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erhuo.activitiy_erhuo.CollectionActivity;
import com.erhuo.activitiy_erhuo.Me;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.adapter.SellingCollAdapter;
import com.erhuo.entity.CommodityHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Gary on 2017/12/9.
 */

public class RequiringCollFragment extends Fragment {

    private List<CommodityHome> commodityHomeList = new ArrayList<>();
    private SellingCollAdapter adapter;
    private View view;
    private List<CommodityHome> tmpList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_selling, container, false);
        CollectionActivity activity = (CollectionActivity) getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new SellingCollAdapter(commodityHomeList, activity, 1);
        recyclerView.setAdapter(adapter);
        if(commodityHomeList.size() == 0){
            getItem();
        }
        return view;
    }

    private void getItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    CollectionActivity activity = (CollectionActivity) getActivity();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_name", activity.getIntent().getStringExtra("user_name"))
                            .add("req", Integer.toString(1))
                            .build();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/sl/qa_coll.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            commodityHomeList.clear();
                            commodityHomeList.addAll(tmpList);
                            adapter.notifyDataSetChanged();

                        }
                    });
                } catch (IOException e) {
                    Me activity = (Me) getActivity();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Me activity = (Me) getActivity();
                            Toast.makeText(activity, "无网络连接", Toast.LENGTH_SHORT).show();
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
                String user_name = jsonObject.getString("owner_name");
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

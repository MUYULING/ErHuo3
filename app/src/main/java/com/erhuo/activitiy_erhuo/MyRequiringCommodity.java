package com.erhuo.activitiy_erhuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.erhuo.adapter.Room2RecycleAdapter;
import com.erhuo.entity.CommodityDetail;

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
 * Created by mac on 2017/12/2.
 */

public class MyRequiringCommodity extends AppCompatActivity {

    private List<CommodityDetail> commodityDetailList = new ArrayList<>();
    private Room2RecycleAdapter adapter;
    private View view;
    private List<CommodityDetail> tmpList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_requiringcom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_myCommodity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.room_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Room2RecycleAdapter(commodityDetailList, this, 1);
        recyclerView.setAdapter(adapter);
        if(commodityDetailList.size() == 0){
            getItem();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_my_requirecommodity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_require_commodity:
                Intent intent = new Intent(this, RequireCommodityEdit.class);
                intent.putExtra("user_name", this.getIntent().getStringExtra("user_name"));
                startActivityForResult(intent, 2);
                break;
            default:
        }
        return true;
    }

    public void getItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_name", getIntent().getStringExtra("user_name"))
                            .build();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/zhangbo/req_commodity.php/crud.php")
                            .post(requestBody)
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            commodityDetailList.clear();
                            commodityDetailList.addAll(tmpList);
                            adapter.notifyDataSetChanged();

                        }
                    });
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyRequiringCommodity.this, "无网络连接", Toast.LENGTH_SHORT).show();
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
                int detailID = jsonObject.getInt("detail_id");
                String state = jsonObject.getString("state");
                CommodityDetail msg = new CommodityDetail(user_name, name, comId, price, type, description, images, upTime, downTime, detailID, state);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("FRESH", "fresh");
        getItem();
    }
}

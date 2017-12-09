package com.erhuo.activitiy_erhuo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.erhuo.entity.CommodityDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Gary on 2017/12/3.
 */

public class SellingDetail extends AppCompatActivity {

    private ConvenientBanner convenientBanner;
    private List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_detail);
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, images)
                .setPointViewVisible(true)
                .setPageIndicator(new int[]{R.mipmap.index_white,R.mipmap.index_red})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .startTurning(2000)
                .setManualPageable(true);
        LinearLayout fav = (LinearLayout) findViewById(R.id.good_collection);
        LinearLayout cont = (LinearLayout) findViewById(R.id.good_contact);
        LinearLayout buyl = (LinearLayout) findViewById(R.id.good_buy);
        TextView contact = (TextView) findViewById(R.id.contact);
        TextView buy = (TextView) findViewById(R.id.buy);
        switch (getIntent().getIntExtra("code", 0)){
            case 0:
                contact.setText("联系卖家");
                buy.setText("现在购买！");
                break;
            case 1:
                contact.setText("联系买家");
                buy.setText("现在应求！");
                break;
            default:break;
        }
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellingDetail.this, Me.class);
                intent.putExtra("user_name", getIntent().getStringExtra("user_name"));
                startActivity(intent);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCollection();
            }
        });
        buyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insertRecord();
            }
        });
        Toast.makeText(SellingDetail.this, getIntent().getIntExtra("com_id", -1) + getIntent().getStringExtra("user_name"), Toast.LENGTH_SHORT).show();
        getItem(getIntent().getIntExtra("com_id", -1), getIntent().getStringExtra("user_name"), getIntent().getIntExtra("code", 0));
    }

    public class LocalImageHolderView implements Holder<String> {

        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;

        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context).load(data).into(imageView);

        }
    }

    private void getItem(final int comId, final String user_name, final int code){
        if(comId == -1){
            Toast.makeText(SellingDetail.this, "无指定商品信息", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_name", user_name)
                            .add("com_id", String.valueOf(comId))
                            .build();
                    Log.d("DETAIL", "comid:" + comId + ", username: " + user_name + ", code: " + code);
                    String url = "";
                    switch (code){
                        case 0:
                            url = "http://123.207.161.20/zhangbo/commodity.php/detail.php";
                            break;
                        case 1:
                            url = "http://123.207.161.20/zhangbo/req_commodity.php/detail.php";
                            break;
                    }
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        Log.d("DETAIL", responseData);
                        final CommodityDetail msg = parseJSONWithJSONObject(responseData);
                        Log.d("DETAIL", msg.getCommodityName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(msg.getCommodityName());
                                TextView nameView = (TextView) findViewById(R.id.tv_goods_title);
                                nameView.setText(msg.getCommodityName());
                                TextView typeView = (TextView)findViewById(R.id.tv_goods_type);
                                typeView.setText("#" + msg.getTag());
                                TextView priceView = (TextView) findViewById(R.id.tv_goods_price);
                                DecimalFormat df = new DecimalFormat("#####0.00");
                                priceView.setText("￥" + df.format(msg.getPrice()));
                                TextView desView = (TextView) findViewById(R.id.tv_goods_description);
                                desView.setText(msg.getDescription());
                                TextView upTimeView = (TextView) findViewById(R.id.tv_goods_up_time);
                                upTimeView.setText("上架 " + msg.getUpTime());
                                TextView downTimeView = (TextView) findViewById(R.id.tv_goods_down_time);
                                downTimeView.setText("下架 " + msg.getDownTime());
                                TextView stateView = (TextView) findViewById(R.id.tv_goods_state);
                                stateView.setText("状态 " + msg.getState());
                                images.add("http://123.207.161.20" + msg.getImageID());
                                Log.d("DETAIL", msg.getImageID());
                                Log.d("DETAIL", images.get(0));
                                images.add("http://123.207.161.20" + msg.getImageID());
                                images.add("http://123.207.161.20" + msg.getImageID());
                                convenientBanner.notifyDataSetChanged();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private CommodityDetail parseJSONWithJSONObject(String jsonData){
        CommodityDetail msg = null;
        try{
            Log.d("DETAIL", jsonData);
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
                String state = jsonObject.getString("state");
                int detailId = jsonObject.getInt("detail_id");
                msg = new CommodityDetail(user_name, name, comId, price, type, description, images, upTime, downTime, detailId, state);
                Log.d("DETAIL", "name: " + name);
                Log.d("DETAIL", "user_name: " + user_name);
                Log.d("DETAIL", "type: " + type);
                Log.d("DETAIL", "com_id：" + comId);
                Log.d("DETAIL", "price: " + price);
                Log.d("DETAIL", "up_time: " + upTime);
                Log.d("DETAIL", "down_time: " + downTime);
                Log.d("DETAIL", "description: " + description);
                Log.d("DETAIL", "image: " + images);
                Log.d("DETAIL", "detail_id: " + detailId);
                Log.d("DETAIL", "state: " + state);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private void insertCollection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Log.d("FAAV", getIntent().getStringExtra("wawawa"));
                Log.d("FAAV", Integer.toString(getIntent().getIntExtra("code", 0)));
                Log.d("FAAV", Integer.toString(getIntent().getIntExtra("com_id", -1)));
                Log.d("FAAV", getIntent().getStringExtra("user_name"));

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name", getIntent().getStringExtra("wawawa"))
                        .add("owner_name", getIntent().getStringExtra("user_name"))
                        .add("req", Integer.toString(getIntent().getIntExtra("code", 0)))
                        .add("com_id", Integer.toString(getIntent().getIntExtra("com_id", -1)))
                        .build();
                Request request = new Request.Builder()
                        .url("http://123.207.161.20/sl/ins_coll.php")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("FAAV", responseData);
                    if(responseData.equals("true")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SellingDetail.this, "收藏添加成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SellingDetail.this, "收藏添加失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

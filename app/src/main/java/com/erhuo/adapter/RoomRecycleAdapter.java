package com.erhuo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.erhuo.activitiy_erhuo.MySellingCommodity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.activitiy_erhuo.SellingDetail;
import com.erhuo.entity.CommodityHome;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Gary on 2017/12/7.
 */

public class RoomRecycleAdapter extends RecyclerView.Adapter<RoomRecycleAdapter.ViewHolder> {

    private Context mContext;
    private List<CommodityHome> mCommodityHome;
    private MySellingCommodity activity;
    private int code;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView com_image;
        TextView com_name;
        TextView com_des;
        TextView com_price;
        TextView com_tag;
        TextView com_upTime;
        TextView com_downTime;
        Button button_click;
        Button button_delete;
        Button button_update;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            com_image = (ImageView) itemView.findViewById(R.id.com_room_itemcard);
            com_name = (TextView) itemView.findViewById(R.id.com_room_itemcard_name);
            com_des = (TextView) itemView.findViewById(R.id.com_room_itemcard_des);
            com_price = (TextView) itemView.findViewById(R.id.com_room_itemcard_price);
            com_tag = (TextView) itemView.findViewById(R.id.com_room_itemcard_tag);
            com_upTime = (TextView) itemView.findViewById(R.id.com_room_itemcard_up_time);
            com_downTime = (TextView) itemView.findViewById(R.id.com_room_itemcard_down_time);
            button_click = (Button) itemView.findViewById(R.id.room_click);
            button_delete = (Button) itemView.findViewById(R.id.room_delete);
            button_update = (Button) itemView.findViewById(R.id.room_update);
        }
    }

    public RoomRecycleAdapter(List<CommodityHome> mCommodityHome, MySellingCommodity activity, int code) {
        this.mCommodityHome = mCommodityHome;
        this.activity = activity;
        this.code = code;
    }

    @Override
    public RoomRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.room_commodity_itemcard, parent, false);
        return new RoomRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomRecycleAdapter.ViewHolder holder, int position) {
        final CommodityHome commodityHome = mCommodityHome.get(position);
        holder.com_name.setText(commodityHome.getCommodityName());
        holder.com_des.setText(commodityHome.getDescription());
        DecimalFormat df = new DecimalFormat("#####0.00");
        String temp = "￥" + df.format(commodityHome.getPrice());
        holder.com_price.setText(temp);
        temp = "#" + commodityHome.getTag();
        holder.com_tag.setText(temp);
        temp = "上架" + commodityHome.getUpTime().substring(0, 10);
        holder.com_upTime.setText(temp);
        temp = "下架" + commodityHome.getDownTime().substring(0, 10);
        holder.com_downTime.setText(temp);
        temp = "http://123.207.161.20" + commodityHome.getImageID();
        Glide.with(mContext).load(temp).into(holder.com_image);
        holder.button_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RORORO", commodityHome.getCommodityName() + ", " + code);
                //Toast.makeText(v.getContext(), "hahaha from: " + commodityHome.getCommodityName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, SellingDetail.class);
                intent.putExtra("com_id", commodityHome.getCommodityId());
                intent.putExtra("user_name", commodityHome.getUserName());
                intent.putExtra("code", code);
                activity.startActivity(intent);
            }
        });
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("user_name", commodityHome.getUserName())
                                    .add("com_id", Integer.toString(commodityHome.getCommodityId()))
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://123.207.161.20/zhangbo/commodity.php/delete.php")
                                    .post(requestBody)
                                    .build();

                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            int success = jsonObject.getInt("success");
                            Log.d("ADD", Integer.toString(success));
                            if(success == 1){
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                                        activity.getItem();
                                    }
                                });
                            }
                            else{
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (IOException e) {

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, "无网络连接", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        holder.button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCommodityHome.size();
    }
}

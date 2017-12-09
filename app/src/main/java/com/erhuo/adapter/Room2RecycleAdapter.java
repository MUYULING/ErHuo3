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

import com.bumptech.glide.Glide;
import com.erhuo.activitiy_erhuo.MyRequiringCommodity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.activitiy_erhuo.SellingDetail;
import com.erhuo.entity.CommodityDetail;
import com.erhuo.entity.CommodityHome;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Gary on 2017/12/7.
 */

public class Room2RecycleAdapter extends RecyclerView.Adapter<Room2RecycleAdapter.ViewHolder> {

    private Context mContext;
    private List<CommodityDetail> mCommodityDetail;
    private MyRequiringCommodity activity;
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

    public Room2RecycleAdapter(List<CommodityDetail> mCommodityDetail, MyRequiringCommodity activity, int code) {
        this.mCommodityDetail = mCommodityDetail;
        this.activity = activity;
        this.code = code;
    }

    @Override
    public Room2RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.room_commodity_itemcard, parent, false);
        return new Room2RecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Room2RecycleAdapter.ViewHolder holder, int position) {
        final CommodityDetail commodityDetail = mCommodityDetail.get(position);
        holder.com_name.setText(commodityDetail.getCommodityName());
        holder.com_des.setText(commodityDetail.getDescription());
        DecimalFormat df = new DecimalFormat("#####0.00");
        String temp = "￥" + df.format(commodityDetail.getPrice());
        holder.com_price.setText(temp);
        temp = "#" + commodityDetail.getTag();
        holder.com_tag.setText(temp);
        temp = "上架" + commodityDetail.getUpTime().substring(0, 10);
        holder.com_upTime.setText(temp);
        temp = "下架" + commodityDetail.getDownTime().substring(0, 10);
        holder.com_downTime.setText(temp);
        temp = "http://123.207.161.20" + commodityDetail.getImageID();
        Glide.with(mContext).load(temp).into(holder.com_image);
        holder.button_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RORORO", commodityDetail.getCommodityName() + ", " + code);
                //Toast.makeText(v.getContext(), "hahaha from: " + commodityHome.getCommodityName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, SellingDetail.class);
                intent.putExtra("com_id", commodityDetail.getCommodityId());
                intent.putExtra("user_name", commodityDetail.getUserName());
                intent.putExtra("wawawa", activity.getIntent().getStringExtra("user_name"));
                intent.putExtra("code", code);
                activity.startActivity(intent);
            }
        });
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        return mCommodityDetail.size();
    }
}

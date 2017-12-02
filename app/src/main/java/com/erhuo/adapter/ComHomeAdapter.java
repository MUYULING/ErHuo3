package com.erhuo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erhuo.erhuo3.R;
import com.erhuo.util.CommodityHome;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Gary on 2017/11/25.
 */

public class ComHomeAdapter extends RecyclerView.Adapter<ComHomeAdapter.ViewHolder> {

    private Context mContext;
    private List<CommodityHome> mCommodityHome;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView com_image;
        TextView com_name;
        TextView com_des;
        TextView com_price;
        TextView com_tag;
        TextView com_upTime;
        TextView com_downTime;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            com_image = (ImageView) itemView.findViewById(R.id.com_home_itemcard);
            com_name = (TextView) itemView.findViewById(R.id.com_home_itemcard_name);
            com_des = (TextView) itemView.findViewById(R.id.com_home_itemcard_des);
            com_price = (TextView) itemView.findViewById(R.id.com_home_itemcard_price);
            com_tag = (TextView) itemView.findViewById(R.id.com_home_itemcard_tag);
            com_upTime = (TextView) itemView.findViewById(R.id.com_home_itemcard_up_time);
            com_downTime = (TextView) itemView.findViewById(R.id.com_home_itemcard_down_time);
        }
    }

    public ComHomeAdapter(List<CommodityHome> mCommodityHome) {
        this.mCommodityHome = mCommodityHome;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_commodity_itemcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommodityHome commodityHome = mCommodityHome.get(position);
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
    }

    @Override
    public int getItemCount() {
        return mCommodityHome.size();
        }
}

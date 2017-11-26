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

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            com_image = (ImageView) itemView.findViewById(R.id.com_home_itemcard);
            com_name = (TextView) itemView.findViewById(R.id.com_home_itemcard_name);
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
        holder.com_name.setText(commodityHome.getCommodity_name());
        Glide.with(mContext).load(commodityHome.getImageID()).into(holder.com_image);
    }

    @Override
    public int getItemCount() {
        return mCommodityHome.size();
        }
}

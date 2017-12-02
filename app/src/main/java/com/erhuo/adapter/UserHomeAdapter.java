package com.erhuo.adapter;

import android.content.Context;
import android.media.Image;
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
import com.erhuo.erhuo3.R;
import com.erhuo.util.UserHome;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Gary on 2017/12/2.
 */

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.ViewHolder> {

    private Context mcontext;
    private List<UserHome> mUserHome;

    public UserHomeAdapter(List<UserHome> mUserHome) {
        this.mUserHome = mUserHome;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mcontext == null){
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.home_user_itemcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserHome userHome = mUserHome.get(position);
        holder.nick_name.setText(userHome.getNickName());
        holder.user_name.setText(userHome.getUserName());
        DecimalFormat df = new DecimalFormat("#####0.0");
        holder.mark.setText(df.format(userHome.getMark()));
        String temp;
        temp = "http://123.207.161.20" + userHome.getImageID();
        Glide.with(mcontext).load(temp).into(holder.user_image);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RORORO", userHome.getUserName());
                Toast.makeText(v.getContext(), "hahaha from: " + userHome.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserHome.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView user_image;
        TextView nick_name;
        TextView user_name;
        TextView mark;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            user_image = (ImageView) itemView.findViewById(R.id.user_home_itemcard);
            nick_name = (TextView) itemView.findViewById(R.id.user_home_itemcard_name);
            user_name = (TextView) itemView.findViewById(R.id.user_home_itemcard_username);
            mark = (TextView) itemView.findViewById(R.id.user_home_itemcard_mark);
            button = (Button) itemView.findViewById(R.id.click);
        }
    }
}

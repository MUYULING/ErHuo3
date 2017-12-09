package com.erhuo.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erhuo.activitiy_erhuo.Me;
import com.erhuo.activitiy_erhuo.NoticeActivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.activitiy_erhuo.SellingDetail;
import com.erhuo.entity.Notice;

import java.util.List;
/**
 * Created by msi on 2017/12/7.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private List<Notice> mNoticeList;
    private NoticeActivity activity;
    private int code;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView applier;
        TextView sss;
        TextView com;
        TextView date;

        public ViewHolder(View view){
            super(view);
            applier = (TextView) view.findViewById(R.id.buyername);
            sss = (TextView) view.findViewById(R.id.xiangmai);
            com = (TextView) view.findViewById(R.id.comname);
            date = (TextView) view.findViewById(R.id.notice_date);
        }
    }

    public NoticeAdapter(List<Notice> noticeList, NoticeActivity activity, int code){
        this.mNoticeList = noticeList;
        this.activity = activity;
        this.code = code;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_notice_card,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        final Notice notice = mNoticeList.get(position);
        if(code == 0){
            holder.sss.setText("想要购买你的");
        }
        else{
            holder.sss.setText("想要应求你的");
        }
        holder.applier.setText(notice.getApplier());
        holder.applier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Me.class);
                intent.putExtra("user_name", notice.getApplierID());
                activity.startActivity(intent);
            }
        });
        holder.com.setText(notice.getCom());
        holder.com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SellingDetail.class);
                intent.putExtra("user_name", notice.getOwnerID());
                intent.putExtra("com_id", notice.getComID());
                intent.putExtra("code", code);
                activity.startActivity(intent);
            }
        });
        holder.date.setText(notice.getDate());
    }

    @Override
    public int getItemCount(){
        return mNoticeList.size();
    }
}

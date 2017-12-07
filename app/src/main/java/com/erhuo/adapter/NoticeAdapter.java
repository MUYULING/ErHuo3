package com.erhuo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erhuo.activitiy_erhuo.R;
import com.erhuo.entity.Notice;

import java.util.List;
/**
 * Created by msi on 2017/12/7.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private List<Notice> mNoticeList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView firstname,secondname,date;

        public ViewHolder(View view){
            super(view);
            firstname = (TextView)view.findViewById(R.id.firstname);
            secondname = (TextView)view.findViewById(R.id.secondname);
            date = (TextView)view.findViewById(R.id.notice_date);
        }
    }

    public NoticeAdapter(List<Notice> noticeList){
        mNoticeList = noticeList;
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
        Notice notice = mNoticeList.get(position);
        holder.firstname.setText(notice.getFirstname());
        holder.secondname.setText(notice.getSecondname());
        holder.date.setText(notice.getDate());
    }

    @Override
    public int getItemCount(){
        return mNoticeList.size();
    }
}

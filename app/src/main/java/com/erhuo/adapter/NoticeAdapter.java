package com.erhuo.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.activitiy_erhuo.Me;
import com.erhuo.activitiy_erhuo.NoticeActivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.activitiy_erhuo.SellingDetail;
import com.erhuo.entity.Notice;
import com.erhuo.fragment.RequiringNoticeFragment;
import com.erhuo.fragment.SellingNoticeFragment;
import com.erhuo.fragment.SellingRecordFragment;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by msi on 2017/12/7.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private List<Notice> mNoticeList;
    private NoticeActivity activity;
    private int code;
    private Fragment fragment;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView applier;
        TextView sss;
        TextView com;
        TextView date;
        Button ack;
        Button nak;

        public ViewHolder(View view){
            super(view);
            applier = (TextView) view.findViewById(R.id.buyername);
            sss = (TextView) view.findViewById(R.id.xiangmai);
            com = (TextView) view.findViewById(R.id.comname);
            date = (TextView) view.findViewById(R.id.notice_date);
            ack = (Button) view.findViewById(R.id.com_home_notice_accept);
            nak = (Button) view.findViewById(R.id.com_home_notice_decline);
        }
    }

    public NoticeAdapter(List<Notice> noticeList, NoticeActivity activity, int code, Fragment fragment){
        this.mNoticeList = noticeList;
        this.activity = activity;
        this.code = code;
        this.fragment = fragment;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_notice_card,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
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
        holder.ack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("message_id", Integer.toString(notice.getMessageID()))
                                .add("user_name", notice.getOwnerID())
                                .add("choose", "1")
                                .add("req", Integer.toString(code))
                                .build();
                        Request request = new Request.Builder()
                                .url("http://123.207.161.20/sl/manage_msg.php")
                                .post(requestBody)
                                .build();
                        try{
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            if(responseData.equals("true")){
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "请求已被接受", Toast.LENGTH_SHORT).show();
                                        if(code == 0){
                                            ((SellingNoticeFragment) fragment).getItem();
                                        }
                                        else{
                                            ((RequiringNoticeFragment) fragment).getItem();
                                        }
                                    }
                                });
                            }
                            else{
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "操作失败", Toast.LENGTH_SHORT).show();
                                        if(code == 0){
                                            ((SellingNoticeFragment) fragment).getItem();
                                        }
                                        else{
                                            ((RequiringNoticeFragment) fragment).getItem();
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        holder.nak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("message_id", Integer.toString(notice.getMessageID()))
                                .add("user_name", notice.getOwnerID())
                                .add("choose", "2")
                                .add("req", Integer.toString(code))
                                .build();
                        Request request = new Request.Builder()
                                .url("http://123.207.161.20/sl/manage_msg.php")
                                .post(requestBody)
                                .build();
                        try{
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            Log.d("NOTICE", responseData);
                            if(responseData.equals("true")){
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "请求已被拒绝", Toast.LENGTH_SHORT).show();
                                        if(code == 0){
                                            ((SellingNoticeFragment) fragment).getItem();
                                        }
                                        else{
                                            ((RequiringNoticeFragment) fragment).getItem();
                                        }
                                    }
                                });
                            }
                            else{
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "操作失败", Toast.LENGTH_SHORT).show();
                                        if(code == 0){
                                            ((SellingNoticeFragment) fragment).getItem();
                                        }
                                        else{
                                            ((RequiringNoticeFragment) fragment).getItem();
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public int getItemCount(){
        return mNoticeList.size();
    }
}

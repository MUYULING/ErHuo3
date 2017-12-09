package com.erhuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.activitiy_erhuo.MyRecordActivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.entity.Notice;
import com.erhuo.entity.Record;
import com.erhuo.fragment.RequiringRecordFragment;
import com.erhuo.fragment.SellingRecordFragment;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Gary on 2017/12/9.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<Record> mRecordList;
    private int code;
    private int mode;
    private MyRecordActivity activity;
    private Fragment fragment;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView buynn;
        TextView sellnn;
        TextView comn;
        TextView state;
        TextView date;
        Button ack;
        Button nak;

        public ViewHolder(View view){
            super(view);
            buynn = (TextView) view.findViewById(R.id.record_buyername);
            sellnn = (TextView) view.findViewById(R.id.record_ownername);
            comn = (TextView) view.findViewById(R.id.record_comname);
            state = (TextView) view.findViewById(R.id.record_state);
            date = (TextView) view.findViewById(R.id.record_date);
            ack = (Button) view.findViewById(R.id.com_home_record_accept);
            nak = (Button) view.findViewById(R.id.com_home_record_decline);
        }
    }

    public RecordAdapter(List<Record> recordList, int code, int mode, MyRecordActivity activity, Fragment fragment){
        mRecordList = recordList;
        this.code = code;
        this.mode = mode;
        this.activity = activity;
        this.fragment = fragment;
    }



    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_card, parent,false);
        RecordAdapter.ViewHolder holder = new RecordAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder holder, int position){
        final Record record = mRecordList.get(position);
        holder.sellnn.setText("所有方 " + record.getSellerNickName());
        holder.buynn.setText("意向方 " + record.getBuyerNickName());
        holder.comn.setText("商品名称 " + record.getComName());
        holder.state.setText("状态 " + record.getRecordState());
        if(record.getRecordTime() != null){
            holder.date.setText("记录生成时间 " + record.getRecordTime());
        }
        else{
            holder.date.setText("记录生成时间 " + record.getRecordTime());
        }
        if(code == 0){
            holder.ack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("record_id", Integer.toString(record.getRecordID()))
                                    .add("record_state", "已完成")
                                    .add("req", Integer.toString(mode))
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://123.207.161.20/sl/up_rec.php")
                                    .post(requestBody)
                                    .build();
                            Response response = null;
                            try {
                                response = client.newCall(request).execute();
                                String responseData = response.body().string();
                                Log.d("RECORD", responseData);
                                if(responseData.equals("true")){
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, "交易已完成", Toast.LENGTH_SHORT).show();
                                            if(code == 0){
                                                if(mode == 0){
                                                    SellingRecordFragment sellingRecordFragment = (SellingRecordFragment) fragment;
                                                    sellingRecordFragment.getItem("sell");
                                                }
                                                else{
                                                    RequiringRecordFragment requiringRecordFragment = (RequiringRecordFragment) fragment;
                                                    requiringRecordFragment.getItem("sell");
                                                }
                                            }
                                            else{
                                                if(mode == 0){
                                                    SellingRecordFragment sellingRecordFragment = (SellingRecordFragment) fragment;
                                                    sellingRecordFragment.getItem("buy");
                                                }
                                                else{
                                                    RequiringRecordFragment requiringRecordFragment = (RequiringRecordFragment) fragment;
                                                    requiringRecordFragment.getItem("buy");
                                                }
                                            }
                                        }
                                    });
                                }
                                else{
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, "操作失败", Toast.LENGTH_SHORT).show();
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
                                    .add("record_id", Integer.toString(record.getRecordID()))
                                    .add("record_state", "已取消")
                                    .add("req", Integer.toString(mode))
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://123.207.161.20/sl/up_rec.php")
                                    .post(requestBody)
                                    .build();
                            Response response = null;
                            try {
                                response = client.newCall(request).execute();
                                String responseData = response.body().string();
                                Log.d("RECORD", responseData);
                                if(responseData.equals("true")){
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, "交易已取消", Toast.LENGTH_SHORT).show();
                                            if(code == 0){
                                                if(mode == 0){
                                                    SellingRecordFragment sellingRecordFragment = (SellingRecordFragment) fragment;
                                                    sellingRecordFragment.getItem("sell");
                                                }
                                                else{
                                                    RequiringRecordFragment requiringRecordFragment = (RequiringRecordFragment) fragment;
                                                    requiringRecordFragment.getItem("sell");
                                                }
                                            }
                                            else{
                                                if(mode == 0){
                                                    SellingRecordFragment sellingRecordFragment = (SellingRecordFragment) fragment;
                                                    sellingRecordFragment.getItem("buy");
                                                }
                                                else{
                                                    RequiringRecordFragment requiringRecordFragment = (RequiringRecordFragment) fragment;
                                                    requiringRecordFragment.getItem("buy");
                                                }
                                            }
                                        }
                                    });
                                }
                                else{
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, "操作失败", Toast.LENGTH_SHORT).show();
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
        else{
            holder.ack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "用户无权限", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            holder.nak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "用户无权限", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return mRecordList.size();
    }
}

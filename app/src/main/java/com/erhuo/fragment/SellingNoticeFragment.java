package com.erhuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erhuo.activitiy_erhuo.Me;
import com.erhuo.activitiy_erhuo.NoticeActivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.adapter.NoticeAdapter;
import com.erhuo.entity.CommodityHome;
import com.erhuo.entity.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Gary on 2017/12/9.
 */

public class SellingNoticeFragment extends Fragment {

    private List<Notice> noticeList = new ArrayList<>();
    private NoticeAdapter adapter;
    private View view;
    private List<Notice> tmpList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        NoticeActivity activity = (NoticeActivity) getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notice_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NoticeAdapter(noticeList, (NoticeActivity)getActivity(), 0, this);
        recyclerView.setAdapter(adapter);
        if(noticeList.size() == 0){
            getItem();
        }
        return view;
    }

    public void getItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    NoticeActivity activity = (NoticeActivity) getActivity();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("to_user", activity.getIntent().getStringExtra("user_name"))
                            .add("req", "0")
                            .build();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/sl/qa_msg.php")
                            .post(requestBody)
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noticeList.clear();
                            noticeList.addAll(tmpList);
                            adapter.notifyDataSetChanged();

                        }
                    });
                } catch (IOException e) {
                    final NoticeActivity activity = (NoticeActivity) getActivity();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "无网络连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            tmpList.clear();
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ownerID = jsonObject.getString("to_user");
                String owner = jsonObject.getString("to_nick");
                String applierID = jsonObject.getString("from_user");
                String applier = jsonObject.getString("from_nick");
                int comId = jsonObject.getInt("com_id");
                String com = jsonObject.getString("name");
                String date = jsonObject.getString("send_time");
                int messageID = jsonObject.getInt("message_id");
                Notice notice = new Notice(owner, ownerID, applier, applierID, com, comId, date, messageID);
                tmpList.add(notice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

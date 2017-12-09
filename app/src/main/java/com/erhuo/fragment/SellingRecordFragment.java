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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.erhuo.activitiy_erhuo.MyRecordActivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.adapter.RecordAdapter;
import com.erhuo.entity.Notice;
import com.erhuo.entity.Record;

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

public class SellingRecordFragment extends Fragment {

    private List<Record> recordList = new ArrayList<>();
    private RecordAdapter adapter;
    private View view;
    private List<Record> tmpList = new ArrayList<>();
    private MyRecordActivity activity;
    private RecyclerView recyclerView;

    private static final String[] typename={"售出","买入"};
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_record, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.record_rev);
        activity = (MyRecordActivity)getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new RecordAdapter(recordList, 0, 0, activity, SellingRecordFragment.this);
        recyclerView.setAdapter(adapter);
        if(recordList.size() == 0){
            getItem("sell");
        }

        spinner = (Spinner) view.findViewById(R.id.TradeRecord_spinner);
        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        spinnerAdapter = ArrayAdapter.createFromResource(activity, R.array.SellRecord, R.layout.type_spiner_text_item);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
        spinnerAdapter.setDropDownViewResource(R.layout.type_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner.setAdapter(spinnerAdapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        return view;
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            switch (arg2){
                case 0:
                    adapter = new RecordAdapter(recordList, 0, 0, activity, SellingRecordFragment.this);
                    recyclerView.setAdapter(adapter);
                    getItem("sell");
                    break;
                case 1:
                    adapter = new RecordAdapter(recordList, 1, 0, activity, SellingRecordFragment.this);
                    recyclerView.setAdapter(adapter);
                    getItem("buy");break;
                default:break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    public void getItem(final String state) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("seller_name", activity.getIntent().getStringExtra("user_name"))
                        .add("buyer_name", activity.getIntent().getStringExtra("user_name"))
                        .add("sell_or_buy", state)
                        .add("req", "0")
                        .build();
                Request request = new Request.Builder()
                        .url("http://123.207.161.20/sl/qa_rec.php")
                        .post(requestBody)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("RECORD", responseData);
                    parseJSONWithJSONObject(responseData);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recordList.clear();
                            recordList.addAll(tmpList);
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (IOException e) {
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
                int recordID = jsonObject.getInt("record_id");
                String buyerNickName = jsonObject.getString("buyer_nick_name");
                String sellerNickName = jsonObject.getString("seller_nick_name");
                String sellerName = jsonObject.getString("seller_name");
                String buyerName = jsonObject.getString("buyer_name");
                String recordState = jsonObject.getString("record_state");
                String recordTime = jsonObject.getString("record_time");
                int comID = jsonObject.getInt("com_id");
                String comName = jsonObject.getString("com_name");
                Record record = new Record(recordID, buyerName, buyerNickName, sellerName, sellerNickName, comName, comID, recordState, recordTime);
                tmpList.add(record);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

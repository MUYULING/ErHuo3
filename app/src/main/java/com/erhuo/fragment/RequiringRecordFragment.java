package com.erhuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.erhuo.activitiy_erhuo.MainActivity;
import com.erhuo.activitiy_erhuo.MyRecordActivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.activitiy_erhuo.SellCommodityEdit;
import com.erhuo.adapter.RecordAdapter;
import com.erhuo.entity.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2017/12/9.
 */

public class RequiringRecordFragment extends Fragment {

    private List<Notice> noticeList = new ArrayList<>();
    private RecordAdapter adapter;
    private View view;
    private List<Notice> tmpList = new ArrayList<>();

    private static final String[] typename={"买入","售出"};
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_record, container, false);
        MyRecordActivity activity = (MyRecordActivity) getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notice_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new RecordAdapter(noticeList);
        recyclerView.setAdapter(adapter);
        if(noticeList.size() == 0){
            getItem("sell", 1);
        }

        spinner = (Spinner) view.findViewById(R.id.TradeRecord_spinner);
        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        spinnerAdapter = ArrayAdapter.createFromResource(activity, R.array.CommodityType, R.layout.type_spiner_text_item);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
        spinnerAdapter.setDropDownViewResource(R.layout.type_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner.setAdapter(spinnerAdapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        return view;
    }


    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {



        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    private void getItem(String state, int code) {
        //Notice notice = new Notice("asd", "asd", "2017.12.7");
       // noticeList.add(notice);
        adapter.notifyDataSetChanged();
    }
}

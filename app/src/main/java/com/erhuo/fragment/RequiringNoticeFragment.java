package com.erhuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erhuo.activitiy_erhuo.NoticeAvtivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.adapter.NoticeAdapter;
import com.erhuo.entity.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2017/12/9.
 */

public class RequiringNoticeFragment extends Fragment {

    private List<Notice> noticeList = new ArrayList<>();
    private NoticeAdapter adapter;
    private View view;
    private List<Notice> tmpList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        NoticeAvtivity activity = (NoticeAvtivity) getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notice_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NoticeAdapter(noticeList);
        recyclerView.setAdapter(adapter);
        if(noticeList.size() == 0){
            getItem();
        }
        return view;
    }

    private void getItem() {
        Notice notice = new Notice("asd", "asd", "2017.12.7");
        noticeList.add(notice);
        adapter.notifyDataSetChanged();
    }
}

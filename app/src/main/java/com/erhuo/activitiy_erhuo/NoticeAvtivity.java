package com.erhuo.activitiy_erhuo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.erhuo.adapter.NoticeAdapter;
import com.erhuo.entity.Notice;

import java.util.ArrayList;
import java.util.List;

public class NoticeAvtivity extends AppCompatActivity {

    private List<Notice> noticeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_notice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Notice);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initNotices();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notice_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NoticeAdapter adapter = new NoticeAdapter(noticeList);
        recyclerView.setAdapter(adapter);
    }

    private void initNotices() {
        for (int i = 0; i < 2; i++) {
            Notice apple = new Notice("abc","def","2017/12/7");
            noticeList.add(apple);
        }
    }
}

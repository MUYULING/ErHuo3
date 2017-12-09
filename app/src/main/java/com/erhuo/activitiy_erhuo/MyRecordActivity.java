package com.erhuo.activitiy_erhuo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.erhuo.adapter.FragmentAdapter;
import com.erhuo.fragment.RequiringRecordFragment;
import com.erhuo.fragment.SellingNoticeFragment;
import com.erhuo.fragment.RequiringNoticeFragment;
import com.erhuo.fragment.SellingRecordFragment;

import java.util.ArrayList;
import java.util.List;

public class MyRecordActivity extends AppCompatActivity {

    private TabLayout tab_title;
    private ViewPager vp_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
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
        initControls();
        fragmentChange();
    }

    private void initControls()
    {
        tab_title = (TabLayout)findViewById(R.id.tab_title);
        vp_pager = (ViewPager)findViewById(R.id.vp_pager);

    }

    private void fragmentChange()
    {
        List<Fragment> list_fragment = new ArrayList<>();
        SellingRecordFragment sellingRecordFragment = new SellingRecordFragment();
        RequiringRecordFragment requiringRecordFragment = new RequiringRecordFragment();
        list_fragment.add(sellingRecordFragment);
        list_fragment.add(requiringRecordFragment);

        List<String> list_title = new ArrayList<>();
        list_title.add("出售商品");
        list_title.add("求购商品");

        tab_title.setTabMode(TabLayout.MODE_FIXED);

        tab_title.addTab(tab_title.newTab().setText(list_title.get(0)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(1)));

        FragmentAdapter fAdapter = new FragmentAdapter(getSupportFragmentManager(), list_fragment, list_title);
        vp_pager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }

}

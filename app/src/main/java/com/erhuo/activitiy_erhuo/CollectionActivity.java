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
import com.erhuo.fragment.RequiringCollFragment;
import com.erhuo.fragment.RequiringNoticeFragment;
import com.erhuo.fragment.SellingCollFragment;
import com.erhuo.fragment.SellingNoticeFragment;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private TabLayout tab_title;
    private ViewPager vp_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

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
        SellingCollFragment sellingCollFragment = new SellingCollFragment();
        RequiringCollFragment requiringCollFragment = new RequiringCollFragment();
        list_fragment.add(sellingCollFragment);
        list_fragment.add(requiringCollFragment);

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

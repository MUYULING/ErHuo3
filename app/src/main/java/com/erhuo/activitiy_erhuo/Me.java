package com.erhuo.activitiy_erhuo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.erhuo.adapter.FragmentAdapter;
import com.erhuo.fragment.BuyingFragment;
import com.erhuo.fragment.CommentFragment;
import com.erhuo.fragment.SellingFragment;

import java.util.ArrayList;
import java.util.List;

public class Me extends AppCompatActivity {

    private TabLayout tab_title;
    private ViewPager vp_pager;
    private List<String> list_title;                                      //tab名称列表
    private List<Fragment> list_fragment;
    private FragmentAdapter fAdapter;
    private SellingFragment sellingFragment;
    private BuyingFragment buyingFragment;
    private CommentFragment commentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initControls();
        fragmentChange();
    }

    /**
     * 初始化控件
     */
    private void initControls()
    {
        tab_title = (TabLayout)findViewById(R.id.tab_title);
        vp_pager = (ViewPager)findViewById(R.id.vp_pager);

    }

    private void fragmentChange()
    {
        list_fragment = new ArrayList<>();
        sellingFragment = new SellingFragment();
        buyingFragment = new BuyingFragment();
        commentFragment = new CommentFragment();
        list_fragment.add(sellingFragment);
        list_fragment.add(buyingFragment);
        list_fragment.add(commentFragment);

        list_title = new ArrayList<>();
        list_title.add("正在出售");
        list_title.add("正在求购");
        list_title.add("评价");

        tab_title.setTabMode(TabLayout.MODE_FIXED);

        tab_title.addTab(tab_title.newTab().setText(list_title.get(0)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(1)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(2)));

        fAdapter = new FragmentAdapter(getSupportFragmentManager(),list_fragment,list_title);
        vp_pager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }
}

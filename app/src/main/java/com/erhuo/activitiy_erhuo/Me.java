package com.erhuo.activitiy_erhuo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.erhuo.adapter.FragmentAdapter;
import com.erhuo.adapter.ViewAdapter;
import com.erhuo.fragment.BuyingFragment;
import com.erhuo.fragment.CommentFragment;
import com.erhuo.fragment.SellingFragment;

import java.util.ArrayList;
import java.util.List;

public class Me extends AppCompatActivity {

    private TabLayout tab_title;
    private ViewPager vp_pager;
    private List<String> list_title;                                      //tab名称列表
    private List<View> listViews;
    private List<Fragment> list_fragment;
    private View sellingView;                                                //定义新闻页面
    private View buyingView;                                               //定义体育页面
    private View commentView;                                                 //定义娱乐页面
    private ViewAdapter vAdapter;                                         //定义以view为切换的adapter
    private FragmentAdapter fAdapter;                                     //定义以fragment为切换的adapter
    private SellingFragment sellingFragment;
    private BuyingFragment buyingFragment;
    private CommentFragment commentFragment;
    private int[] tabImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initControls();
        viewChanage();
        //fragmentChange();
    }

    /**
     * 初始化控件
     */
    private void initControls()
    {
        tab_title = (TabLayout)findViewById(R.id.tab_title);
        vp_pager = (ViewPager)findViewById(R.id.vp_pager);

        //为tabLayout上的图标赋值
        tabImg = new int[]{R.drawable.favorite,R.drawable.favorite,R.drawable.favorite};
    }

    /**
     * 采用在viewpager中切换 view 的方式,并添加了icon的功能
     */
    private void viewChanage()
    {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        sellingView = mInflater.inflate(R.layout.fragment_selling, null);
        buyingView = mInflater.inflate(R.layout.fragment_buying, null);
        commentView = mInflater.inflate(R.layout.fragment_comment, null);
        listViews.add(sellingView);
        listViews.add(buyingView);
        listViews.add(commentView);

        list_title = new ArrayList<>();
        list_title.add("正在出售");
        list_title.add("正在求购");
        list_title.add("评价");

        //设置TabLayout的模式,这里主要是用来显示tab展示的情况的
        //TabLayout.MODE_FIXED          各tab平分整个工具栏,如果不设置，则默认就是这个值
        //TabLayout.MODE_SCROLLABLE     适用于多tab的，也就是有滚动条的，一行显示不下这些tab可以用这个
        //                              当然了，你要是想做点特别的，像知乎里就使用的这种效果
        tab_title.setTabMode(TabLayout.MODE_FIXED);

        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);

        //为TabLayout添加tab名称
        tab_title.addTab(tab_title.newTab().setText(list_title.get(0)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(1)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(2)));


        vAdapter = new ViewAdapter(this,listViews,list_title,tabImg);
        vp_pager.setAdapter(vAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }

    /**
     * 采用viewpager中切换fragment
     */
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
        list_title.add("新闻");
        list_title.add("体育");
        list_title.add("娱乐");

        fAdapter = new FragmentAdapter(getSupportFragmentManager(),list_fragment,list_title);
        vp_pager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }
}

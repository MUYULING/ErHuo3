package com.erhuo.activitiy_erhuo;

/**
 * Created by mac on 2017/11/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.erhuo.interfaces.bCallBack;
import com.erhuo.interfaces.ICallBack;
import com.erhuo.UIView.SearchView;

/**
 * Created by Carson_Ho on 17/8/11.
 */

public class SearchActivity extends AppCompatActivity {

    // 1. 初始化搜索框变量
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2. 绑定视图
        setContentView(R.layout.activity_search);

        // 3. 绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);

        // 4. 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
                Intent intent = new Intent();
                intent.putExtra("data_return", string);
                setResult(RESULT_OK, intent);
                finish();
             }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

    }
}
package com.erhuo.activitiy_erhuo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erhuo.adapter.FragmentAdapter;
import com.erhuo.entity.User;
import com.erhuo.entity.UserMe;
import com.erhuo.fragment.BuyingFragment;
import com.erhuo.fragment.CommentFragment;
import com.erhuo.fragment.SellingFragment;

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

public class Me extends AppCompatActivity {

    private TabLayout tab_title;
    private ViewPager vp_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initControls();
        fragmentChange();
        getUser();
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
        List<Fragment> list_fragment = new ArrayList<>();
        SellingFragment sellingFragment = new SellingFragment();
        BuyingFragment buyingFragment = new BuyingFragment();
        CommentFragment commentFragment = new CommentFragment();
        list_fragment.add(sellingFragment);
        list_fragment.add(buyingFragment);
        list_fragment.add(commentFragment);

        List<String> list_title = new ArrayList<>();
        list_title.add("正在出售");
        list_title.add("正在求购");
        list_title.add("评价");

        tab_title.setTabMode(TabLayout.MODE_FIXED);

        tab_title.addTab(tab_title.newTab().setText(list_title.get(0)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(1)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(2)));

        FragmentAdapter fAdapter = new FragmentAdapter(getSupportFragmentManager(), list_fragment, list_title);
        vp_pager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }

    private void getUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("search", getIntent().getStringExtra("user_name"))
                        .build();
                Request request = new Request.Builder()
                        .url("http://123.207.161.20/gaolingzhe/userdetail.php")
                        .post(requestBody)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    final User user = parseJSONWithJSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView avatar = (ImageView) findViewById(R.id.personal_photo);
                            Glide.with(Me.this).load("http://123.207.161.20" + user.getImageID()).into(avatar);
                            TextView name = (TextView) findViewById(R.id.personal_name);
                            name.setText(user.getNickName());
                            TextView userName = (TextView) findViewById(R.id.personal_ID);
                            userName.setText(user.getUserName());
                            TextView userGender = (TextView) findViewById(R.id.personal_sex);
                            userGender.setText("" + user.getGender());
                            TextView userPhone = (TextView) findViewById(R.id.personal_contact);
                            userPhone.setText(user.getPhone());
                            TextView userMark = (TextView) findViewById(R.id.personal_score);
                            userMark.setText(String.valueOf(user.getMark()));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private User parseJSONWithJSONObject(String responseData) {
        User msg = null;
        try{
            JSONArray jsonArray = new JSONArray(responseData);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userName = jsonObject.getString("user_name");
                String nickName = jsonObject.getString("nick_name");
                String imageID = jsonObject.getString("head_image");
                String gender = jsonObject.getString("sex");
                String phone = jsonObject.getString("phone");
                double mark = jsonObject.getDouble("mark");
                String studentID = jsonObject.getString("student_id");
                String school = jsonObject.getString("school");
                msg = new User(userName, nickName, imageID, gender, phone, mark, studentID, school);
                Log.d("ME", "user_name: " + userName);
                Log.d("ME", "nick_name: " + nickName);
                Log.d("ME", "imageID: " + imageID);
                Log.d("ME", "gender: " + gender);
                Log.d("ME", "phone: " + phone);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
}

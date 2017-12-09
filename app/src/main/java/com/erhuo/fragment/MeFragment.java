package com.erhuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erhuo.activitiy_erhuo.Login;
import com.erhuo.activitiy_erhuo.MainActivity;
import com.erhuo.activitiy_erhuo.Me;
import com.erhuo.activitiy_erhuo.MyRequiringCommodity;
import com.erhuo.activitiy_erhuo.MySellingCommodity;
import com.erhuo.activitiy_erhuo.NoticeAvtivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.entity.UserMe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class MeFragment extends Fragment {


    private View view;
    private RelativeLayout meLayout;
    private RelativeLayout sellingLayout;
    private RelativeLayout requiringLayout;
    private RelativeLayout noticLayout;
    private RelativeLayout logoutLayout;
    private RelativeLayout buyingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        meLayout = (RelativeLayout) view.findViewById(R.id.mine);
        meLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                Intent intent = new Intent(view.getContext(), Me.class);
                intent.putExtra("user_name", activity.getIntent().getStringExtra("user_name"));
                startActivity(intent);
            }
        });
        sellingLayout = (RelativeLayout) view.findViewById(R.id.my_sellCommodity);
        sellingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity, MySellingCommodity.class);
                intent.putExtra("user_name", activity.getIntent().getStringExtra("user_name"));
                startActivityForResult(intent, 1);
            }
        });

        requiringLayout = (RelativeLayout) view.findViewById(R.id.my_requireCommodity);
        requiringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity,MyRequiringCommodity.class);
                intent.putExtra("user_name", activity.getIntent().getStringExtra("user_name"));
                startActivityForResult(intent, 1);
            }
        });
        noticLayout = (RelativeLayout) view.findViewById(R.id.my_tongzhi);
        noticLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                Intent intent = new Intent(view.getContext(), NoticeAvtivity.class);
                intent.putExtra("user_name", activity.getIntent().getStringExtra("user_name"));
                startActivity(intent);
            }
        });
        logoutLayout = (RelativeLayout) view.findViewById(R.id.LogOut);
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
                activity.finish();
            }
        });
        buyingLayout = (RelativeLayout) view.findViewById(R.id.my_requireCommodity);
        buyingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                Intent intent = new Intent(view.getContext(), MyRequiringCommodity.class);
                intent.putExtra("user_name", activity.getIntent().getStringExtra("user_name"));
                startActivity(intent);
                activity.finish();
            }
        });

        getUser();
        return view;
    }

    private void getUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MainActivity activity = (MainActivity) getActivity();
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("search", activity.getIntent().getStringExtra("user_name"))
                        .build();
                Request request = new Request.Builder()
                        .url("http://123.207.161.20/gaolingzhe/user.php")
                        .post(body)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    final UserMe userMe = parseJSONWithJSONObject(responseData);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView avatar = (ImageView) view.findViewById(R.id.my_photo);
                            Glide.with(getContext()).load("http://123.207.161.20" + userMe.getImageID()).into(avatar);
                            TextView name = (TextView) view.findViewById(R.id.my_name);
                            name.setText(userMe.getNickName());
                            TextView userName = (TextView) view.findViewById(R.id.my_yonghuID);
                            userName.setText(userMe.getUserName());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private UserMe parseJSONWithJSONObject(String responseData) {
        UserMe msg = null;
        try{
            JSONArray jsonArray = new JSONArray(responseData);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userName = jsonObject.getString("user_name");
                String nickName = jsonObject.getString("nick_name");
                String imageID = jsonObject.getString("head_image");
                String gender = jsonObject.getString("sex");
                msg = new UserMe(userName, nickName, imageID, gender);
                Log.d("ME", "user_name: " + userName);
                Log.d("ME", "nick_name: " + nickName);
                Log.d("ME", "imageID: " + imageID);
                Log.d("ME", "gender: " + gender);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
}

package com.erhuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.erhuo.adapter.ComHomeAdapter;
import com.erhuo.adapter.UserHomeAdapter;
import com.erhuo.activitiy_erhuo.MainActivity;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.activitiy_erhuo.SearchActivity;
import com.erhuo.entity.CommodityHome;
import com.erhuo.entity.UserHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class SearchFragment extends Fragment {

    private List<CommodityHome> comList = new ArrayList<>();
    private ComHomeAdapter adapter;
    private List<UserHome>userList = new ArrayList<>();
    private UserHomeAdapter adapter2;
    private RecyclerView recyclerView;


    private View view;
    private ImageView imageview;
    private TextView textview;
    private RelativeLayout layout;

    private String key;
    private List<CommodityHome> tmpList = new ArrayList<>();
    private List<UserHome> tmpList2 = new ArrayList<>();

    private static final String[] name={"出售商品","求购商品","用户"};
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private int itemSelectedNo = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        imageview = (ImageView) view.findViewById(R.id.search);
        textview = (TextView) view.findViewById(R.id.search_text);
        imageview.setImageResource(R.drawable.ic_search);
        layout = (RelativeLayout) view.findViewById(R.id.search_all);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        MainActivity activity2 = (MainActivity) getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.search_rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity2));
        switch (itemSelectedNo){
            case 0:
                adapter = new ComHomeAdapter(comList, activity2, 0);
                recyclerView.setAdapter(adapter);
                break;
            case 1:
                adapter = new ComHomeAdapter(comList, activity2, 1);
                recyclerView.setAdapter(adapter);
                break;
            case 2:
                adapter2 = new UserHomeAdapter(userList, (MainActivity) getActivity());
                recyclerView.setAdapter(adapter2);
                break;
            default:break;
        }
        MainActivity activity3 = (MainActivity) getActivity();
        spinner = (Spinner) view.findViewById(R.id.search_spinner);
        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        spinnerAdapter = ArrayAdapter.createFromResource(activity3, R.array.searchType, R.layout.search_spiner_text_item);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
        spinnerAdapter.setDropDownViewResource(R.layout.search_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(spinnerAdapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        return view;
    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> array, View view, int itemNo, long ret) {
            itemSelectedNo = itemNo;
            MainActivity activity = (MainActivity) getActivity();
            switch (itemSelectedNo){
                case 0:
                    adapter = new ComHomeAdapter(comList, activity, 0);
                    recyclerView.setAdapter(adapter);
                    if(key != null && !key.equals("")){
                        getSearchResult(itemSelectedNo);
                    }
                    break;
                case 1:
                    adapter = new ComHomeAdapter(comList, activity, 1);
                    recyclerView.setAdapter(adapter);
                    if(key != null && !key.equals("")){
                        getSearchResult(itemSelectedNo);
                    }
                    break;
                case 2:
                    adapter2 = new UserHomeAdapter(userList, (MainActivity) getActivity());
                    recyclerView.setAdapter(adapter2);
                    if(key != null && !key.equals("")){
                        getSearchResult(itemSelectedNo);
                    }
                    break;
                default:break;
            }
        }
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    textview.setText(returnedData);
                    Log.d("Search", returnedData);
                    key = returnedData;
                }
                break;
            default:
                break;
        }
        if(key != null && !key.equals("")){
            getSearchResult(itemSelectedNo);
        }
    }

    private void getSearchResult(final int no){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = null;
                    OkHttpClient client = new OkHttpClient();
                    switch (no){
                        case 0:
                            request = new Request.Builder()
                                    .url("http://123.207.161.20/suntong/searchCom.php?search=" + key)
                                    .build();
                            break;
                        case 1:
                            request = new Request.Builder()
                                    .url("http://123.207.161.20/suntong/searchReq.php?search=" + key)
                                    .build();
                            break;
                        case 2:
                            request = new Request.Builder()
                                    .url("http://123.207.161.20/suntong/searchUser.php?search=" + key)
                                    .build();
                            break;
                        default:break;
                    }
                    Response response = null;
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData, no);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainActivity activity = (MainActivity) getActivity();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (no){
                            case 0:
                                comList.clear();
                                comList.addAll(tmpList);
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                comList.clear();
                                comList.addAll(tmpList);
                                adapter.notifyDataSetChanged();
                                break;
                            case 2:
                                userList.clear();
                                userList.addAll(tmpList2);
                                adapter2.notifyDataSetChanged();
                                break;
                            default:break;
                        }

                    }
                });
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData, int no){
        try{
            switch (no){
                case 0:
                case 1:
                    tmpList.clear();
                    JSONArray jsonArray = new JSONArray(jsonData);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String userName = jsonObject.getString("user_name");
                        int comId = jsonObject.getInt("com_id");
                        String type = jsonObject.getString("type");
                        String images = jsonObject.getString("images");
                        double price = jsonObject.getDouble("price");
                        String upTime = jsonObject.getString("up_time");
                        String downTime = jsonObject.getString("down_time");
                        String description = jsonObject.getString("description");
                        CommodityHome msg = new CommodityHome(userName, name, comId, price, type, description, images, upTime, downTime);
                        tmpList.add(msg);
                        Log.d("WebWebWeb", "name: " + name);
                        Log.d("WebWebWeb", "user_name: " + userName);
                        Log.d("WebWebWeb", "type: " + type);
                        Log.d("WebWebWeb", "com_id：" + comId);
                        Log.d("WebWebWeb", "price: " + price);
                        Log.d("WebWebWeb", "up_time: " + upTime);
                        Log.d("WebWebWeb", "down_time: " + downTime);
                        Log.d("WebWebWeb", "description: " + description);
                        Log.d("WebWebWeb", "image: " + images);
                    }
                    break;
                case 2:
                    tmpList2.clear();
                    JSONArray jsonArray2 = new JSONArray(jsonData);
                    for(int i = 0; i < jsonArray2.length(); i++){
                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                        String userName = jsonObject.getString("user_name");
                        String images = jsonObject.getString("head_image");
                        String nickName = jsonObject.getString("nick_name");
                        String gender = jsonObject.getString("sex");
                        double mark = jsonObject.getDouble("mark");
                        String stuID = jsonObject.getString("student_id");
                        String school = jsonObject.getString("school");
                        String phone = jsonObject.getString("phone");
                        String QQ = jsonObject.getString("qq");
                        String weChat = jsonObject.getString("wechat");
                        String email = jsonObject.getString("email");
                        UserHome msg = new UserHome(userName, nickName, gender, mark, stuID, school, images, phone, QQ, weChat, email);
                        tmpList2.add(msg);
                        Log.d("WebWebWeb", "username: " + userName);
                    }
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

package com.erhuo.activitiy_erhuo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.adapter.CustomDatePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mac on 2017/12/2.
 */

public class SellCommodityEdit extends AppCompatActivity {

    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private EditText tv1;
    private EditText tv2;
    private EditText tv3;
    private String name;
    private String price;
    private String description;
    String type;
    String downTime;


    private static final String[] typename={"食品","饮品","书籍","文具用品","数码产品","衣服","鞋类","箱包","化妆用品","体育用品","洗漱用品","杂物"};
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsellcom);

        tv1=(EditText) findViewById(R.id.input_name);
        tv2=(EditText) findViewById(R.id.input_price);
        tv3=(EditText) findViewById(R.id.input_description);

        tv1.getText().toString();
        tv2.getText().toString();
        tv3.getText().toString();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit);
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

        spinner = (Spinner) findViewById(R.id.ComType_spinner);
        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.CommodityType, R.layout.type_spiner_text_item);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
        spinnerAdapter.setDropDownViewResource(R.layout.type_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(spinnerAdapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());



        selectTime = (RelativeLayout) findViewById(R.id.selectTime);

        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
            }
        });
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);

        initDatePicker();

    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            type = typename[arg2];
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);
        currentTime.setText(now);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                downTime = time;
                currentTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    public void httpPost(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_name","")
                            .add("name",name)
                            .add("type",type)
                            .add("price",price)
                            .add("description",description)
                            .add("images","")
                            .add("down_time","downTime")
                            .build();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/zhangbo/commodity.php/add_tommodity.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();


                } catch (IOException e) {

                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(SellCommodityEdit.this, "无网络连接", Toast.LENGTH_SHORT).show();
                          }
                      });
                      e.printStackTrace();
                }
            }
        }).start();
    }

}


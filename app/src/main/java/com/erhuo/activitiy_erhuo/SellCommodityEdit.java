package com.erhuo.activitiy_erhuo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.adapter.CustomDatePicker;

import java.io.File;
import java.io.FileNotFoundException;
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
    private String userName;
    private String name;
    private String price;
    private String description;
    private ImageView picture;
    String type;
    String downTime;
    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;


    private static final String[] typename={"食品","饮品","书籍","文具用品","数码产品","衣服","鞋类","箱包","化妆用品","体育用品","洗漱用品","杂物"};
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsellcom);
        userName = getIntent().getStringExtra("user_name");
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
        tv1=(EditText) findViewById(R.id.input_name);
        tv2=(EditText) findViewById(R.id.input_price);
        tv3=(EditText) findViewById(R.id.input_description);
        //*****************************************
        name = tv1.getText().toString();
        price = tv2.getText().toString();
        description = tv3.getText().toString();
        //*****************************************
        Button takePhoto = (Button) findViewById(R.id.take_photo);
        picture = (ImageView) findViewById(R.id.picture);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                File outputImage = new File(getExternalCacheDir(),
                        "output_image.jpg");
                try {if (outputImage.exists()) { outputImage.delete();
                }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(SellCommodityEdit.this,"com.erhuo.fileprovider", outputImage);
                }
                else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
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

    public void addItem(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_name", userName)
                            .add("name", name)
                            .add("type", type)
                            .add("price", price)
                            .add("description", description)
                            .add("images","")
                            .add("down_time", downTime)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/zhangbo/commodity.php/add_tommodity.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if(responseData.equals("true")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SellCommodityEdit.this, "添加成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SellCommodityEdit.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SellCommodityEdit.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

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


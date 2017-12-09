package com.erhuo.activitiy_erhuo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.erhuo.adapter.CustomDatePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mac on 2017/12/2.
 */

public class SellCommodityReEdit extends AppCompatActivity {

    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private EditText tv1;
    private EditText tv2;
    private EditText tv3;
    private String name;
    private String price;
    private String description;
    private String type;
    private String downTime;
    private String userName;
    private int detail_id;
    private int com_id;
    private ImageView picture;
    private Bitmap bitmap;

    String B_name;
    String B_type;
    String B_typehanzi;
    String B_images;
    String b_images;
    String B_price;
    String B_upTime;
    String B_downTime;
    String B_description;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;

    private String picPath = null;
    private String imagePath = null;

    private File file;



    private static final String[] typename={"食品","饮品","书籍","文具用品","数码产品","衣服","鞋类","箱包","化妆用品","体育用品","洗漱用品","杂物"};
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsellcom);

        userName = getIntent().getStringExtra("user_name");
        com_id = getIntent().getIntExtra("com_id", 0);
        detail_id = getIntent().getIntExtra("detail_id", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_sell);
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


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_name", userName)
                            .add("com_id", Integer.toString(com_id))
                            .build();
                    Log.d("user_name", userName);
                    Log.d("com_id","" + com_id);
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/zhangbo/commodity.php/detail.php")
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    B_name = jsonObject.getString("name");
                    B_type = jsonObject.getString("type");
                    B_images =jsonObject.getString("images");
                    B_price = jsonObject.getString("price");
                    B_upTime = jsonObject.getString("up_time");
                    B_downTime = jsonObject.getString("down_time");
                    B_description = jsonObject.getString("description");
                    Log.d("B_name",B_name);
                    Log.d("B_type",B_type);
                    Log.d("B_price",B_price);
                    Log.d("B_upTime",B_upTime);
                    Log.d("B_images",B_images);
                    Log.d("B_downTime",B_downTime);
                    Log.d("B_description", B_description);
                    Log.d("username",userName);
                    Log.d("detail_id",Integer.toString(detail_id));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText(B_name);
                            tv2.setText(B_price);
                            tv3.setText(B_description);

                            switch(B_type)
                            {
                                case"food":B_typehanzi = "食品";break;
                                case"drink":B_typehanzi = "饮品";break;
                                case"book":B_typehanzi = "书籍";break;
                                case"stationery":B_typehanzi = "文具用品";break;
                                case"digital":B_typehanzi = "数码产品";break;
                                case"clothes":B_typehanzi = "衣服";break;
                                case"shoes":B_typehanzi = "鞋类";break;
                                case"bag":B_typehanzi = "箱包";break;
                                case"cosmetic":B_typehanzi = "化妆用品";break;
                                case"sports":B_typehanzi = "体育用品";break;
                                case"toiletry":B_typehanzi = "洗漱用品";break;
                                case"groceries":B_typehanzi = "杂物";break;
                            }
                            SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
                            int k= apsAdapter.getCount();
                            for(int i=0;i<k;i++){
                                if(B_typehanzi.equals(apsAdapter.getItem(i).toString())){
                                    spinner.setSelection(i,true);// 默认选中项
                                    break;
                                }
                            }

                            initDatePicker(B_downTime, B_upTime);
                            Glide.with(SellCommodityReEdit.this).load("http://123.207.161.20" +B_images).into(picture);
                        }
                    });

                } catch (IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SellCommodityReEdit.this,  "无网络连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        //Button takePhoto = (Button) findViewById(R.id.take_photo);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        picture = (ImageView) findViewById(R.id.picture);

        /*takePhoto.setOnClickListener(new View.OnClickListener() {
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
        });  */

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SellCommodityReEdit.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SellCommodityReEdit.this, new
                            String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1); }
                else {
                    openAlbum();
                }
            }

        });

        tv1=(EditText) findViewById(R.id.input_name);
        tv2=(EditText) findViewById(R.id.input_price);
        tv3=(EditText) findViewById(R.id.input_description);

        tv1.getText().toString();
        tv2.getText().toString();
        tv3.getText().toString();

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
        selectDate.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
               // 日期格式为yyyy-MM-dd
                downTime = currentDate.getText().toString();
                customDatePicker1.show(B_downTime.split(" ")[0]);
            }
        });

        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1=(EditText) findViewById(R.id.input_name);
                tv2=(EditText) findViewById(R.id.input_price);
                tv3=(EditText) findViewById(R.id.input_description);
                name = tv1.getText().toString();
                price = tv2.getText().toString();
                description = tv3.getText().toString();
                if(picPath!=null)
                {
                    file = new File(picPath);
                }
                addItem();

                finish();
            }
        });

    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                } break;
            default:
        }
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
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4 及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4 以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是 document 类型的 Uri，则通过 document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.
                    getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 file 类型的 Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过 Uri 和 selection 来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            Log.d("!!!!!!!!!!!!!imagePath",imagePath);
            picPath = imagePath;
            bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }



    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            switch(typename[arg2])
            {
                case"食品":type = "food";break;
                case"饮品":type = "drink";break;
                case"书籍":type = "book";break;
                case"文具用品":type = "stationery";break;
                case"数码产品":type = "digital";break;
                case"衣服":type = "clothes";break;
                case"鞋类":type = "shoes";break;
                case"箱包":type = "bag";break;
                case"化妆用品":type = "cosmetic";break;
                case"体育用品":type = "sports";break;
                case"洗漱用品":type = "toiletry";break;
                case"杂物":type = "groceries";break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void initDatePicker(String b_downTime, String b_upTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = sdf.format(new Date());
        downTime = now.split(" ")[0];
        //Log.d("!!!!!!!!!!!!now",now);
        //Log.d("!!!!!!!!!!!!now",now);
        //Log.d("!!!!!!!!!!!!B_downtime",b_downTime);
        //Log.d("!!!!!!!!!!!!B_uptime",b_upTime);
        //Log.d("!!!!!!!!!!!!now",now);
        //Log.d("!!!!!!!!!!!!now",now);
        //Log.d("!!!!!!!!!!!!now",now);
        //Log.d("!!!!!!!!!!!!now",now);

        currentDate.setText(b_downTime.split(" ")[0]);
        currentTime.setText(b_upTime);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                downTime = time.split(" ")[0];
                currentDate.setText(time.split(" ")[0]);
            }
        }, now , "2050-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentTime.setText(time);
            }
        }, now , "2050-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    public void addItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("image/jpeg; charset=utf-8");
                    //RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                    OkHttpClient client1 = new OkHttpClient();

                    b_images = B_images;
                    RequestBody requestBody1;
                    if(file!=null){
                        requestBody1 = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("pic", file.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                                .addFormDataPart("oldimages", b_images)
                                .addFormDataPart("name", name)
                                .addFormDataPart("detail_id", Integer.toString(detail_id))
                                .addFormDataPart("type", type)
                                .addFormDataPart("price", price)
                                .addFormDataPart("description", description)
                                .addFormDataPart("down_time", downTime)
                                .build();
                    }
                    else{
                        requestBody1 = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("oldimages", b_images)
                                .addFormDataPart("name", name)
                                .addFormDataPart("detail_id", Integer.toString(detail_id))
                                .addFormDataPart("type", type)
                                .addFormDataPart("price", price)
                                .addFormDataPart("description", description)
                                .addFormDataPart("down_time", downTime)
                                .build();
                    }

                    Log.d("oldimages", b_images);
                    Log.d("oldimages", b_images);
                    Log.d("oldimages", b_images);
                    Log.d("oldimages", b_images);
                    Log.d("oldimages", b_images);
                    Log.d("oldimages", b_images);
                    Log.d("oldimages", b_images);
                    Log.d("oldimages", b_images);

                    Request request1 = new Request.Builder()
                            .url("http://123.207.161.20/zhangbo/req_commodity.php/alter.php")
                            .post(requestBody1)
                            .build();


                    Response response1 = client1.newCall(request1).execute();
                    String responseData1 = response1.body().string();
                    Log.d("responseData1",responseData1);
                    JSONObject jsonObject1 = new JSONObject(responseData1);
                    int success = jsonObject1.getInt("success");
                    Log.d("ADD", Integer.toString(success));
                    if(success == 1){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SellCommodityReEdit.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SellCommodityReEdit.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SellCommodityReEdit.this, "无网络连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}




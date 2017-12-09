package com.erhuo.activitiy_erhuo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registry extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private String userName;
    private String nickName;
    private String password1;
    private String password2;
    private String school;
    private String gender;
    private String id;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
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
        final EditText userNameEdit = (EditText) findViewById(R.id.edit_username);

        final EditText nickNameEdit = (EditText) findViewById(R.id.edit_nickname);

        final EditText password1Edit = (EditText) findViewById(R.id.edit_password1);

        final EditText password2Edit = (EditText) findViewById(R.id.edit_password2);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);
        radioButton1 = (RadioButton) findViewById(R.id.maleGroupID);
        radioButton2 = (RadioButton) findViewById(R.id.femaleGroupID);
        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());
        final EditText schoolEdit = (EditText) findViewById(R.id.edit_school);

        final EditText idEdit = (EditText) findViewById(R.id.edit_studentid);

        final EditText phoneEdit = (EditText) findViewById(R.id.edit_phone);

        Button submit = (Button) findViewById(R.id.submit);
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = userNameEdit.getText().toString();
                nickName = nickNameEdit.getText().toString();
                password1 = password1Edit.getText().toString();
                password2 = password2Edit.getText().toString();
                school = schoolEdit.getText().toString();
                id = idEdit.getText().toString();
                phone = phoneEdit.getText().toString();
                if(userName.equals("") || password1.equals("") || password2.equals("")){
                    Toast.makeText(Registry.this, "用户名或密码不可为空", Toast.LENGTH_SHORT).show();
                    Log.d("REG", userName);
                    Log.d("REG", nickName);
                    Log.d("REG", password1);
                    Log.d("REG", school);
                    Log.d("REG", id);
                    Log.d("REG", phone);
                    Log.d("REG", gender);
                }
                else if(!password1.equals(password2)){
                    Toast.makeText(Registry.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();Log.d("REG", userName);
                    Log.d("REG", nickName);
                    Log.d("REG", password1);
                    Log.d("REG", school);
                    Log.d("REG", id);
                    Log.d("REG", phone);
                    Log.d("REG", gender);

                }
                else{
                    registry();
                    Log.d("REG", userName);
                    Log.d("REG", nickName);
                    Log.d("REG", password1);
                    Log.d("REG", school);
                    Log.d("REG", id);
                    Log.d("REG", phone);
                    Log.d("REG", gender);
                }
            }
        });

    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == radioButton1.getId()){
                gender = "male";
            }
            if(checkedId == radioButton2.getId()){
                gender = "female";
            }
        }
    }

    private void registry(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", userName)
                        .add("nickname", nickName)
                        .add("password", password1)
                        .add("school", school)
                        .add("id", id)
                        .add("sex", gender)
                        .add("phone", phone)
                        .build();
                Request request = new Request.Builder()
                        .url("http://123.207.161.20/jiale/register.php")
                        .post(requestBody)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if(responseData.equals("true")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Registry.this, "注册成功，请登陆", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Registry.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

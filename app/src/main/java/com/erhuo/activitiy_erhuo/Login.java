package com.erhuo.activitiy_erhuo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    EditText ed_id;
    EditText ed_pwd;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.btnlogin);
        ed_id = (EditText) findViewById(R.id.ed_user);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = ed_id.getText().toString();
                password = ed_pwd.getText().toString();
                Log.d("LOGIN", username);
                Log.d("LOGIN", password);
                if(username.equals("") || password.equals("")){
                    Toast.makeText(Login.this, "用户名或密码不可为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    login();
                }
            }
        });
        Button register = (Button) findViewById(R.id.btnreg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registry.class);
                startActivity(intent);
            }
        });

    }

    public void login() {
        new Thread() {
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("username", username)
                            .add("password", password)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://123.207.161.20/jiale/login.php")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    Log.d("LOGIN", responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseData.equals("TRUETRUE")){
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("user_name", username);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Login.this, "登录失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}

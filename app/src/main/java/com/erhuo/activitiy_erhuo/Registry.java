package com.erhuo.activitiy_erhuo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Registry extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private String gender;

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
        EditText userNameEdit = (EditText) findViewById(R.id.edit_username);
        String userName = userNameEdit.getText().toString();
        EditText nickNameEdit = (EditText) findViewById(R.id.edit_nickname);
        String nickName = nickNameEdit.getText().toString();
        EditText password1Edit = (EditText) findViewById(R.id.edit_password1);
        String password1 = password1Edit.getText().toString();
        EditText password2Edit = (EditText) findViewById(R.id.edit_password2);
        String password = password2Edit.getText().toString();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);
        radioButton1 = (RadioButton) findViewById(R.id.maleGroupID);
        radioButton2 = (RadioButton) findViewById(R.id.femaleGroupID);
        EditText schoolEdit = (EditText) findViewById(R.id.edit_school);
        String school = schoolEdit.getText().toString();
        //EditText idEdit
    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == radioButton1.getId()){
                gender = "male";
            }
            else{
                gender = "female";
            }
        }
    }
}

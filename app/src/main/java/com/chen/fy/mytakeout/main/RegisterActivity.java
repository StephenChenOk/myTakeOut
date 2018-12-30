package com.chen.fy.mytakeout.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.UserInfo;
import com.chen.fy.mytakeout.utils.LoginRegisterUtils;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity{

    private EditText userId_et;
    private EditText password_one_et;
    private EditText password_two_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);

        userId_et = findViewById(R.id.id_register);
        password_one_et = findViewById(R.id.password_one_register);
        password_two_et = findViewById(R.id.password_two_register);

        //禁止输入空格
        LoginRegisterUtils.setEditTextInhibitInputSpace(userId_et);
        LoginRegisterUtils.setEditTextInhibitInputSpace(password_one_et);
        LoginRegisterUtils.setEditTextInhibitInputSpace(password_two_et);

        //自动弹出输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        //注册点击事件
        Button register_btn = findViewById(R.id.btn_register);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userId_et.getText().toString();
                //用户名已填入且没有被注册过时
                if(LoginRegisterUtils.userAvailable(userId)){
                    String password1 = password_one_et.getText().toString();
                    String password2 = password_two_et.getText().toString();
                    //判断两次密码是否相等
                    if(LoginRegisterUtils.passwordSame(password1,password2)){
                        //获取系统随机生成的值(盐值),用于加密
                        String pwSalt = UUID.randomUUID().toString().substring(0, 5);
                        //用输入的密码+盐值进行MD5加密
                        String pwHash = LoginRegisterUtils.getMD5(password1 + pwSalt);
                        //存入数据库
                        UserInfo userInfo = new UserInfo();
                        userInfo.setUserName("匿名网友");
                        userInfo.setUserId(userId);
                        userInfo.setPwSalt(pwSalt);
                        userInfo.setPwHash(pwHash);
                        userInfo.save();
                        //注册成功后跳到登入界面
                        Toast.makeText(RegisterActivity.this,"注册成功,可以进行登入操作",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        //标题栏返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package com.chen.fy.mytakeout.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.utils.LoginRegisterUtils;

public class LoginActivity extends AppCompatActivity{

    private EditText userId_et;
    private EditText password_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        userId_et = findViewById(R.id.id_login);
        password_et = findViewById(R.id.password_login);
        //设置过滤器，不能输入空格
        LoginRegisterUtils.setEditTextInhibitInputSpace(userId_et);
        LoginRegisterUtils.setEditTextInhibitInputSpace(password_et);

        //自动弹出输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        //登入点击事件
        Button login_btn = findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userId_et.getText().toString();
                String password = password_et.getText().toString();
                //用户名存在且密码与用户名对应
                if(LoginRegisterUtils.userExisted(userId)&&LoginRegisterUtils.passwordCorrected(userId,password)){
                    Toast.makeText(LoginActivity.this, "登入成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("userId",userId);
                    setResult(RESULT_OK,intent);
                    finish();
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

    //反射右上角菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //点击菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.register_login_menu:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                userId_et.setFocusable(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

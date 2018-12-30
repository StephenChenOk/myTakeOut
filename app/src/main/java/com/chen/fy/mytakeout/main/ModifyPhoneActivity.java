package com.chen.fy.mytakeout.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.UserInfo;
import com.chen.fy.mytakeout.utils.LoginRegisterUtils;

import org.litepal.LitePal;

import java.util.List;

public class ModifyPhoneActivity extends AppCompatActivity {

    private EditText modify_phone_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_phone);

        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_modify_phone);
        setSupportActionBar(toolbar);

        modify_phone_et = findViewById(R.id.modify_phone_et);
        LoginRegisterUtils.setEditTextInhibitInputSpace(modify_phone_et);

        //自动弹出输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

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
        getMenuInflater().inflate(R.menu.modify_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //点击菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify_save:
                String phone = modify_phone_et.getText().toString();
                if (!phone.isEmpty()) {
                    if (phone.length() == 11) {
                        List<UserInfo> userInfos = LitePal.where("userId = ?", MainActivity.userId).find(UserInfo.class);
                        for (UserInfo userInfo : userInfos) {
                            userInfo.setPhone(phone);
                            userInfo.save();
                        }
                        finish();
                    } else {
                        Toast.makeText(this, "电话号码格式不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

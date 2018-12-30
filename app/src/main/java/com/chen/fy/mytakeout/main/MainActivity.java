package com.chen.fy.mytakeout.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.StoreInfo;
import com.chen.fy.mytakeout.fragment.FoundFragment;
import com.chen.fy.mytakeout.fragment.HomeFragment;
import com.chen.fy.mytakeout.fragment.MineFragment;
import com.chen.fy.mytakeout.fragment.OrderFragment;
import com.chen.fy.mytakeout.utils.ImageUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    private HomeFragment homeFragment;
    private FoundFragment foundFragment;
    private OrderFragment orderFragment;
    private MineFragment mineFragment;

    //登入的用户名
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LitePal.getDatabase();
//        File file;
//        file.getAbsoluteFile();

        this.getFilesDir();

        //找到控件对象
        RadioGroup radioGroup = findViewById(R.id.radio_group_main);

        //初始化fragment
        homeFragment = new HomeFragment();
        foundFragment = new FoundFragment();
        orderFragment = new OrderFragment();
        mineFragment = new MineFragment();

        //第一次进入时显示home界面
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_main,homeFragment).
                commitAllowingStateLoss();

        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //底部导航栏按钮选中事件
        RadioButton radioButton = group.findViewById(checkedId);
        radioButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.home_main:
                transaction.replace(R.id.fragment_main,homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case R.id.found_main:
                transaction.replace(R.id.fragment_main,foundFragment);
                transaction.commitAllowingStateLoss();
                break;
            case R.id.order_main:
                transaction.replace(R.id.fragment_main,orderFragment);
                transaction.commitAllowingStateLoss();
                break;
            case R.id.mine_main:
                transaction.replace(R.id.fragment_main,mineFragment);
                transaction.commitAllowingStateLoss();
                break;
        }
    }

    //跳转登录界面,方便返回值
    public void jumpLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    userId = data.getStringExtra("userId");
                }
                break;
        }
    }
}
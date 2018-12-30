package com.chen.fy.mytakeout.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.adapter.MyFragmentPagerAdapter;
import com.chen.fy.mytakeout.entity.EvaluationInfo;
import com.chen.fy.mytakeout.fragment.HomeFragment;

import org.litepal.LitePal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoreActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener,View.OnClickListener{

    private RadioButton radioButton_store_menu;
    private RadioButton radioButton_store_grade;
    private RadioButton radioButton_store_info;
    private ViewPager viewPager;

    //代表界面view的常量
    public static final int PAGER_ONE = 0;
    public static final int PAGER_TWO = 1;
    public static final int PAGER_THREE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_home);

        //顶部导航栏
        RadioGroup radioGroup = findViewById(R.id.radio_group_home_store);
        radioButton_store_menu = findViewById(R.id.radioBtn_store_home_menu);   //便于当滑动时实现对导航栏的checked属性赋值
        radioButton_store_grade = findViewById(R.id.radioBtn_store_home_grade);
        radioButton_store_info = findViewById(R.id.radioBtn_store_home_info);
        radioGroup.setOnCheckedChangeListener(this);

        //配置viewPager
        viewPager = findViewById(R.id.viewPage_home_store);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);   //设置一开始显示的界面
        viewPager.addOnPageChangeListener(this);   //界面改变监听器

        //返回键
        ImageView return_logo = findViewById(R.id.return_logo_store);
        return_logo.setOnClickListener(this);

        //点击搜索TextView跳转搜索界面
        TextView search_tv = findViewById(R.id.search_tv_menu);
        search_tv.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //显示评价次数
        List<EvaluationInfo> evaluationInfos = LitePal.where("storename == ?", HomeFragment.storeName).find(EvaluationInfo.class);
        radioButton_store_grade.setText(MessageFormat.format("评价({0})", evaluationInfos.size()));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        // i 表示界面当前的状态,0表示什么都没做,1正在滑动,2滑动完毕
        if(i==2){
            switch (viewPager.getCurrentItem()){
                case PAGER_ONE:
                    radioButton_store_menu.setChecked(true);
                    break;
                case PAGER_TWO:
                    radioButton_store_grade.setChecked(true);
                    break;
                case PAGER_THREE:
                    radioButton_store_info.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioBtn_store_home_menu: {
                viewPager.setCurrentItem(PAGER_ONE);
            }
            break;
            case R.id.radioBtn_store_home_grade: {
                viewPager.setCurrentItem(PAGER_TWO);
            }
            break;
            case R.id.radioBtn_store_home_info: {
                viewPager.setCurrentItem(PAGER_THREE);
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        //按返回键
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_logo_store:
                finish();
                break;
            case R.id.search_tv_menu:
                Intent intent = new Intent(this, SearchMenuActivity.class);
                startActivity(intent);
                break;
        }
    }
}

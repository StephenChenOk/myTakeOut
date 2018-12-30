package com.chen.fy.mytakeout.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.fragment.HomeFragment;
import com.chen.fy.mytakeout.fragment.OrderFragment;
import com.chen.fy.mytakeout.fragment.StoreEvaluationFragment;
import com.chen.fy.mytakeout.fragment.StoreMenuFragment;
import com.chen.fy.mytakeout.utils.OrderUtils;


import de.hdodenhof.circleimageview.CircleImageView;

public class EvaluationActivity extends AppCompatActivity {

    private String storeName;
    private String menuName;
    private int orderId;
    private EditText evaluationText;
    private CircleImageView store_logo_evaluation;
    private TextView store_name_evaluation;
    private RatingBar ratingBar;    //星级评价条
    private TextView grade_number_tv;  //评分
    private float grade_number = 0;    //分数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation);

        Toolbar toolbar = findViewById(R.id.toolbar_evaluation);
        setSupportActionBar(toolbar);

        evaluationText = findViewById(R.id.evaluation_text);
        store_logo_evaluation = findViewById(R.id.store_logo_evaluation);
        store_name_evaluation = findViewById(R.id.store_name_evaluation);
        ratingBar = findViewById(R.id.rating_bar_evaluation);
        grade_number_tv = findViewById(R.id.grade_number_evaluation);

        //自动弹出输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        //获取店家名
        if (getIntent() != null) {
            storeName = getIntent().getStringExtra("storeName");
            menuName = getIntent().getStringExtra("menuName");
            orderId = getIntent().getIntExtra("orderId", 0);
            store_logo_evaluation.setImageResource(OrderFragment.getStoreLogoId(storeName));
            store_name_evaluation.setText(storeName);
        }

        //星级评价条点击事件
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                grade_number_tv.setText(String.format("评分:%s", rating));
                grade_number = rating;
            }
        });

        //返回
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
        getMenuInflater().inflate(R.menu.evaluation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //点击菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.evaluation:
                String text = evaluationText.getText().toString();
                if (!text.isEmpty()) {
                    OrderUtils.submitEvaluation(storeName, text, menuName, orderId, grade_number);
                    Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "评价不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

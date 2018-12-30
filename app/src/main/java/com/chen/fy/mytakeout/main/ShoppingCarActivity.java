package com.chen.fy.mytakeout.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.adapter.MenuAdapter;
import com.chen.fy.mytakeout.adapter.ShoppingCarAdapter;
import com.chen.fy.mytakeout.application.MyApplication;
import com.chen.fy.mytakeout.entity.MenuInfo;
import com.chen.fy.mytakeout.entity.ShoppingCarInfo;
import com.chen.fy.mytakeout.fragment.HomeFragment;
import com.chen.fy.mytakeout.utils.OrderUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCarActivity extends AppCompatActivity implements View.OnClickListener {

    private List<ShoppingCarInfo> list = new ArrayList<>();
    private TextView totalNumber;

    private ShoppingCarAdapter evaluationAdapter;
    private  GridLayoutManager layoutManager;
    private RecyclerView recyclerView;

    private int TNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shooping_car);

        Toolbar toolbar = findViewById(R.id.toolbar_shopping_car);
        setSupportActionBar(toolbar);

        //找到控件对象
        Button allSelectBtn = findViewById(R.id.all_select_btn_shopping_car);
        Button orderBtn = findViewById(R.id.order_btn_shopping_car);
        totalNumber = findViewById(R.id.total_number_shopping_car);

        intiData();
        evaluationAdapter = new ShoppingCarAdapter(list);
        //找到RecyclerView控件
        recyclerView = findViewById(R.id.listView_shopping_car);
        //用来指定RecycleView的布局方式,这里是卡片式布局的意思
        layoutManager = new GridLayoutManager(this, 1);//1 表示列数
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(evaluationAdapter);

        //按钮点击事件
        allSelectBtn.setOnClickListener(this);
        orderBtn.setOnClickListener(this);

        //返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void intiData() {
        TNumber = 0;
        list.clear();
        if (MainActivity.userId != null) {
            List<ShoppingCarInfo> shoppingCarInfos = LitePal.where("userName = ?", MainActivity.userId).find(ShoppingCarInfo.class);
            list.addAll(shoppingCarInfos);
        }
        //给总计赋值
        for(ShoppingCarInfo shoppingCarInfo:list){
            TNumber = TNumber+shoppingCarInfo.getPrice();
        }
        totalNumber.setText(String.valueOf(TNumber));
    }

    @Override
    public void onClick(View v) {
        int total = 0;
        switch (v.getId()) {
            case R.id.all_select_btn_shopping_car:   //全选按钮
                for (ShoppingCarInfo shoppingCarInfo : list) {
                    total = total + shoppingCarInfo.getPrice();
                }
                totalNumber.setText(String.valueOf(total));
                break;
            case R.id.order_btn_shopping_car:         //结算按钮
                //1 清空购物车
                LitePal.deleteAll(ShoppingCarInfo.class);   //清空购物车数据库
                intiData();
                evaluationAdapter = new ShoppingCarAdapter(list);
                evaluationAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(evaluationAdapter);
                //2生成相应的订单
                for(MenuInfo menuInfo: MenuAdapter.addShoppingCarList) {   //结算后取出加入购物车中的集合
                    OrderUtils.salesMenu(menuInfo.getId());
                    OrderUtils.salesStore(HomeFragment.id);
                    OrderUtils.createOrder(menuInfo.getName(), menuInfo.getPrice());
                }
                Toast.makeText(MyApplication.getContext(), "下单成功!", Toast.LENGTH_SHORT).show();
                MenuAdapter.addShoppingCarList.clear();   //清空集合
                break;

        }
    }

    //反射右上角菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_car,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //点击菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_shopping_car:
                LitePal.deleteAll(ShoppingCarInfo.class);   //清空购物车数据库
                intiData();
                evaluationAdapter = new ShoppingCarAdapter(list);
                evaluationAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(evaluationAdapter);
                MenuAdapter.addShoppingCarList.clear();   //清空集合
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

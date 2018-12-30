package com.chen.fy.mytakeout.main;

import android.annotation.SuppressLint;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.adapter.HomeAdapter;
import com.chen.fy.mytakeout.adapter.ItemClickLister;
import com.chen.fy.mytakeout.entity.StoreInfo;
import com.chen.fy.mytakeout.fragment.HomeFragment;
import com.chen.fy.mytakeout.utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchStoreActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ItemClickLister {

    //商家信息总集合
    private List<StoreInfo> totalList = HomeFragment.list;
    //
    //当前搜索到的数据集合
    private List<StoreInfo> searchList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_store);
        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_search_store);
        setSupportActionBar(toolbar);
        //找到RecyclerView控件
        recyclerView = findViewById(R.id.recyclerView_search_store);
        //用来指定RecycleView的布局方式,这里是卡片式布局的意思
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);//1 表示列数
        recyclerView.setLayoutManager(layoutManager);

        //返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //反射菜单布局
    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home_store_menu, menu);

        //获取search的item对象
        MenuItem search = menu.findItem(R.id.search_toolbar);      //搜索框外的menu对象
        SearchView searchView = (SearchView) search.getActionView();         //SearchView对象

        //找到SearchView中Text中的控件对象
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);

        textView.setTextSize(14);

        // 直接展开搜索框,且当没有输入时右边没有×,有输入时才有
        searchView.onActionViewExpanded();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("请输入商家名");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_toolbar:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //当用户提交搜索结果时激活该方法
        searchList = SearchUtils.searchStoreResult(totalList, query);
        if(!searchList.isEmpty()) {  //当找到对应的商家时
            HomeAdapter homeAdapter = new HomeAdapter(searchList);
            homeAdapter.setItemClickLister(this);
            //关联适配器
            recyclerView.setAdapter(homeAdapter);
            //提醒recyclerView有数据更新
            homeAdapter.notifyDataSetChanged();
        }else{ //当没有找到对应的商家时
            Toast.makeText(this,"没有找到合适的商家",Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // 用户输入字符时激发该方法
        return false;
    }

    @Override
    public void onItemClick(int position) {
        //获取当前点击的商店
        StoreInfo storeInfo = searchList.get(position);
        //获取商店的名字,并以_为标志剪切成数组,以便显示店里的菜单
        String[] storeName = storeInfo.getLogo().split("_");
        HomeFragment.storeLogoName = storeName[0];
        HomeFragment.id = storeInfo.getId();
        Intent intent = new Intent(SearchStoreActivity.this, StoreActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLongClick(int position) {

    }
}

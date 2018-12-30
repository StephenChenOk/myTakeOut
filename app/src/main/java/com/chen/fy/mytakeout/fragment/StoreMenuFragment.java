package com.chen.fy.mytakeout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.adapter.ItemClickLister;
import com.chen.fy.mytakeout.adapter.MenuAdapter;
import com.chen.fy.mytakeout.entity.MenuInfo;
import com.chen.fy.mytakeout.main.ShoppingCarActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import q.rorbin.badgeview.QBadgeView;

public class StoreMenuFragment extends Fragment implements ItemClickLister {

    public static List<MenuInfo> list = new ArrayList<>();

    //通过存在数据库的图片名找到相应图片的集合    不可以直接往数据库中加入资源id,因为当有新的文件导入项目中时,资源id可能发生变化
    private static Map<String, Integer> menuLogoMap = new HashMap<>();

    private QBadgeView qBadgeView;

    //把图片名称与图片资源id一一对应起来
    static {
        //面包店
        menuLogoMap.put("bao_1", R.drawable.bao_1);
        menuLogoMap.put("bao_2", R.drawable.bao_2);
        menuLogoMap.put("bao_3", R.drawable.bao_3);
        menuLogoMap.put("bao_4", R.drawable.bao_4);
        menuLogoMap.put("bao_5", R.drawable.bao_5);
        menuLogoMap.put("bao_6", R.drawable.bao_6);
        menuLogoMap.put("bao_7", R.drawable.bao_7);
        menuLogoMap.put("bao_8", R.drawable.bao_8);
        //汉堡王
        menuLogoMap.put("hanbao_1", R.drawable.hanbao_1);
        menuLogoMap.put("hanbao_2", R.drawable.hanbao_2);
        menuLogoMap.put("hanbao_3", R.drawable.hanbao_3);
        menuLogoMap.put("hanbao_4", R.drawable.hanbao_4);
        menuLogoMap.put("hanbao_5", R.drawable.hanbao_5);
        menuLogoMap.put("hanbao_6", R.drawable.hanbao_6);
        menuLogoMap.put("hanbao_7", R.drawable.hanbao_7);
        menuLogoMap.put("hanbao_8", R.drawable.hanbao_8);
        //快餐
        menuLogoMap.put("kuaican_1", R.drawable.kuaican_1);
        menuLogoMap.put("kuaican_2", R.drawable.kuaican_2);
        menuLogoMap.put("kuaican_3", R.drawable.kuaican_3);
        menuLogoMap.put("kuaican_4", R.drawable.kuaican_4);
        menuLogoMap.put("kuaican_5", R.drawable.kuaican_5);
        menuLogoMap.put("kuaican_6", R.drawable.kuaican_6);
        menuLogoMap.put("kuaican_7", R.drawable.kuaican_7);
        menuLogoMap.put("kuaican_8", R.drawable.kuaican_8);
        menuLogoMap.put("kuaican_9", R.drawable.kuaican_9);
        menuLogoMap.put("kuaican_10", R.drawable.kuaican_10);
        //面馆
        menuLogoMap.put("mian_1", R.drawable.mian_1);
        menuLogoMap.put("mian_2", R.drawable.mian_2);
        menuLogoMap.put("mian_3", R.drawable.mian_3);
        menuLogoMap.put("mian_4", R.drawable.mian_4);
        menuLogoMap.put("mian_5", R.drawable.mian_5);
        menuLogoMap.put("mian_6", R.drawable.mian_6);
        menuLogoMap.put("mian_7", R.drawable.mian_7);
        //奶茶
        menuLogoMap.put("naicha_1", R.drawable.naicha_1);
        menuLogoMap.put("naicha_2", R.drawable.naicha_2);
        menuLogoMap.put("naicha_3", R.drawable.naicha_3);
        menuLogoMap.put("naicha_4", R.drawable.naicha_4);
        menuLogoMap.put("naicha_5", R.drawable.naicha_5);
        menuLogoMap.put("naicha_6", R.drawable.naicha_6);
        menuLogoMap.put("naicha_7", R.drawable.naicha_7);
        menuLogoMap.put("naicha_8", R.drawable.naicha_8);
        //烧烤
        menuLogoMap.put("shaokao_1", R.drawable.shaokao_1);
        menuLogoMap.put("shaokao_2", R.drawable.shaokao_2);
        menuLogoMap.put("shaokao_3", R.drawable.shaokao_3);
        menuLogoMap.put("shaokao_4", R.drawable.shaokao_4);
        menuLogoMap.put("shaokao_5", R.drawable.shaokao_5);
        menuLogoMap.put("shaokao_6", R.drawable.shaokao_6);
        menuLogoMap.put("shaokao_7", R.drawable.shaokao_7);
        menuLogoMap.put("shaokao_8", R.drawable.shaokao_8);
        menuLogoMap.put("shaokao_9", R.drawable.shaokao_9);
        menuLogoMap.put("shaokao_10", R.drawable.shaokao_10);
        //甜品
        menuLogoMap.put("tianpin_1", R.drawable.tianpin_1);
        menuLogoMap.put("tianpin_2", R.drawable.tianpin_2);
        menuLogoMap.put("tianpin_3", R.drawable.tianpin_3);
        menuLogoMap.put("tianpin_4", R.drawable.tianpin_4);
        menuLogoMap.put("tianpin_5", R.drawable.tianpin_5);
        menuLogoMap.put("tianpin_6", R.drawable.tianpin_6);
        menuLogoMap.put("tianpin_7", R.drawable.tianpin_7);
        //小吃店
        menuLogoMap.put("xiaochi_1", R.drawable.xiaochi_1);
        menuLogoMap.put("xiaochi_2", R.drawable.xiaochi_2);
        menuLogoMap.put("xiaochi_3", R.drawable.xiaochi_3);
        menuLogoMap.put("xiaochi_4", R.drawable.xiaochi_4);
        menuLogoMap.put("xiaochi_5", R.drawable.xiaochi_5);
        menuLogoMap.put("xiaochi_6", R.drawable.xiaochi_6);
        menuLogoMap.put("xiaochi_7", R.drawable.xiaochi_7);
        menuLogoMap.put("xiaochi_8", R.drawable.xiaochi_8);
        menuLogoMap.put("xiaochi_9", R.drawable.xiaochi_9);
        menuLogoMap.put("xiaochi_10", R.drawable.xiaochi_10);
        menuLogoMap.put("xiaochi_11", R.drawable.xiaochi_11);
        menuLogoMap.put("xiaochi_12", R.drawable.xiaochi_12);
        //粥
        menuLogoMap.put("zhou_1", R.drawable.zhou_1);
        menuLogoMap.put("zhou_2", R.drawable.zhou_2);
        menuLogoMap.put("zhou_3", R.drawable.zhou_3);
        menuLogoMap.put("zhou_4", R.drawable.zhou_4);
        menuLogoMap.put("zhou_5", R.drawable.zhou_5);
        menuLogoMap.put("zhou_6", R.drawable.zhou_6);
        menuLogoMap.put("zhou_7", R.drawable.zhou_7);
        menuLogoMap.put("zhou_8", R.drawable.zhou_8);
        menuLogoMap.put("zhou_9", R.drawable.zhou_9);
        //家常菜
        menuLogoMap.put("jiacai_1", R.drawable.jiacai_1);
        menuLogoMap.put("jiacai_2", R.drawable.jiacai_2);
        menuLogoMap.put("jiacai_3", R.drawable.jiacai_3);
        menuLogoMap.put("jiacai_4", R.drawable.jiacai_4);
        menuLogoMap.put("jiacai_5", R.drawable.jiacai_5);
        menuLogoMap.put("jiacai_6", R.drawable.jiacai_6);
        menuLogoMap.put("jiacai_7", R.drawable.jiacai_7);
        menuLogoMap.put("jiacai_8", R.drawable.jiacai_8);

    }

    //获取相应的图片资源id
    public static Integer getMenuLogoId(String menuName) {
        //如果集合中有这个图片,则返回其资源id
        if (menuLogoMap.containsKey(menuName)) {
            return menuLogoMap.get(menuName);
        }
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_menu_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //购物车
        FloatingActionButton floatingActionButton = view.findViewById(R.id.shopping_car);
        qBadgeView = new QBadgeView(getContext());
        qBadgeView.bindTarget(floatingActionButton);

        //获取数据
        initMenuInfo();
        //创建RecyclerView对象
        RecyclerView recyclerView = view.findViewById(R.id.listView_store_menu);
        //用来指定RecycleView的布局方式,这里是线性布局的意思
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MenuAdapter menuAdapter = new MenuAdapter(list, qBadgeView);
        menuAdapter.setItemClickLister(this);
        //关联适配器
        recyclerView.setAdapter(menuAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShoppingCarActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化数据
    private void initMenuInfo() {
        list.clear();
        //模糊查询
        List<MenuInfo> menuInfos = LitePal.where("logoName like ?", HomeFragment.storeLogoName + "%").find(MenuInfo.class);
        list.addAll(menuInfos);
    }

    @Override
    public void onResume() {
        super.onResume();
        qBadgeView.setBadgeNumber(MenuAdapter.addShoppingCarList.size());
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "short", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(int position) {
        Toast.makeText(getContext(), "long", Toast.LENGTH_SHORT).show();
    }
}

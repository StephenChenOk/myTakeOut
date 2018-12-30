package com.chen.fy.mytakeout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.adapter.OrderAdapter;
import com.chen.fy.mytakeout.comparator.OrderDataComparator;
import com.chen.fy.mytakeout.entity.OrderInfo;
import com.chen.fy.mytakeout.main.MainActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {

    //订单集合
    private List<OrderInfo> list = new ArrayList<>();
    private RecyclerView recyclerView;
    //订单时间排序比较器
    private OrderDataComparator orderDataComparator = new OrderDataComparator();

    //通过存在数据库的图片名找到相应图片的集合    不可以直接往数据库中加入资源id,因为当有新的文件导入项目中时,资源id可能发生变化
    private static Map<String, Integer> storeLogoMap = new HashMap<>();

    //把图片名称与图片资源id一一对应起来
    static {
        storeLogoMap.put("热腾腾包子店", R.drawable.bao_one_store);
        storeLogoMap.put("汉堡王", R.drawable.hanbao_one_store);
        storeLogoMap.put("一生好味道", R.drawable.kuaican_one_store);
        storeLogoMap.put("好面来", R.drawable.mian_one_store);
        storeLogoMap.put("奶茶博士", R.drawable.naicha_one_store);
        storeLogoMap.put("串串香烧烤", R.drawable.shaokao_one_store);
        storeLogoMap.put("饭后小甜品", R.drawable.tianpin_one_store);
        storeLogoMap.put("正宗沙县小吃", R.drawable.xiaochi_one_store);
        storeLogoMap.put("好粥到", R.drawable.zhou_one_store);
        storeLogoMap.put("经典家常菜", R.drawable.jiacai_one_store);
    }

    //获取相应的图片资源id
    public static Integer getStoreLogoId(String storeName) {
        //如果集合中有这个图片,则返回其资源id
        if (storeLogoMap.containsKey(storeName)) {
            return storeLogoMap.get(storeName);
        }
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView_order);
        //用来指定RecycleView的布局方式,这里是卡片式布局的意思
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);//1 表示列数
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取数据
        intiData();
        //适配器初始化
        OrderAdapter orderAdapter = new OrderAdapter(list,getContext());
        recyclerView.setAdapter(orderAdapter);
    }

    //数据初始化
    public void intiData() {
        list.clear();
        if (MainActivity.userId != null) {
            if(!MainActivity.userId.isEmpty()) {
                List<OrderInfo> orderInfos = LitePal.where("userId == ?", MainActivity.userId).find(OrderInfo.class);
                list.addAll(orderInfos);
                Collections.sort(list,orderDataComparator);
            }
        }
    }
}

package com.chen.fy.mytakeout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.adapter.HomeAdapter;
import com.chen.fy.mytakeout.adapter.ItemClickLister;
import com.chen.fy.mytakeout.comparator.AverageComparator;
import com.chen.fy.mytakeout.comparator.DistanceComparator;
import com.chen.fy.mytakeout.comparator.SalesComparator;
import com.chen.fy.mytakeout.entity.StoreInfo;
import com.chen.fy.mytakeout.main.SearchStoreActivity;
import com.chen.fy.mytakeout.main.StoreActivity;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements ItemClickLister, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    //商店图标名称
    public static String storeLogoName;
    //商店名称
    public static String storeName;
    //商店id
    public static int id;
    //商店店铺数据集合
    public static List<StoreInfo> list = new ArrayList<>();

    private List<StoreInfo> defaultList = new ArrayList<>();
    //排序比较器
    private SalesComparator salesComparator = new SalesComparator();
    private DistanceComparator distanceComparator = new DistanceComparator();
    private AverageComparator averageComparator = new AverageComparator();

    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;

    //通过存在数据库的图片名找到相应图片的集合    不可以直接往数据库中加入资源id,因为当有新的文件导入项目中时,资源id可能发生变化
    private static Map<String, Integer> storeLogoMap = new HashMap<>();

    //把图片名称与图片资源id一一对应起来
    static {
        storeLogoMap.put("bao_one_store", R.drawable.bao_one_store);
        storeLogoMap.put("hanbao_one_store", R.drawable.hanbao_one_store);
        storeLogoMap.put("kuaican_one_store", R.drawable.kuaican_one_store);
        storeLogoMap.put("mian_one_store", R.drawable.mian_one_store);
        storeLogoMap.put("naicha_one_store", R.drawable.naicha_one_store);
        storeLogoMap.put("shaokao_one_store", R.drawable.shaokao_one_store);
        storeLogoMap.put("tianpin_one_store", R.drawable.tianpin_one_store);
        storeLogoMap.put("xiaochi_one_store", R.drawable.xiaochi_one_store);
        storeLogoMap.put("zhou_one_store", R.drawable.zhou_one_store);
        storeLogoMap.put("jiacai_one_store", R.drawable.jiacai_one_store);
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
        View view = inflater.inflate(R.layout.home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //view 已经成功建成
        super.onViewCreated(view, savedInstanceState);

        //找到RecyclerView控件
        recyclerView = view.findViewById(R.id.recyclerView_home);
        //找到商家排序按钮组控件
        RadioGroup radioGroup = view.findViewById(R.id.home_radio_group);
        //找到搜索框对象
        TextView search_tv = view.findViewById(R.id.search_tv_home);
        search_tv.setOnClickListener(this);

        //用来指定RecycleView的布局方式,这里是卡片式布局的意思
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);//1 表示列数
        recyclerView.setLayoutManager(layoutManager);

        //点击商家排序按钮
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {

//        this.getActivity().getFilesDir();
//        File file;
//        file.getAbsoluteFile();

        //每次从菜单活动跳转到home中时都会重新刷新
        super.onResume();
        intiStoreInfo();
        homeAdapter = new HomeAdapter(list);
        homeAdapter.setItemClickLister(this);
        //关联适配器
        recyclerView.setAdapter(homeAdapter);
        //提醒recyclerView有数据更新
        homeAdapter.notifyDataSetChanged();
    }

    //准备数据
    public void intiStoreInfo() {
        list.clear();
        //从数据库中载入数据
        List<StoreInfo> storeInfos = LitePal.findAll(StoreInfo.class);
        //往list集合中添加数据
        list.addAll(storeInfos);
        //初始化综合排序集合
        if(defaultList.isEmpty()) {
            defaultList.addAll(storeInfos);
        }
    }

    @Override
    //点击item
    public void onItemClick(int position) {
        //获取当前点击的商店
        StoreInfo storeInfo = list.get(position);
        //获取商店的名字,并以_为标志剪切成数组,以便显示店里的菜单
        String[] storeLogeNames = storeInfo.getLogo().split("_");
        storeLogoName = storeLogeNames[0];
        storeName = storeInfo.getName();
        id = storeInfo.getId();
        //跳转到店家Activity
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), StoreActivity.class);
            startActivity(intent);
        }
    }

    @Override
    //长按item
    public void onLongClick(int position) {
        if (getContext() != null) {
            //Toast.makeText(getContext(), "long", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    //RadioGroup监听事件
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //商家排序按钮
        RadioButton radioButton = group.findViewById(checkedId);
        radioButton.setOnClickListener(this);
    }

    @Override
    //排序按钮
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_tv_home:
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), SearchStoreActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.default_home_btn:   //综合排序
                // list = defaultList;    //不可以这样,这样表示这两个变量指向同一个对象
                list.clear();
                list.addAll(defaultList);
                break;
            case R.id.sales_home_btn:   //月售排
                Collections.sort(list, salesComparator);
                break;
            case R.id.distance_home_btn:   //距离排序
                Collections.sort(list, distanceComparator);
                break;
            case R.id.average_home_btn:   //人均排序
                Collections.sort(list, averageComparator);
                break;
        }
        homeAdapter = new HomeAdapter(list);
        homeAdapter.setItemClickLister(this);
        //关联适配器
        recyclerView.setAdapter(homeAdapter);
        //提醒recyclerView有数据更新
        homeAdapter.notifyDataSetChanged();
    }

}

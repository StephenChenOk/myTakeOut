package com.chen.fy.mytakeout.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.chen.fy.mytakeout.adapter.EvaluationAdapter;
import com.chen.fy.mytakeout.comparator.EvaluationDataComparator;
import com.chen.fy.mytakeout.entity.EvaluationInfo;
import com.chen.fy.mytakeout.main.MainActivity;
import com.chen.fy.mytakeout.utils.ImageUtils;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreEvaluationFragment extends Fragment {

    private List<EvaluationInfo> list = new ArrayList<>();
    //评价时间排序比较器
    EvaluationDataComparator evaluationDataComparator = new EvaluationDataComparator();

    //通过存在数据库的图片名找到相应图片的集合    不可以直接往数据库中加入资源id,因为当有新的文件导入项目中时,资源id可能发生变化
    private static Map<String, Integer> userIconMap = new HashMap<>();

    //把图片名称与图片资源id一一对应起来
    static {
        userIconMap.put("user_1",R.drawable.user_1);
        userIconMap.put("user_2",R.drawable.user_2);
        userIconMap.put("user_3",R.drawable.user_3);
        userIconMap.put("user_4",R.drawable.user_4);
        userIconMap.put("user_5",R.drawable.user_5);
        userIconMap.put("user_6",R.drawable.user_6);
        userIconMap.put("user_7",R.drawable.user_7);
        userIconMap.put("user_8",R.drawable.user_8);
        userIconMap.put("user_9",R.drawable.user_9);
        userIconMap.put("user_10",R.drawable.user_10);
        userIconMap.put("user_11",R.drawable.user_11);
        userIconMap.put("陈一生",R.drawable.user_mine);
    }

    //获取相应的图片资源id
    public static Integer getUserHeadIcon(String userName) {
        //如果集合中有这个图片,则返回其资源id
        if (userIconMap.containsKey(userName)) {
            return userIconMap.get(userName);
        }
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_grade_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        EvaluationAdapter evaluationAdapter;
//        Bitmap headIcon = ImageUtils.getHeadIcon(MainActivity.userId);
//        //初始化数据
//        intiData();
//        //获取外部存储位置的uri
//        File file = new File(getContext().getExternalFilesDir(null), MainActivity.userId + ".jpg");
//        Uri headIconUri = Uri.fromFile(file);
//        try {
//            headIcon = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(headIconUri));
//        } catch (FileNotFoundException e) {
//            headIcon = BitmapFactory.decodeResource(getResources(),R.drawable.user_mine);
//        }
        intiData();
        EvaluationAdapter evaluationAdapter = new EvaluationAdapter(list);
        //找到RecyclerView控件
        RecyclerView recyclerView = view.findViewById(R.id.listView_store_evaluation);
        //用来指定RecycleView的布局方式,这里是卡片式布局的意思
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);//1 表示列数
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(evaluationAdapter);
    }

    public void intiData(){
        list.clear();
        List<EvaluationInfo> evaluationInfos = LitePal.where("storename == ?", HomeFragment.storeName).find(EvaluationInfo.class);
        list.addAll(evaluationInfos);
        Collections.sort(list,evaluationDataComparator);
    }
}

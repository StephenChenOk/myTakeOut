package com.chen.fy.mytakeout.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.UserInfo;
import com.chen.fy.mytakeout.main.MainActivity;
import com.chen.fy.mytakeout.main.MyInfoActivity;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends Fragment implements View.OnClickListener {

    private String userId;
    private TextView user_name_mine;
    private TextView info_menu_mine;
    private CircleImageView headIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //找到各个控件对象
        user_name_mine = view.findViewById(R.id.user_name_mine);
        info_menu_mine = view.findViewById(R.id.info_menu_mine);
        headIcon = view.findViewById(R.id.head_icon_mine);

        LinearLayout wallet_box = view.findViewById(R.id.wallet_mine_box);
        LinearLayout address_box = view.findViewById(R.id.address_mine_box);
        LinearLayout collection_box = view.findViewById(R.id.collection_mine_box);
        LinearLayout evaluation_box = view.findViewById(R.id.evaluation_mine_box);
        LinearLayout set_box = view.findViewById(R.id.set_mine_box);

        //点击接口
        user_name_mine.setOnClickListener(this);
        info_menu_mine.setOnClickListener(this);
        headIcon.setOnClickListener(this);

        wallet_box.setOnClickListener(this);
        address_box.setOnClickListener(this);
        collection_box.setOnClickListener(this);
        evaluation_box.setOnClickListener(this);
        set_box.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        userId = MainActivity.userId;
        //赋值
        if (userId != null && !userId.isEmpty()) {
            List<UserInfo> userInfos = LitePal.where("userId = ?", userId).find(UserInfo.class);
            for (UserInfo userInfo : userInfos) {
                user_name_mine.setText(userInfo.getUserName());
                info_menu_mine.setText("个人信息>");
            }
        } else {
            user_name_mine.setText("登入/注册");
            info_menu_mine.setText("");
        }

        //头像加载
        File file = new File(getContext().getExternalFilesDir(null), MainActivity.userId + ".jpg");
        Uri headIconUri = Uri.fromFile(file);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(headIconUri));
            headIcon.setImageBitmap(bitmap);                    //如果上面产生文件存在异常，则不执行
        } catch (FileNotFoundException e) {
            headIcon.setImageResource(R.drawable.user_mine);   //捕获异常后，设置头像为默认头像，程序继续执行
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_name_mine: //实现一样的点击效果
            case R.id.info_menu_mine:
            case R.id.head_icon_mine:
                //当还没登入时
                if (userId == null || userId.isEmpty()) {
                    if (getActivity() != null) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.jumpLoginActivity();
                    }
                } else {
                    //已经登入时,进入我的信息界面
                    Intent intent = new Intent(getContext(), MyInfoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.wallet_mine_box:{
                break;
            }
            case R.id.address_mine_box:{
                break;
            }
            case R.id.collection_mine_box:{
                break;
            }
            case R.id.evaluation_mine_box:{
                break;
            }
            case R.id.set_mine_box:{
                break;
            }
        }
    }
}

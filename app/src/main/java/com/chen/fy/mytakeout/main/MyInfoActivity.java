package com.chen.fy.mytakeout.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.UserInfo;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.PermissionManager;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends TakePhotoActivity implements View.OnClickListener {

    private TextView name_my_info_tv;
    private TextView id_my_info_tv;
    private TextView phone_my_info_tv;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private CropOptions cropOptions;
    private Uri headIconUri;
    private CircleImageView headIcon;

    private Button take_photo;
    private Button chosen_photo;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info);

        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_my_info);
        //  setSupportActionBar(toolbar);


        //获得对象
        takePhoto = getTakePhoto();

        //找到控件
        LinearLayout head_icon_my_info_box = findViewById(R.id.head_icon_my_info_box);
        LinearLayout name_my_info_box = findViewById(R.id.name_my_info_box);
        LinearLayout id_my_info_box = findViewById(R.id.id_my_info_box);
        LinearLayout phone_my_info_box = findViewById(R.id.phone_my_info_box);
        Button out_btn = findViewById(R.id.out_my_info);
        name_my_info_tv = findViewById(R.id.name_my_info);
        id_my_info_tv = findViewById(R.id.id_my_info);
        phone_my_info_tv = findViewById(R.id.phone_my_info);
        headIcon = findViewById(R.id.head_icon_my_info);

        //点击事件
        head_icon_my_info_box.setOnClickListener(this);
        name_my_info_box.setOnClickListener(this);
        id_my_info_box.setOnClickListener(this);
        phone_my_info_box.setOnClickListener(this);
        out_btn.setOnClickListener(this);

        //获取外部存储位置的uri
        File file = new File(getExternalFilesDir(null), MainActivity.userId + ".jpg");
        headIconUri = Uri.fromFile(file);

        //进行图片剪切
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(true).create();  //true表示使用TakePhoto自带的裁剪工具

        /***
         *  初始化点击头像后生成的选择框
         */
        LayoutInflater inflater = LayoutInflater.from(this);
        //反射一个自定义的全新的对话框布局
        View view = inflater.inflate(R.layout.head_icon_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        //在当前布局中找到控件对象
        take_photo = view.findViewById(R.id.take_photo);
        chosen_photo = view.findViewById(R.id.chosen_photo);
        //监听事件
        take_photo.setOnClickListener(this);
        chosen_photo.setOnClickListener(this);

        //标题栏返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //头像加载
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(headIconUri));
            headIcon.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            headIcon.setImageResource(R.drawable.user_mine);
        }

        //给控件赋值
        List<UserInfo> userInfos = LitePal.where("userId = ?", MainActivity.userId).find(UserInfo.class);
        for (UserInfo userInfo : userInfos) {
            name_my_info_tv.setText(userInfo.getUserName());
            id_my_info_tv.setText(userInfo.getUserId());
            phone_my_info_tv.setText(userInfo.getPhone());
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {   //点击头像
            case R.id.head_icon_my_info_box:
                dialog.show();
                break;
            case R.id.take_photo: {  //点击拍照
                //相机获取照片并剪裁
                takePhoto.onPickFromCaptureWithCrop(headIconUri, cropOptions);
                // takePhoto.onPickFromCapture(uri);
                dialog.dismiss();
                break;
            }
            case R.id.chosen_photo: {  //点击相册
                //相册获取照片并剪裁
                takePhoto.onPickFromGalleryWithCrop(headIconUri, cropOptions);
                // takePhoto.onPickFromGallery();
                dialog.dismiss();
                break;
            }
            case R.id.name_my_info_box:
                intent = new Intent(this, ModifyNameActivity.class);
                startActivity(intent);
                break;
            case R.id.id_my_info_box:
                Toast.makeText(this, "账号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.phone_my_info_box:
                intent = new Intent(this, ModifyPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.out_my_info:
                MainActivity.userId = "";
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        //将拍摄的照片显示出来
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(headIconUri));
            //imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

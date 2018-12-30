package com.chen.fy.mytakeout.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.application.MyApplication;
import com.chen.fy.mytakeout.main.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 图片操作工具类
 */
public class ImageUtils {

    /**
     * Bitmap 转换成 byte[]
     * @param bitmap bitmap
     * @return   二进制图片
     */
    public static byte[] getBytes(Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();

    }

    /**
     * byte[] 转化成 Bitmap
     * @param b 二进制图片
     * @return bitmap
     */
    public static Bitmap getBitmap(byte[] b) {

        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }

    }

    /**
     * 获取用户的头像
     * @param userId   用户名
     * @return   头像的bitMap类型
     */
    public static Bitmap getHeadIcon(String userId){
        //获取外部存储位置的uri
        Bitmap headIcon;
        File file = new File(MyApplication.getContext().getExternalFilesDir(null), userId + ".jpg");
        Uri headIconUri = Uri.fromFile(file);
        try {
            headIcon = BitmapFactory.decodeStream(MyApplication.getContext().getContentResolver().openInputStream(headIconUri));
        } catch (FileNotFoundException e) {
            headIcon = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.user_mine);
        }
        return headIcon;
    }

}

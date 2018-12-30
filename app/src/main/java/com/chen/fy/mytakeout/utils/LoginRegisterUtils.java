package com.chen.fy.mytakeout.utils;

import android.support.v4.view.ViewCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.chen.fy.mytakeout.application.MyApplication;
import com.chen.fy.mytakeout.entity.UserInfo;

import org.litepal.LitePal;

import java.security.MessageDigest;
import java.util.List;

/**
 * 登入/注册账号时的工具类
 */
public class LoginRegisterUtils {

    //注册账号时判断账号是否可以使用
    public static boolean userAvailable(String userId) {
        if (userId.isEmpty()) {
            Toast.makeText(MyApplication.getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<UserInfo> userInfos = LitePal.select("userId").find(UserInfo.class);
        for (UserInfo userInfo : userInfos) {
            if (userInfo.getUserId().equals(userId)) {
                //如果账号已经存在
                Toast.makeText(MyApplication.getContext(), "账号已经存在", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    //注册时判断密码输入是否一致
    public static boolean passwordSame(String password1, String password2) {
        if (password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(MyApplication.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password1.equals(password2)) {
            //密码相等
            return true;
        } else {
            Toast.makeText(MyApplication.getContext(), "两次密码不相等", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //登入时判断是否有当前用户名
    public static boolean userExisted(String userId) {
        if (userId.isEmpty()) {
            Toast.makeText(MyApplication.getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<UserInfo> userInfos = LitePal.select("userId").find(UserInfo.class);
        for (UserInfo userInfo : userInfos) {
            if (userId.equals(userInfo.getUserId())) {
                return true;
            }
        }
        Toast.makeText(MyApplication.getContext(), "此用户名不存在", Toast.LENGTH_SHORT).show();
        return false;
    }

    //登入时判断密码是否与用户名对应
    public static boolean passwordCorrected(String userId, String password) {
        if (password.isEmpty()) {
            Toast.makeText(MyApplication.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<UserInfo> userInfos = LitePal.where("userId = ?", userId).find(UserInfo.class);
        for (UserInfo userInfo : userInfos) {
            //判断密码是否相同
            if (userInfo.getPwHash().equals(getMD5(password + userInfo.getPwSalt()))) {
                return true;
            }
        }
        Toast.makeText(MyApplication.getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
        return false;
    }

    //MD5加密密码
    public static String getMD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}

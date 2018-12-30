package com.chen.fy.mytakeout.utils;

import android.annotation.SuppressLint;

import com.chen.fy.mytakeout.adapter.MenuAdapter;
import com.chen.fy.mytakeout.entity.EvaluationInfo;
import com.chen.fy.mytakeout.entity.MenuInfo;
import com.chen.fy.mytakeout.entity.OrderInfo;
import com.chen.fy.mytakeout.entity.ShoppingCarInfo;
import com.chen.fy.mytakeout.entity.StoreInfo;
import com.chen.fy.mytakeout.entity.UserInfo;
import com.chen.fy.mytakeout.fragment.HomeFragment;
import com.chen.fy.mytakeout.main.MainActivity;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * 下单/订单界面工具类
 */
public class OrderUtils {

    /**
     * 商家月售更新
     */
    public static void salesStore(int storeId) {
//        //获取要更新的商家
        List<StoreInfo> storeInfos = LitePal.where("id == " + storeId).find(StoreInfo.class);
        for (StoreInfo storeInfo : storeInfos) {
            //月售加一
            storeInfo.setSales(storeInfo.getSales() + 1);
            //更新
            storeInfo.save();
        }
    }

    /**
     * 菜单月售更新
     */
    public static void salesMenu(int menuId) {
        //获取要更新的商家
        List<MenuInfo> menuInfos = LitePal.where("id == " + menuId).find(MenuInfo.class);
        for (MenuInfo menuInfo : menuInfos) {
            //月售加一
            menuInfo.setSales(menuInfo.getSales() + 1);
            //更新
            menuInfo.save();
        }
    }

    /**
     * 加入购物车
     * @param menuName    菜单名
     * @param menuLogo    菜单图片
     * @param price       菜单价格
     */
    public static void addShoppingCar(String menuName, String menuLogo, int price) {
        ShoppingCarInfo shoppingCarInfo = new ShoppingCarInfo();
        shoppingCarInfo.setUserName(MainActivity.userId);
        shoppingCarInfo.setStoreName(HomeFragment.storeName);
        shoppingCarInfo.setMenuLogo(menuLogo);
        shoppingCarInfo.setMenuName(menuName);
        shoppingCarInfo.setPrice(price);
        shoppingCarInfo.save();
    }

    /**
     * 购物车右上角的数字显示
     * @param qBadgeView   数字显示控件
     */
    public static void showShoppingCarNumber(QBadgeView qBadgeView){
        qBadgeView.setBadgeNumber(MenuAdapter.addShoppingCarList.size());
    }

    /**
     * 点击下单后生成一个订单
     */
    public static void createOrder(String menuName, int price) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStoreName(HomeFragment.storeName);
        orderInfo.setMenuName(menuName);
        orderInfo.setPrice(price);
        orderInfo.setOrderType(false);
        orderInfo.setEvaluationType(false);
        orderInfo.setUserId(MainActivity.userId);
        orderInfo.setOrderLongDate(System.currentTimeMillis());
        orderInfo.save();
    }

    /**
     * 评价提交
     *
     * @param storeName      店家名
     * @param evaluationText 评价文本
     * @param menuName       所点的东西
     * @param orderId        订单id
     */
    public static void submitEvaluation(String storeName, String evaluationText,
                                        String menuName, int orderId, float grade_number) {
        //评价提交后在商家的评价界面中创建一条评价
        EvaluationInfo evaluationInfo;
        evaluationInfo = new EvaluationInfo();
        //店名
        evaluationInfo.setStoreName(storeName);
        //用户名
        List<UserInfo> userInfos = LitePal.where("userId = ?", MainActivity.userId).find(UserInfo.class);
        for (UserInfo userInfo : userInfos) {
            evaluationInfo.setUserId(MainActivity.userId);
            evaluationInfo.setUserName(userInfo.getUserName());
        }
        //评价时间
        evaluationInfo.setEvaluationLongDate(System.currentTimeMillis());
        //评价分数
        evaluationInfo.setGradeEvaluation(grade_number);
        //评价文本
        evaluationInfo.setTextEvaluation(evaluationText);
        //菜单
        evaluationInfo.setMenu(menuName);
        evaluationInfo.save();

        //同时订单界面的相应项改为已评价
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setEvaluationType(true);
        orderInfo.updateAll("id == " + orderId);
    }

    /**
     * 时间格式转化1( 毫秒数 转为 年.月.日 )
     *
     * @param time 当前评价的时间
     * @return 格式化好的日期格式
     */
    public static String dateStringChange(long time) {
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 时间格式转化2( 年.月.日 转化为毫秒数)
     *
     * @param time 当前评价的时间
     * @return 格式化好的毫秒数
     */
    public static long dateLongChange(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd"); //格式需要一样
        Date date = null;
        try {
            date = dateFormat.parse(time);     //解析
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}

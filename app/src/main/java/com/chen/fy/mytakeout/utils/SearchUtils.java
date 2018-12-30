package com.chen.fy.mytakeout.utils;

import com.chen.fy.mytakeout.entity.MenuInfo;
import com.chen.fy.mytakeout.entity.StoreInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索工具类
 */
public class SearchUtils {

    /**
     * 搜索商家结果
     */
    public static List<StoreInfo> searchStoreResult(List<StoreInfo> list, String search){
        List<StoreInfo> result = new ArrayList();
        //遍历查找商店名有没有符合的
        for(int i=0;i<list.size();i++){
            StoreInfo storeInfo = list.get(i);
            if(storeInfo.getName().toLowerCase().contains(search.toLowerCase())){ //比较
                result.add(storeInfo);
            }
        }
        return result;
    }

    /**
     * 搜索菜单结果
     */
    public static List<MenuInfo> searchMenuResult(List<MenuInfo> list, String search){
        List<MenuInfo> result = new ArrayList<MenuInfo>();
        //遍历查找商店名有没有符合的
        for(int i=0;i<list.size();i++){
            MenuInfo menuInfo = list.get(i);
            if(menuInfo.getName().toLowerCase().contains(search.toLowerCase())){ //比较
                result.add(menuInfo);
            }
        }
        return result;
    }


}

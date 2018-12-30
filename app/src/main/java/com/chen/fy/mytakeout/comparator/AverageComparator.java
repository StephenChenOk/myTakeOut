package com.chen.fy.mytakeout.comparator;

import com.chen.fy.mytakeout.entity.StoreInfo;
import java.util.Comparator;

/**
 * 人均比较器
 */
public class AverageComparator implements Comparator<StoreInfo> {
    @Override
    public int compare(StoreInfo storeInfo1, StoreInfo storeInfo2) {
        if(storeInfo1.getAverage()>storeInfo2.getAverage()){
            return 1;
        }else if(storeInfo1.getAverage() == storeInfo2.getAverage()){
            return 0;
        }else{
            return -1;
        }
    }
}

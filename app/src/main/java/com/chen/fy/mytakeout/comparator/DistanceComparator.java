package com.chen.fy.mytakeout.comparator;

import com.chen.fy.mytakeout.entity.StoreInfo;
import java.util.Comparator;

/**
 * 距离比较器
 */
public class DistanceComparator implements Comparator<StoreInfo> {
    @Override
    public int compare(StoreInfo storeInfo1, StoreInfo storeInfo2) {
        if(storeInfo1.getDistance()>storeInfo2.getDistance()){
            return 1;
        }else if(storeInfo1.getDistance() == storeInfo2.getDistance()){
            return 0;
        }else{
            return -1;
        }
    }
}

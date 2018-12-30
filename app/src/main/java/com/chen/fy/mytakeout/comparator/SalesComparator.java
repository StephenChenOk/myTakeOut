package com.chen.fy.mytakeout.comparator;

import com.chen.fy.mytakeout.entity.StoreInfo;

import java.util.Comparator;

/**
 * 月售比较器
 */
public class SalesComparator implements Comparator<StoreInfo> {
    @Override
    public int compare(StoreInfo storeInfo1, StoreInfo storeInfo2) {
        if (storeInfo1.getSales() < storeInfo2.getSales()) {
            return 1;
        } else if (storeInfo1.getSales() == storeInfo2.getSales()) {
            return 0;
        } else {
            return -1;
        }
    }
}

package com.chen.fy.mytakeout.comparator;

import com.chen.fy.mytakeout.entity.EvaluationInfo;
import com.chen.fy.mytakeout.entity.OrderInfo;
import com.chen.fy.mytakeout.utils.OrderUtils;

import java.util.Comparator;

/**
 * 评价时间比较器
 */
public class OrderDataComparator implements Comparator<OrderInfo> {
    @Override
    public int compare(OrderInfo orderInfo1, OrderInfo orderInfo2) {
        if (orderInfo1.getOrderLongDate() < orderInfo2.getOrderLongDate()) {
            return 1;
        } else if (orderInfo1.getOrderLongDate() == orderInfo2.getOrderLongDate()) {
            return 0;
        } else {
            return -1;
        }
    }
}

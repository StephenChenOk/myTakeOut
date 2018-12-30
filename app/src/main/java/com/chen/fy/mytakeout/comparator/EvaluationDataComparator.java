package com.chen.fy.mytakeout.comparator;

import com.chen.fy.mytakeout.entity.EvaluationInfo;
import com.chen.fy.mytakeout.entity.StoreInfo;
import com.chen.fy.mytakeout.utils.OrderUtils;

import java.util.Comparator;

/**
 * 评价时间比较器
 */
public class EvaluationDataComparator implements Comparator<EvaluationInfo> {
    @Override
    public int compare(EvaluationInfo evaluationInfo1, EvaluationInfo evaluationInfo2) {
        if (evaluationInfo1.getEvaluationLongDate() < evaluationInfo2.getEvaluationLongDate()) {
            return 1;
        } else if (evaluationInfo1.getEvaluationLongDate() == evaluationInfo2.getEvaluationLongDate()) {
            return 0;
        } else {
            return -1;
        }
    }
}

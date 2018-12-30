package com.chen.fy.mytakeout.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.chen.fy.mytakeout.fragment.StoreEvaluationFragment;
import com.chen.fy.mytakeout.fragment.StoreMenuFragment;
import com.chen.fy.mytakeout.fragment.StoreInfoFragment;
import com.chen.fy.mytakeout.main.StoreActivity;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private StoreMenuFragment menuFragment;
    private StoreEvaluationFragment gradeFragment;
    private StoreInfoFragment storeInfoFragment;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        menuFragment = new StoreMenuFragment();
        gradeFragment = new StoreEvaluationFragment();
        storeInfoFragment = new StoreInfoFragment();
    }

    @Override
    public Fragment getItem(int i) {  //获取当前要显示的view
        Fragment fragment = null;
        switch (i) {
            case StoreActivity.PAGER_ONE:
                fragment = menuFragment;
                break;
            case StoreActivity.PAGER_TWO:
                fragment = gradeFragment;
                break;
            case StoreActivity.PAGER_THREE:
                fragment = storeInfoFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {  //获得viewPager中有多少个view
        return PAGER_COUNT;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //在viewPager容器中一次最多只可以存3个view,假设有1 2 3,则处于1界面时缓存1 2,处于2时缓存1 2 3,处于3时缓存2 3
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}


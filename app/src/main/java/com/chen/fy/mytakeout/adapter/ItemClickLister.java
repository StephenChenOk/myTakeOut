package com.chen.fy.mytakeout.adapter;
/*
 recyclerView点击事件
 */
public interface ItemClickLister {
    //点击事件
    void onItemClick(int position);
    //长按事件
    void onLongClick( int position);
}

package com.chen.fy.mytakeout.adapter;

/**
 * 自定义适配器
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.StoreInfo;
import com.chen.fy.mytakeout.fragment.HomeFragment;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<StoreInfo> list;
    private ItemClickLister itemClickLister;
    private Context myContext;

    //构造方法,并传入数据源
    public HomeAdapter(List<StoreInfo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(myContext == null){
            myContext = viewGroup.getContext();
        }
        //反射每行的子布局,并把view传入viewHolder中,以便获取控件对象
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_store_item_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        //对子项的数据进行赋值
        StoreInfo storeInfo = list.get(i);   //获取每一行的实例
        //viewHolder.logo.setImageResource(HomeFragment.getStoreLogoId(storeInfo.getLogo()));
        Glide.with(myContext).load(HomeFragment.getStoreLogoId(storeInfo.getLogo())).into(viewHolder.logo);
        viewHolder.name.setText(storeInfo.getName());
        viewHolder.grade.setText(String.format("评价:%s", String.valueOf(storeInfo.getGrade())));
        viewHolder.sales.setText(String.format("月售%s", String.valueOf(storeInfo.getSales())));
        viewHolder.time.setText(String.format("%s分钟", String.valueOf(storeInfo.getTime())));
        viewHolder.distance.setText(String.format("%skm", String.valueOf(storeInfo.getDistance())));
        viewHolder.type.setText(storeInfo.getType());
        viewHolder.average.setText(String.format("人均¥%s", String.valueOf(storeInfo.getAverage())));

        if (itemClickLister != null) {
            //对每一项的item引入一个接口
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickLister.onItemClick(i);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickLister.onLongClick(i);
                    return true;   //消化事件
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //传入接口
    public void setItemClickLister(ItemClickLister itemClickLister) {
        this.itemClickLister = itemClickLister;
    }

    /**
     * 内部类,获取各控件的对象
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView logo;
        private TextView name;
        private TextView grade;
        private TextView sales;
        private TextView time;
        private TextView distance;
        private TextView type;
        private TextView average;
        ViewHolder(@NonNull View itemView) {
            //获得每一行的布局view
            super(itemView);
            logo = itemView.findViewById(R.id.logo_store);
            name = itemView.findViewById(R.id.name_store);
            grade = itemView.findViewById(R.id.grade_store);
            sales = itemView.findViewById(R.id.sales_store);
            time = itemView.findViewById(R.id.time_store);
            distance = itemView.findViewById(R.id.distance_store);
            type = itemView.findViewById(R.id.type_store);
            average = itemView.findViewById(R.id.average_store);
        }
    }
}
package com.chen.fy.mytakeout.adapter;

/*
  自定义适配器
 */

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.application.MyApplication;
import com.chen.fy.mytakeout.entity.MenuInfo;
import com.chen.fy.mytakeout.fragment.StoreMenuFragment;
import com.chen.fy.mytakeout.main.MainActivity;
import com.chen.fy.mytakeout.utils.OrderUtils;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuInfo> list;
    private ItemClickLister itemClickLister;

    //用来储存加入购物车的菜单的集合
    public static List<MenuInfo> addShoppingCarList = new ArrayList<>();

    //显示购物车右上角的数字
    private QBadgeView qBadgeView;

    //构造方法,并传入数据源
    public MenuAdapter(List<MenuInfo> list) {
        this.list = list;
    }

    //构造方法,并传入数据源和显示购物车右上角的数字的控件
    public MenuAdapter(List<MenuInfo> list ,QBadgeView qBadgeView) {
        this.list = list;
        this.qBadgeView = qBadgeView;
    }

    //传入接口
    public void setItemClickLister(ItemClickLister itemClickLister) {
        this.itemClickLister = itemClickLister;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //反射每行的子布局,并把view传入viewHolder中,以便获取控件对象
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.store_menu_item_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        //对子项的数据进行赋值
        final MenuInfo menuInfo = list.get(i);    //获取当前行对象
        viewHolder.logo.setImageResource(StoreMenuFragment.getMenuLogoId(menuInfo.getLogo()));
        viewHolder.name.setText(menuInfo.getName());
        viewHolder.sales.setText(String.format("月售%s", String.valueOf(menuInfo.getSales())));
        viewHolder.zan.setText(String.format("赞%s", String.valueOf(menuInfo.getZan())));
        viewHolder.price.setText(String.format("¥%s", String.valueOf(menuInfo.getPrice())));
        //下单按钮的点击事件
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.userId != null) {
                    if (!MainActivity.userId.isEmpty()) {
                        Toast.makeText(MyApplication.getContext(),"加入购物车成功！",Toast.LENGTH_SHORT).show();
                        OrderUtils.addShoppingCar(menuInfo.getName(),menuInfo.getLogo(),menuInfo.getPrice());
                        addShoppingCarList.add(menuInfo);    //加入集合
                        OrderUtils.showShoppingCarNumber(qBadgeView);  //更新购物车右上角的数字
                    }
                }else{
                    Toast.makeText(MyApplication.getContext(),"请先登入",Toast.LENGTH_SHORT).show();
                }
            }
        });


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
                    return true;  //消化事件
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建一个内部类,放着要显示的View控件,通过实例化这个类,把其对象一起放到View
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView logo;
        private TextView name;
        private TextView sales;
        private TextView zan;
        private TextView price;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //获得每一行的布局view,并找到控件对象
            logo = itemView.findViewById(R.id.food_logo_store);
            name = itemView.findViewById(R.id.food_name_store);
            sales = itemView.findViewById(R.id.food_sale_store);
            zan = itemView.findViewById(R.id.food_zan_store);
            price = itemView.findViewById(R.id.food_price_store);
            imageView = itemView.findViewById(R.id.food_buy_store_image);
        }
    }
}
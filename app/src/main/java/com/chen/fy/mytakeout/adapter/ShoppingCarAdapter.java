package com.chen.fy.mytakeout.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.ShoppingCarInfo;
import com.chen.fy.mytakeout.fragment.StoreMenuFragment;

import java.util.List;

public class ShoppingCarAdapter extends RecyclerView.Adapter<ShoppingCarAdapter.ViewHolder>{

    private List<ShoppingCarInfo> list;
    //构造方法,并传入数据源
    public ShoppingCarAdapter(List<ShoppingCarInfo> list){
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //反射每行的子布局,并把view传入viewHolder中,以便获取控件对象
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopping_car_adapter, viewGroup, false);
        return new ShoppingCarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ShoppingCarInfo shoppingCarInfo = list.get(i);
        viewHolder.shopping_car_store_name.setText(shoppingCarInfo.getStoreName());
        viewHolder.shopping_car_menu_logo.setImageResource(StoreMenuFragment.getMenuLogoId(shoppingCarInfo.getMenuLogo()));
        viewHolder.shopping_car_menu_name.setText(shoppingCarInfo.getMenuName());
        viewHolder.shopping_car_menu_price.setText(String.valueOf(shoppingCarInfo.getPrice()));
        viewHolder.shopping_car_menu_number.setText("1");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView shopping_car_store_name;
        private ImageView shopping_car_menu_logo;
        private TextView shopping_car_menu_name;
        private TextView shopping_car_menu_price;
        private TextView shopping_car_menu_number;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopping_car_store_name = itemView.findViewById(R.id.shopping_car_store_name);
            shopping_car_menu_logo = itemView.findViewById(R.id.shopping_car_menu_logo);
            shopping_car_menu_name = itemView.findViewById(R.id.shopping_car_menu_name);
            shopping_car_menu_price = itemView.findViewById(R.id.shopping_car_menu_price);
            shopping_car_menu_number = itemView.findViewById(R.id.shopping_car_menu_number);
        }
    }
}

package com.chen.fy.mytakeout.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.OrderInfo;
import com.chen.fy.mytakeout.fragment.OrderFragment;
import com.chen.fy.mytakeout.main.EvaluationActivity;
import com.chen.fy.mytakeout.utils.OrderUtils;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderInfo> list;
    private Context context;
    private boolean test = true;

    public OrderAdapter(List<OrderInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //反射每行的子布局,并把view传入viewHolder中,以便获取控件对象
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_item_adapter, viewGroup, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final OrderInfo orderInfo = list.get(i);
        viewHolder.logo.setImageResource(OrderFragment.getStoreLogoId(orderInfo.getStoreName()));
        viewHolder.storeName.setText(orderInfo.getStoreName());
        if(orderInfo.isOrderType()) {  //已经完成配送
            viewHolder.orderType.setText("订单已完成");
        }else{
            viewHolder.orderType.setText("正在配送中");
        }
        viewHolder.menuName.setText(orderInfo.getMenuName());
        viewHolder.price.setText(String.format("¥%d", orderInfo.getPrice()));
        viewHolder.orderDate.setText(OrderUtils.dateStringChange(orderInfo.getOrderLongDate()));
        //点击评价事件
        if (orderInfo.isEvaluationType()) {   //已经进行评价
            viewHolder.evaluation_order_btn.setText("已评价");
        } else {
            viewHolder.evaluation_order_btn.setBackgroundResource(R.drawable.text_frame);  //设置背景框
            viewHolder.evaluation_order_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EvaluationActivity.class);
                    intent.putExtra("storeName", orderInfo.getStoreName());  //店家名
                    intent.putExtra("menuName", orderInfo.getMenuName());
                    intent.putExtra("orderId",orderInfo.getId());
                    context.startActivity(intent);
                }
            });
        }
        test=!test;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView logo;
        private TextView storeName;
        private TextView orderType;
        private TextView menuName;
        private TextView price;
        private TextView orderDate;
        private Button evaluation_order_btn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo_store_order);
            storeName = itemView.findViewById(R.id.name_store_order);
            orderType = itemView.findViewById(R.id.order_type);
            menuName = itemView.findViewById(R.id.menu_order_tv);
            price = itemView.findViewById(R.id.price_order_tv);
            orderDate = itemView.findViewById(R.id.order_date);
            evaluation_order_btn = itemView.findViewById(R.id.evaluation_order_btn);
        }
    }

}

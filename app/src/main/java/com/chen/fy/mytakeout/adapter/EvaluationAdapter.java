package com.chen.fy.mytakeout.adapter;

/**
 * 自定义评价界面适配器
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chen.fy.mytakeout.R;
import com.chen.fy.mytakeout.entity.EvaluationInfo;
import com.chen.fy.mytakeout.fragment.StoreEvaluationFragment;
import com.chen.fy.mytakeout.utils.ImageUtils;
import com.chen.fy.mytakeout.utils.OrderUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.ViewHolder> {

    private List<EvaluationInfo> list;
    private Context myContext;
    //private Bitmap headIcon;
    //构造方法,并传入数据源
    public EvaluationAdapter(List<EvaluationInfo> list){
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
                .inflate(R.layout.store_evaluation_item_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        EvaluationInfo evaluationInfo = list.get(i);
        if(StoreEvaluationFragment.getUserHeadIcon(evaluationInfo.getUserName())!= 0 ) {
           // viewHolder.headIcon.setImageResource(StoreEvaluationFragment.getUserHeadIcon(evaluationInfo.getUserName()));
            Glide.with(myContext).load(StoreEvaluationFragment.getUserHeadIcon(evaluationInfo.getUserName())).into(viewHolder.headIcon);
        }else{
            //viewHolder.headIcon.setImageBitmap(ImageUtils.getHeadIcon(evaluationInfo.getUserId()));
            Glide.with(myContext).load(ImageUtils.getHeadIcon(evaluationInfo.getUserId())).into(viewHolder.headIcon);
        }

        viewHolder.user.setText(evaluationInfo.getUserName());
        viewHolder.date.setText(OrderUtils.dateStringChange(evaluationInfo.getEvaluationLongDate()));
        viewHolder.grade.setText(String.format("评分 %s", evaluationInfo.getGradeEvaluation()));
        viewHolder.text.setText(evaluationInfo.getTextEvaluation());
        viewHolder.menu.setText(evaluationInfo.getMenu());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建一个内部类,放着要显示的View控件,通过实例化这个类,把其对象一起放到View
    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView headIcon;
        private TextView user;
        private TextView date;
        private TextView grade;
        private TextView text;
        private TextView menu;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //获得每一行的布局view,并找到控件对象
            headIcon = itemView.findViewById(R.id.headIcon_evaluation);
            user = itemView.findViewById(R.id.user_evaluation);
            date = itemView.findViewById(R.id.date_evaluation);
            grade = itemView.findViewById(R.id.grade_evaluation);
            text = itemView.findViewById(R.id.text_evaluation);
            menu = itemView.findViewById(R.id.menu_store_evaluation);
        }
    }
}
package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.RecommendBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */
public class TypeThreeAdapter extends RecyclerView.Adapter {
    protected Context context;
    protected List<RecommendBean.ConEntity.DetailListEntity> detailListEntities = new ArrayList<>();

    public TypeThreeAdapter(Context context) {
        this.context = context;
    }

    public void setDetailListEntities(List<RecommendBean.ConEntity.DetailListEntity> detailListEntities) {
        this.detailListEntities = detailListEntities;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.type_threeandzero_item, parent, false);
        return new TypeThreeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        TypeThreeItemHolder holder = (TypeThreeItemHolder)holder1;
        RecommendBean.ConEntity.DetailListEntity detailListEntity = detailListEntities.get(position);
        String name = detailListEntity.getName();
        Log.e("TypeThreeAdapter","name="+name);
        Glide.with(context).load(detailListEntity.getLogo()).placeholder(R.mipmap.place_holder).into(holder.iv_picture);
        holder.iv_start.setBackgroundResource(R.mipmap.play_start);
        holder.tv_descriptions.setText(detailListEntity.getDescriptions());
        holder.tv_name.setText(detailListEntity.getName());
    }

    @Override
    public int getItemCount() {
        return detailListEntities.size();
    }

    protected static class TypeThreeItemHolder extends RecyclerView.ViewHolder {
        public ImageView iv_start, iv_picture;
        public TextView tv_descriptions, tv_name;

        public TypeThreeItemHolder(View itemView) {
            super(itemView);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture1);
            iv_start = (ImageView) itemView.findViewById(R.id.iv_start);
            tv_descriptions = (TextView) itemView.findViewById(R.id.tv_descriptions1);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name1);
        }
    }

}

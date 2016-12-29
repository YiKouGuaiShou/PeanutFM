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
public class TypeOneAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<RecommendBean.ConEntity.DetailListEntity> detailListEntities = new ArrayList<>();

    public TypeOneAdapter(Context context) {
        this.context = context;
    }

    public void setDetailListEntities(List<RecommendBean.ConEntity.DetailListEntity> detailListEntities) {
        this.detailListEntities = detailListEntities;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.typeone_item, parent, false);
        return new TypeOneItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TypeOneItemHolder holder1 = (TypeOneItemHolder) holder;
        RecommendBean.ConEntity.DetailListEntity detailListEntity = detailListEntities.get(position);
        String name = detailListEntity.getName();
        Log.e("TypeOneAdapter", "name=" + name);
        Glide.with(context).load(detailListEntity.getLogo()).placeholder(R.mipmap.ic_launcher)
                .into(holder1.iv_picture);
        holder1.tv_describtion.setText(detailListEntity.getDescriptions());
        holder1.tv_name.setText(detailListEntity.getName());
    }

    @Override
    public int getItemCount() {
        return detailListEntities.size();
    }

    private static class TypeOneItemHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_describtion;
        public ImageView iv_picture;

        public TypeOneItemHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_describtion = (TextView) itemView.findViewById(R.id.tv_describtion);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture1);
        }
    }
}

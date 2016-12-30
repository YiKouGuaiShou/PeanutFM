package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.TypeThreeItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */
public class TypeThreeMoreAdapter extends UltimateBaseAdapter {
    List<TypeThreeItemBean.ConEntity> conList = new ArrayList<>();

    public TypeThreeMoreAdapter(Context context) {
        super(context);
    }

    public void setConList(List<TypeThreeItemBean.ConEntity> conList) {
        this.conList = conList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.more_typethree_item,parent,false);
        return new TypeThreeMoreHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return conList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        TypeThreeMoreHolder holder = (TypeThreeMoreHolder) holder1;
        TypeThreeItemBean.ConEntity conEntity = conList.get(position);
        holder.tv_name.setText(conEntity.getAlbumName());
        Glide.with(context).load(conEntity.getLogo()).placeholder(R.mipmap.place_holder)
                .into(holder.iv_picture);
    }

    private static class TypeThreeMoreHolder extends RecyclerView.ViewHolder{
        public ImageView iv_picture;
        public TextView tv_name;
        public TypeThreeMoreHolder(View itemView) {
            super(itemView);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture1);
            tv_name = (TextView) itemView.findViewById(R.id.tv_descriptions1);
        }
    }
}

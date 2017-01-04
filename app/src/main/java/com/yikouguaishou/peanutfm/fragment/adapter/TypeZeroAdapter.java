package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.WebViewActivity;
import com.yikouguaishou.peanutfm.bean.RecommendBean;

import java.io.File;

/**
 * Created by Administrator on 2016/12/29.
 */
public class TypeZeroAdapter extends TypeThreeAdapter {
    public TypeZeroAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.typezero_item, parent, false);
        return new TypeThreeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        super.onBindViewHolder(holder1, position);
    }

    @Override
    public void setOnclickListener(RecommendBean.ConEntity.DetailListEntity detailListEntity) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("linkUrl", detailListEntity.getLinkUrl());
        intent.putExtra("linkType",1);
        context.startActivity(intent);
    }
}

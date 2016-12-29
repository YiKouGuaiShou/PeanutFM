package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yikouguaishou.peanutfm.R;

/**
 * Created by Administrator on 2016/12/29.
 */
public class TypeZeroAdapter extends TypeThreeAdapter{
    public TypeZeroAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.typezero_item, parent, false);
        return new TypeThreeItemHolder(view);
    }
}

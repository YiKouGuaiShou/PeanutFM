package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.WebViewActivity;
import com.yikouguaishou.peanutfm.bean.TypeZeroItemBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */
public class TypeZeroMoreAdapter extends UltimateBaseAdapter {

    public List<TypeZeroItemBean.ConEntity> conList;

    public TypeZeroMoreAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.more_typezero_item, parent, false);
        return new TypeZeroMoreHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return conList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        TypeZeroMoreHolder holder = (TypeZeroMoreHolder) holder1;
        final TypeZeroItemBean.ConEntity conEntity = conList.get(position);
        Glide.with(context).load(conEntity.getCover()).placeholder(R.mipmap.place_holder)
                .into(holder.iv);
        Log.e("typeZeroItem", "typeZeroItem :" + conEntity.getTitle());
        holder.tv_typeZero1.setText(conEntity.getTitle());
        holder.tv_typeZero2.setText(conEntity.getSummaryContent());
        holder.tv_typeZero3.setText(conEntity.getCommentCount() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("linkUrl", conEntity.getCon());
                intent.putExtra("linkType", 1);
                context.startActivity(intent);
            }
        });
    }

    public void setConList(List<TypeZeroItemBean.ConEntity> conList) {
        this.conList = conList;
        notifyDataSetChanged();
    }

    private static class TypeZeroMoreHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv_typeZero1, tv_typeZero2, tv_typeZero3;
        public LinearLayout turn_layout;

        public TypeZeroMoreHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_typeZeroitem);
            tv_typeZero1 = (TextView) itemView.findViewById(R.id.tv_typeZeroitem1);
            tv_typeZero2 = (TextView) itemView.findViewById(R.id.tv_typeZeroitem2);
            tv_typeZero3 = (TextView) itemView.findViewById(R.id.tv_msg);
            turn_layout = (LinearLayout) itemView.findViewById(R.id.turn_layout);
        }
    }
}

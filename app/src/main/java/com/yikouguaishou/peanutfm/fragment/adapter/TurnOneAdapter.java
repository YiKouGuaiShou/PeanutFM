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
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.WebViewActivity;
import com.yikouguaishou.peanutfm.bean.TurnOneItemBean;
import com.yikouguaishou.peanutfm.utils.ShareUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */
public class TurnOneAdapter extends UltimateBaseAdapter {
    private List<TurnOneItemBean.ConEntity> data;

    public TurnOneAdapter(Context context) {
        super(context);
    }

    public void setData(List<TurnOneItemBean.ConEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.turn_one_item, parent, false);
        return new TurnOneItemHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        TurnOneItemHolder holder = (TurnOneItemHolder) holder1;
        TurnOneItemBean.ConEntity conEntity = data.get(position);
        Glide.with(context).load(conEntity.getCover()).placeholder(R.mipmap.place_holder)
                .into(holder.iv_picture);
        //Log.e("conEntity.getCover()", conEntity.getCover());
        holder.tv_msg1.setText(conEntity.getBrowseVolume() + "人");
        holder.tv_msg2.setText(conEntity.getCommentCount() + "");
        holder.tv_title.setText(conEntity.getTitle());

        doTime(holder, conEntity);

        setLisenser(holder, conEntity);

    }

    /**
     * 点击事件
     *
     * @param holder
     */
    private void setLisenser(TurnOneItemHolder holder, final TurnOneItemBean.ConEntity conEntity) {

        holder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnTOWebViewActivity(conEntity);
            }
        });

        holder.layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.showShare(context);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnTOWebViewActivity(conEntity);
            }
        });
    }

    private void turnTOWebViewActivity(TurnOneItemBean.ConEntity conEntity) {
        Intent intent = new Intent(context, WebViewActivity.class);
        String linkUrl = conEntity.getConent();
        intent.putExtra("linkType", 2);
        intent.putExtra("linkUrl", linkUrl);
        context.startActivity(intent);
    }

    private void doTime(TurnOneItemHolder holder, TurnOneItemBean.ConEntity conEntity) {
        Date date = new Date();
        String endDate = conEntity.getEndDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = format.format(date);

        try {
            long nowTime = format.parse(nowDate).getTime();
            long endTime = format.parse(endDate).getTime();
            long isEnd = nowTime - endTime;
            if (isEnd < 0) {
                holder.tv_isEnd.setText("进行中");
                holder.tv_isEnd.setTextColor(context.getResources().getColor(R.color.colorRed));
            } else {
                holder.tv_isEnd.setText("已结束");
                holder.tv_isEnd.setTextColor(context.getResources().getColor(R.color.grey));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static class TurnOneItemHolder extends RecyclerView.ViewHolder {
        public ImageView iv_picture;
        public TextView tv_isEnd, tv_msg1, tv_msg2, tv_title;
        public LinearLayout layout1, layout2, layout3;

        public TurnOneItemHolder(View itemView) {
            super(itemView);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            tv_isEnd = (TextView) itemView.findViewById(R.id.tv_isEnd);
            tv_msg1 = (TextView) itemView.findViewById(R.id.tv_msg1);
            tv_msg2 = (TextView) itemView.findViewById(R.id.tv_msg2);
            tv_title = (TextView) itemView.findViewById(R.id.title);

            layout1 = (LinearLayout) itemView.findViewById(R.id.layout1);
            layout2 = (LinearLayout) itemView.findViewById(R.id.layout2);
            layout3 = (LinearLayout) itemView.findViewById(R.id.layout3);
        }
    }
}

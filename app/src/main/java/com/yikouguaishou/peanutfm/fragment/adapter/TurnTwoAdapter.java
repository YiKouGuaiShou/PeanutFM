package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.TurnTwoItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */
public class TurnTwoAdapter extends UltimateBaseAdapter {
    private List<TurnTwoItemBean.VideoPlateListEntity> videoPlateList;

    public TurnTwoAdapter(Context context) {
        super(context);
    }

    public void setData(List<TurnTwoItemBean.VideoPlateListEntity> videoPlateList) {
        this.videoPlateList = videoPlateList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.turn_two_item, parent, false);
        return new TurnTwoItemHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return videoPlateList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        TurnTwoItemHolder holder = (TurnTwoItemHolder) holder1;
        TurnTwoItemBean.VideoPlateListEntity entity = videoPlateList.get(position);
        Glide.with(context).load(entity.getCover()).placeholder(R.mipmap.place_holder)
                .into(holder.iv_picture);
        holder.tv_descriptions1.setText(entity.getTitle());
        holder.tv_name.setText(entity.getPlayDescribe());
        holder.tv_person.setText(entity.getClickNum() + "人");
    }

    private static class TurnTwoItemHolder extends RecyclerView.ViewHolder {
        public RoundedImageView iv_picture;
        public TextView tv_descriptions1, tv_name, tv_person;

        public TurnTwoItemHolder(View itemView) {
            super(itemView);
            iv_picture = (RoundedImageView) itemView.findViewById(R.id.iv_picture1);
            tv_descriptions1 = (TextView) itemView.findViewById(R.id.tv_descriptions1);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name1);
            tv_person = (TextView) itemView.findViewById(R.id.tv_persons);
        }
    }
}
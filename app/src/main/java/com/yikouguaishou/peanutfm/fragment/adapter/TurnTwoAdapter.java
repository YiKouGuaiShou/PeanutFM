package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.TurnTwoItemBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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
        final TurnTwoItemHolder vh = (TurnTwoItemHolder) holder1;
        final TurnTwoItemBean.VideoPlateListEntity entity = videoPlateList.get(position);
        vh.tv_descriptions1.setText(entity.getTitle());
        vh.tv_name.setText(entity.getPlayDescribe());
        vh.tv_person.setText(entity.getClickNum() + "人");
        vh.JC.setUp(entity.getVideoExpand().getVideoUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        Glide.with(context).load(entity.getVideoExpand().getVideoIcon())
                .placeholder(R.mipmap.place_holder)
                .into(vh.JC.thumbImageView);
    }

    private static class TurnTwoItemHolder extends RecyclerView.ViewHolder {
        public TextView tv_descriptions1, tv_name, tv_person;
        JCVideoPlayerStandard JC;

        public TurnTwoItemHolder(View itemView) {
            super(itemView);
            JC = (JCVideoPlayerStandard) itemView.findViewById(R.id.JC_video1);
            tv_descriptions1 = (TextView) itemView.findViewById(R.id.tv_descriptions1);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name1);
            tv_person = (TextView) itemView.findViewById(R.id.tv_persons);
        }
    }
}

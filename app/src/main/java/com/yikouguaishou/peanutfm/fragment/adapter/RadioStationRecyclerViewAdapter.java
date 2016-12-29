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
import com.yikouguaishou.peanutfm.bean.RadioStationBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */
public class RadioStationRecyclerViewAdapter extends RecyclerView.Adapter<RadioStationRecyclerViewAdapter.MyRadioStationViewHolder> {
    private Context ctx;
    private List<RadioStationBean.ConBeanX> radioStationData;

    public RadioStationRecyclerViewAdapter(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * 更新数据
     *
     * @param radioStationData
     */
    public void setRadioStationData(List<RadioStationBean.ConBeanX> radioStationData) {
        this.radioStationData = radioStationData;
        notifyDataSetChanged();
    }

    /**
     * 获取子布局
     *
     * @param parent
     * @return
     */
    @Override
    public MyRadioStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_radio_station, parent, false);
        return new MyRadioStationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRadioStationViewHolder holder, int position) {
        List<RadioStationBean.ConBeanX.LiveListBean> liveList = radioStationData.get(0).getLiveList();
        RadioStationBean.ConBeanX.LiveListBean liveListBean = liveList.get(position);

        //设置logo图片
        String logoUrl = liveListBean.getLogoUrl();
        if (logoUrl != null) {
            Glide.with(ctx).load(logoUrl).into(holder.rv_item_iv_logo);
        } else {
            holder.rv_item_iv_logo.setImageResource(R.mipmap.ic_launcher);
        }

        //设置频道名
        String name = liveListBean.getName();
        holder.rv_item_tv_channel.setText(name);

        //设置点击量
        int clicks = liveListBean.getClicks();
        holder.rv_item_tv_number.setText("" + clicks);

        List<RadioStationBean.ConBeanX.LiveListBean.ConBean> con = liveListBean.getCon();

        int time = getTime();
        for (int i = 0; i < con.size(); i++) {
            String startTime = con.get(i).getStartTime();
            String endTime = con.get(i).getEndTime();
            int start = getInteger(startTime);
            int end = getInteger(endTime);
            if (time >= start && time < end) {
                //设置主播
                String anchorperson = con.get(i).getAnchorperson();
                holder.rv_item_tv_anchor.setText("主播:" + anchorperson);

                //设置节目名
                String conName = con.get(i).getName();
                holder.rv_item_tv_title.setText("正在直播:" + conName);
            }
        }
    }

    /**
     * 获取系统时间
     */
    private int getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(new Date());
        return getInteger(date);
    }

    /**
     * 时间字符串转化为int类型
     *
     * @param date
     * @return
     */
    private int getInteger(String date) {
        String replaceDate = date.replace(":", "");
        int time = Integer.parseInt(replaceDate);
        return time;
    }

    /**
     * 返回item个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return radioStationData == null ? 0 : radioStationData.get(0).getLiveList().size();
    }

    class MyRadioStationViewHolder extends RecyclerView.ViewHolder {
        public ImageView rv_item_iv_logo;
        public TextView rv_item_tv_channel, rv_item_tv_title, rv_item_tv_anchor, rv_item_tv_number;

        public MyRadioStationViewHolder(View itemView) {
            super(itemView);
            rv_item_iv_logo = (ImageView) itemView.findViewById(R.id.rv_item_iv_logo);
            rv_item_tv_channel = (TextView) itemView.findViewById(R.id.rv_item_tv_channel);
            rv_item_tv_title = (TextView) itemView.findViewById(R.id.rv_item_tv_title);
            rv_item_tv_anchor = (TextView) itemView.findViewById(R.id.rv_item_tv_anchor);
            rv_item_tv_number = (TextView) itemView.findViewById(R.id.rv_item_tv_number);
        }
    }
}

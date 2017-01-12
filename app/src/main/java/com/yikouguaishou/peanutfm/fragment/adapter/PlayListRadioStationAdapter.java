package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.PlayRadioStationItemClickListener;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class PlayListRadioStationAdapter extends UltimateViewAdapter {
    private Context ctx;
    private List<RadioStationBean.ConBeanX.LiveListBean.ProgamlistBean> progamlist;

    private PlayRadioStationItemClickListener listener;

    public PlayListRadioStationAdapter(Context ctx, List<RadioStationBean.ConBeanX.LiveListBean.ProgamlistBean> progamlist) {
        this.ctx = ctx;
        this.progamlist = progamlist;
    }

    public void setOnItemClickListener(PlayRadioStationItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.play_list_radio_station_rv, parent, false);
        return new PlayRadioListHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return progamlist.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PlayRadioListHolder holder2 = (PlayRadioListHolder) holder;
        //设置节目名
        String name = progamlist.get(position).getName();
        holder2.play_list_item_tv_name.setText(name);
        //设置播放时间
        String startTime = progamlist.get(position).getStartTime();
        String endTime = progamlist.get(position).getEndTime();
        holder2.play_list_item_tv_time.setText("播放时间：" + startTime + "-" + endTime);
        final int time = getTime();
        final int start = getInteger(startTime);
        final int end = getInteger(endTime);
        if (time >= start && time < end) {
            holder2.play_list_item_tv_name.setTextColor(ctx.getResources().getColor(R.color.colorYellow));
            holder2.play_list_item_tv_time.setTextColor(ctx.getResources().getColor(R.color.colorYellow));
        }

        holder2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start < time) {
                    listener.onFetch(position);
                } else {
                    Toast.makeText(ctx, "节目暂未开始", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

    private static class PlayRadioListHolder extends RecyclerView.ViewHolder {
        public TextView play_list_item_tv_name, play_list_item_tv_time;

        public PlayRadioListHolder(View itemView) {
            super(itemView);
            play_list_item_tv_name = (TextView) itemView.findViewById(R.id.play_list_item_tv_name);
            play_list_item_tv_time = (TextView) itemView.findViewById(R.id.play_list_item_tv_time);
        }
    }
}

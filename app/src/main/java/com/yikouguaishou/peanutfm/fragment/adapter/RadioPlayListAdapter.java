package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yikouguaishou.peanutfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */
public class RadioPlayListAdapter extends UltimateBaseAdapter {
    private List<String> nameList;
    private int currentPage;
    private int index;
    private int nowPlay;

    public RadioPlayListAdapter(Context context) {
        super(context);
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
        notifyDataSetChanged();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.radio_playlist_item, parent, false);
        return new RadioPlayListHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
//        Log.e("== Size== ", "size = " + nameList.size());
        return nameList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        final RadioPlayListHolder holder = (RadioPlayListHolder) holder1;
        String name = nameList.get(position);
        holder.tv.setText(name);
        nowPlay = currentPage * 20 + index - 1;
        holder.tv.setTextColor(context.getResources().getColor(R.color.grey));
        if (position == nowPlay) {
            holder.tv.setTextColor(context.getResources().getColor(R.color.colorYellow));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage = position / 20;
                index = position % 20 + 1;
                nowPlay = currentPage * 20 + index - 1;
                notifyDataSetChanged();
                holder.tv.setTextColor(context.getResources().getColor(R.color.colorYellow));
                lisener.onListItemClick(currentPage, index);
            }
        });
    }

    private static class RadioPlayListHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public RadioPlayListHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_radioItem);
        }
    }

    public void setRadioPlayListLisener(RadioPlayListLisener lisener) {
        this.lisener = lisener;
    }

    private RadioPlayListLisener lisener;

    public interface RadioPlayListLisener {
        void onListItemClick(int currentPage, int index);
    }
}

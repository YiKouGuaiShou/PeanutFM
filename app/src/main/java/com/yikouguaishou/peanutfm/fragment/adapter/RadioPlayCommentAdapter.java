package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.RadioPlayCommentBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/1/9.
 */
public class RadioPlayCommentAdapter extends UltimateViewAdapter {
    private List<RadioPlayCommentBean.ConEntity> commentList;
    private static int TYPE_ONE = 1;
    private static int TYPE_TWO = 2;
    private Context contex;
    List<String> strings;

    public RadioPlayCommentAdapter(Context context) {
        this.contex = context;
    }

    public void setCommentList(List<RadioPlayCommentBean.ConEntity> commentList, List<String> strings) {
        this.commentList = commentList;
        this.strings = strings;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(contex).inflate(R.layout.radioplay_item_one, parent, false);
            return new RadioPlayCommentHolderOne(view);
        } else {
            View view = LayoutInflater.from(contex).inflate(R.layout.radioplay_item_two, parent, false);
            return new RadioPlayCommentHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_ONE) {
            RadioPlayCommentHolderOne holder1 = (RadioPlayCommentHolderOne) holder;
            Glide.with(contex).load(strings.get(0)).into(holder1.iv_pic1);
            holder1.albnum1.setText(strings.get(1));
            holder1.name1.setText(strings.get(2));
            holder1.commentCount1.setText("互动评论(" + strings.get(3) + ")");
        } else {
            RadioPlayCommentHolderTwo holder2 = (RadioPlayCommentHolderTwo) holder;
            RadioPlayCommentBean.ConEntity conEntity = commentList.get(position - 1);
            Glide.with(contex).load(conEntity.getDiscussantIcon()).placeholder(R.mipmap.place_holder).into(holder2.iv_pic2);
            holder2.name2.setText(conEntity.getDiscussantName());
            holder2.content2.setText(conEntity.getContent());
            holder2.time2.setText(conEntity.getCreateTime());
            holder2.good2.setText(conEntity.getPraiseCount() + "");
            holder2.comment2.setText(conEntity.getDetailsCount() + "");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }

    @Override
    public int getAdapterItemCount() {
        return commentList.size() + 1;
    }


    private static class RadioPlayCommentHolderOne extends RecyclerView.ViewHolder {
        public RoundedImageView iv_pic1;
        public TextView albnum1, name1, commentCount1;

        public RadioPlayCommentHolderOne(View itemView) {
            super(itemView);
            iv_pic1 = (RoundedImageView) itemView.findViewById(R.id.mImageView_search_photo);
            albnum1 = (TextView) itemView.findViewById(R.id.mTextView_search_title);
            name1 = (TextView) itemView.findViewById(R.id.mTextView_search_source);
            commentCount1 = (TextView) itemView.findViewById(R.id.commentCount);
        }
    }

    private static class RadioPlayCommentHolderTwo extends RecyclerView.ViewHolder {
        public CircleImageView iv_pic2;
        public TextView name2, content2, time2, good2, comment2;

        public RadioPlayCommentHolderTwo(View itemView) {
            super(itemView);
            iv_pic2 = (CircleImageView) itemView.findViewById(R.id.mImageView_search_photo);
            name2 = (TextView) itemView.findViewById(R.id.radioplay_itemtwo_name);
            content2 = (TextView) itemView.findViewById(R.id.radioplay_itemtwo_content);
            time2 = (TextView) itemView.findViewById(R.id.riqi);
            good2 = (TextView) itemView.findViewById(R.id.dianzan_nshu);
            comment2 = (TextView) itemView.findViewById(R.id.pingln_shu);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return null;
    }

}

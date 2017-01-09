package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.DynamicCommentListBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snowflake on 2017/1/9.
 */
public class DynamicCommentAdapter extends UltimateViewAdapter<DynamicCommentAdapter.CommentViewHolder> {
    private Context context;
    private List<DynamicCommentListBean.ConBean> commentListDatas = new ArrayList<>();

    public DynamicCommentAdapter(Context context, List<DynamicCommentListBean.ConBean> commentListDatas) {
        this.context = context;
        this.commentListDatas = commentListDatas;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView civ_commentList_discussantIcon;
        TextView tv_commentList_discussantName, tv_commentList_content,
                tv_commentList_createTime, tv_commentList_replay, tv_commentList_delete;
        public CommentViewHolder(View itemView) {
            super(itemView);
            civ_commentList_discussantIcon = (CircleImageView) itemView.findViewById(R.id.mCircleImageView_dynamicDetails_commentList_photo);
            tv_commentList_discussantName = (TextView) itemView.findViewById(R.id.mTextView_dynamicDetails_commentList_name);
            tv_commentList_content = (TextView) itemView.findViewById(R.id.mTextView_dynamicDetails_commentList_content);
            tv_commentList_createTime = (TextView) itemView.findViewById(R.id.mTextView_dynamicDetails_commentList_createTime);
            tv_commentList_replay = (TextView) itemView.findViewById(R.id.mTextView_dynamicDetails_commentList_replay);
            tv_commentList_delete = (TextView) itemView.findViewById(R.id.mTextView_dynamicDetails_commentList_delete);

            tv_commentList_replay.setOnClickListener(this);
            tv_commentList_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.mTextView_dynamicDetails_commentList_replay:
                    Toast.makeText(context, "回复", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.mTextView_dynamicDetails_commentList_delete:
                    Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_comment_item, null);
        return new CommentViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return commentListDatas == null ? 0 : commentListDatas.size();
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        DynamicCommentListBean.ConBean conBean = commentListDatas.get(position);
        String discussantName = conBean.getDiscussantName();
        holder.tv_commentList_discussantName.setText(discussantName);
        String content = conBean.getContent();
        holder.tv_commentList_content.setText(content);
        String createTime = conBean.getCreateTime();
        holder.tv_commentList_createTime.setText(createTime);
        String discussantIcon = conBean.getDiscussantIcon();
        Glide.with(context)
                .load(discussantIcon)
                .into(holder.civ_commentList_discussantIcon);
    }

    @Override
    public CommentViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public CommentViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

}

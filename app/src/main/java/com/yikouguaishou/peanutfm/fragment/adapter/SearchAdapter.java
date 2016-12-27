package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.SearchResult;

import java.util.List;

/**
 * Created by snowflake on 2016/12/27.
 */
public class SearchAdapter extends BaseAdapter {
    private List<SearchResult.ConBean> resultDatas;
    private Context context;
    private final LayoutInflater inflater;

    private static final String URL = "http://fsapp.linker.cc/fslhsrv/srv/wifimusicbox/search/-1/0";

    public SearchAdapter(Context context, List<SearchResult.ConBean> resultDatas) {
        this.context = context;
        this.resultDatas = resultDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resultDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return resultDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.search_item, null);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.mTextView_search_title);
            viewHolder.tv_source = (TextView) view.findViewById(R.id.mTextView_search_source);
            viewHolder.img_photo = (ImageView) view.findViewById(R.id.mImageView_search_photo);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_title.setText(resultDatas.get(position).getName());
        viewHolder.tv_source.setText(resultDatas.get(position).getProviderName());
        String logoUrl = resultDatas.get(position).getLogoUrl();
        Glide.with(context)
                .load(logoUrl)
                .into(viewHolder.img_photo);

        return view;
    }

    class ViewHolder {
        TextView tv_title, tv_source;
        ImageView img_photo;
    }
}

package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by snowflake on 2016/12/30.
 */
public class ColumnListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ColumnListBean.ConBean> columnListDatas = new LinkedList<>();
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private View headerRecycleView;

    private OnItemClickListener mListener;
    private String playUrl;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public ColumnListAdapter(Context context, List<ColumnListBean.ConBean> columnListDatas) {
        this.context = context;
        this.columnListDatas = columnListDatas;
    }

    public View getHeaderRecycleView() {
        return headerRecycleView;
    }

    public void setHeaderRecycleView(View headerRecycleView) {
        this.headerRecycleView = headerRecycleView;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        if (headerRecycleView == null) {
            return ITEM_TYPE_CONTENT;
        }
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        }
        return ITEM_TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return headerRecycleView == null ? columnListDatas.size() : columnListDatas.size() + 1;
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_columnList_count;
        ImageView iv_columnList_sort;
        RelativeLayout rl_sort;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tv_columnList_count = (TextView) itemView.findViewById(R.id.mTextView_columnList_count);
            iv_columnList_sort = (ImageView) itemView.findViewById(R.id.mImageView_columnList_asc);
            rl_sort = (RelativeLayout) itemView.findViewById(R.id.rl_sort);
        }

    }

    //内容 ViewHolder
    public class ColumnViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_column_logo;
        ImageView iv_column_download, iv_column_downloaded;
        TextView tv_column_name, tv_column_replyNum, tv_column_listenNum, tv_column_duration;

        public ColumnViewHolder(View itemView) {
            super(itemView);

            iv_column_logo = (RoundedImageView) itemView.findViewById(R.id.mImageView_column_logo);
            iv_column_download = (ImageView) itemView.findViewById(R.id.mImageView_column_download);
            iv_column_downloaded = (ImageView) itemView.findViewById(R.id.mImageView_column_downloaded);
            tv_column_name = (TextView) itemView.findViewById(R.id.mTextView_column_name);
            tv_column_replyNum = (TextView) itemView.findViewById(R.id.mTextView_column_comment);
            tv_column_listenNum = (TextView) itemView.findViewById(R.id.mTextView_column_listen);
            tv_column_duration = (TextView) itemView.findViewById(R.id.mTextView__column_duration);

            iv_column_download.setOnClickListener(new DownMP3ClickListener());
        }

    }
    class DownMP3ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String path = "file";
            String fileName = "2.mp3";
            OutputStream output = null;
            try {
                /*
                 * 通过URL取得HttpURLConnection
                 * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
                 * <uses-permission android:name="android.permission.INTERNET" />
                 */
                URL url = new URL(playUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //取得inputStream，并将流中的信息写入SDCard
                /*
                 * 写前准备
                 * 1.在AndroidMainfest.xml中进行权限配置
                 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                 * 取得写入SDCard的权限
                 * 2.取得SDCard的路径： Environment.getExternalStorageDirectory()
                 * 3.检查要保存的文件上是否已经存在
                 * 4.不存在，新建文件夹，新建文件
                 * 5.将input流中的信息写入SDCard
                 * 6.关闭流
                 */
                String SDCard = Environment.getExternalStorageDirectory() + "";
                String pathName = SDCard + "/" + path + "/" + fileName;//文件存储路径

                File file = new File(pathName);
                InputStream input = conn.getInputStream();
                if (file.exists()) {
                    return;
                } else {
                    String dir = SDCard + "/" + path;
                    new File(dir).mkdir();//新建文件夹
                    file.createNewFile();//新建文件
                    output = new FileOutputStream(file);
                    //读取大文件
                    byte[] buffer = new byte[4 * 1024];
                    while (input.read(buffer) != -1) {
                        output.write(buffer);
                    }
                    output.flush();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    Toast.makeText(context, "下载完成！", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerRecycleView != null && viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(headerRecycleView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.columnlist_item, parent, false);
        return new ColumnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_CONTENT) {
            ColumnListBean.ConBean conBean = columnListDatas.get(position - 1);
            playUrl = conBean.getPlayUrl();
            if (holder instanceof ColumnViewHolder) {
                String name = conBean.getName();
                ((ColumnViewHolder) holder).tv_column_name.setText(name);
                int replyNum = conBean.getReplyNum();
                String comments = String.valueOf(replyNum);
                ((ColumnViewHolder) holder).tv_column_replyNum.setText(comments);
                int listenNum = conBean.getListenNum();
                String listens = String.valueOf(listenNum);
                ((ColumnViewHolder) holder).tv_column_listenNum.setText(listens);
                String duration = conBean.getDuration();
                ((ColumnViewHolder) holder).tv_column_duration.setText(duration);

                String logoUrl = conBean.getLogoUrl();
                Glide.with(context)
                        .load(logoUrl)
                        .into(((ColumnViewHolder) holder).iv_column_logo);
                return;
            } else if (getItemViewType(position) == ITEM_TYPE_HEADER) {
                return;
            }
            return;
        }
    }

    public void downloadMP3() {

    }
}

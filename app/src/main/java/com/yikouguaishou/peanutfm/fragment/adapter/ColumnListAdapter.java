package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;
import com.yikouguaishou.peanutfm.view.CircleProgressView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 专栏列表的适配器
 */
public class ColumnListAdapter extends UltimateViewAdapter<ColumnListAdapter.ColumnViewHolder> {
    private Context context;
    private List<ColumnListBean.ConBean> columnListDatas = new ArrayList<>();

    private OnItemClickListener mItemClickListener;
    private String playUrl;
    private String name;
    private ImageView iv_column_downloaded, iv_column_download;
    private CircleProgressView circleProgressView;
    int progressValue = 0;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.i("log", "handler : progressValue=" + progressValue);

                //通知view，进度值有变化
                circleProgressView.setProgressValue(progressValue * 3);
                circleProgressView.postInvalidate();

                progressValue += 1;
                if (progressValue > 100) {
                    timer.cancel();
                }
            }
            super.handleMessage(msg);
        }
    };

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }


    public ColumnListAdapter(Context context, List<ColumnListBean.ConBean> columnListDatas) {
        this.context = context;
        this.columnListDatas = columnListDatas;
    }

    @Override
    public int getAdapterItemCount() {
        return columnListDatas == null ? 0 : columnListDatas.size();
    }

    //内容 ViewHolder
    public class ColumnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RoundedImageView iv_column_logo;
        TextView tv_column_name, tv_column_replyNum, tv_column_listenNum, tv_column_duration;

        public ColumnViewHolder(final View itemView) {
            super(itemView);

            iv_column_logo = (RoundedImageView) itemView.findViewById(R.id.mImageView_column_logo);
            iv_column_download = (ImageView) itemView.findViewById(R.id.mImageView_column_download);
            iv_column_downloaded = (ImageView) itemView.findViewById(R.id.mImageView_column_downloaded);
            circleProgressView = (CircleProgressView) itemView.findViewById(R.id.mProgressBar_download);
            tv_column_name = (TextView) itemView.findViewById(R.id.mTextView_column_name);
            tv_column_replyNum = (TextView) itemView.findViewById(R.id.mTextView_column_comment);
            tv_column_listenNum = (TextView) itemView.findViewById(R.id.mTextView_column_listen);
            tv_column_duration = (TextView) itemView.findViewById(R.id.mTextView__column_duration);

            iv_column_download.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getPosition());
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.mImageView_column_download) {
                /* 异步下载 */
                new DownloadAsyncTask().execute(playUrl);
            }
        }
    }

    @Override
    public ColumnViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.columnlist_item, parent, false);
        return new ColumnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ColumnViewHolder holder, int position) {
        ColumnListBean.ConBean conBean = columnListDatas.get(position);
        playUrl = conBean.getPlayUrl();
        name = conBean.getName();
        holder.tv_column_name.setText(name);
        int replyNum = conBean.getReplyNum();
        String comments = String.valueOf(replyNum);
        holder.tv_column_replyNum.setText(comments);
        int listenNum = conBean.getListenNum();
        String listens = String.valueOf(listenNum);
        holder.tv_column_listenNum.setText(listens);
        String duration = conBean.getDuration();
        holder.tv_column_duration.setText(duration);

        String logoUrl = conBean.getLogoUrl();
        Glide.with(context)
                .load(logoUrl)
                .into(holder.iv_column_logo);
    }


    /* 异步任务，后台处理与更新UI */
    class DownloadAsyncTask extends AsyncTask<String, String, String> {
        /* 后台线程 */
        @Override
        protected String doInBackground(String... params) {
            timer.schedule(task, 1000, 1000); // 1s后执行task,经过1s再次执行

            /* 所下载文件的URL */
            try {
                /* SD卡根目录 */
                String rootDie = Environment.getExternalStorageDirectory() + "";
                /* 输出文件夹名称 */
                String file = "PeanutFM";
                /* 输出文件名称 */
                String fileName = name + ".mp3";
                String pathName = rootDie + "/" + file + "/" + fileName;//文件存储路径
                File newFile = new File(pathName);

                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                /* URL属性设置 */
                conn.setRequestMethod("GET");
                /* URL建立连接 */
                conn.connect();
                /* 下载文件的大小 */
                int fileOfLength = conn.getContentLength();
                /* 每次下载的大小与总下载的大小 */
                int length = 0;
                /* 输入流 */
                InputStream in = conn.getInputStream();
                if (newFile.exists()) {
                    System.out.println("exits");
                } else {
                    String dir = rootDie + "/" + file;
                    new File(dir).mkdir();
                    newFile.createNewFile();
                    /* 输出流 */
                    FileOutputStream out = new FileOutputStream(newFile);
                    /* 缓存模式，下载文件 */
                    byte[] buff = new byte[1024 * 1024];
                    while ((length = in.read(buff)) > 0) {
                        out.write(buff, 0, length);
                    }
                    /* 关闭输入输出流 */
                    in.close();
                    out.flush();
                    out.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /* 预处理UI线程 */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            circleProgressView.setVisibility(View.VISIBLE);
            iv_column_download.setVisibility(View.GONE);
        }

        /* 结束时的UI线程 */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            iv_column_downloaded.setVisibility(View.VISIBLE);
            circleProgressView.setVisibility(View.GONE);
            iv_column_download.setVisibility(View.GONE);
            Toast.makeText(context, "下载完成！", Toast.LENGTH_SHORT).show();
        }

        /* 处理UI线程，会被多次调用,触发事件为publicProgress方法 */
        @Override
        protected void onProgressUpdate(String... values) {
            /* 进度显示 */
            circleProgressView.setVisibility(View.VISIBLE);
            circleProgressView.setColors(randomColors());
        }
    }

    /**
     * 随机颜色
     *
     * @return
     */
    private int[] randomColors() {
        int[] colors = new int[4];

        Random random = new Random();
        int r, g, b;
        for (int i = 0; i < 4; i++) {
            r = random.nextInt(256);
            g = random.nextInt(256);
            b = random.nextInt(256);
            colors[i] = Color.argb(255, r, g, b);
            Log.i("customView", "log: colors[" + i + "]=" + Integer.toHexString(colors[i]));
        }
        return colors;
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
    public ColumnViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public ColumnViewHolder newHeaderHolder(View view) {
        return null;
    }
}

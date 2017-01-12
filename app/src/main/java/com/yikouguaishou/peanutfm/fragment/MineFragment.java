package com.yikouguaishou.peanutfm.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.AdviceActivity;
import com.yikouguaishou.peanutfm.ListenHistoryActivity;
import com.yikouguaishou.peanutfm.LoginActivity;
import com.yikouguaishou.peanutfm.MyActivityActivity;
import com.yikouguaishou.peanutfm.MyCollectionActivity;
import com.yikouguaishou.peanutfm.MyCountActivity;
import com.yikouguaishou.peanutfm.MyNewsActivity;
import com.yikouguaishou.peanutfm.MyZhuBoActivity;
import com.yikouguaishou.peanutfm.PersonInfoActivity;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.SettingActivity;
import com.yikouguaishou.peanutfm.utils.MySharePreferrences;
import com.yikouguaishou.peanutfm.utils.TimeTranstor;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends Fragment implements ListView.OnItemClickListener {
    private ListView mListView_mf;
    /**
     * listview中所有子view的字符串
     */
    private String[] mItemnames;
    private ListViewAdapter adapter;
    private int[] itemlogo;
    private CircleImageView cv;
    private TextView mf_username;
    private boolean isLoaded = false;
    private String iconurl;
    private String username;
    private Context context;

    //listview的每个子view的名称

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        init(view);


        isLoadAgain();


        return view;

    }

    private void isLoadAgain() {
        long nowtime = System.currentTimeMillis();
        Log.e("TAG", "onCreate: " + "这里获取的nowtime" + nowtime);
        long lasttime = MySharePreferrences.getLasttime(context);
        Log.e("TAG", "onCreate: " + "这里获取的上次的lasttime" + lasttime);

//        Toast.makeText(MainActivity.this, "上次登录是"+(nowtime-lasttime)/1000+"秒前", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "上次登录是" + TimeTranstor.getTime(lasttime), Toast.LENGTH_SHORT).show();


        if (nowtime - lasttime < 360000000) {

            String iconurl = MySharePreferrences.getIconurl(context);
            String username = MySharePreferrences.getUsername(context);

            if ((iconurl != null) && (username != null)) {
                Log.e("TAG", "读取到了onCreateView:  iconurl： " + iconurl);
                Log.e("TAG", "读取到了onCreateView:  username： " + username);


                if (cv == null || mf_username == null) {
                    Log.e("TAG", "onCreateView: ===========>null");
                } else {
                    if (mf_username != null) {
                        Log.e("TAG", "isLoadAgain: ====>开始读取");
                        if(MySharePreferrences.getLoadState(context))
                        {
                            Glide.with(context).load(iconurl).into(cv);
                            mf_username.setText(username);
                            isLoaded = true;
                        }
                        else
                        {
                            mf_username.setText("用户名");
                            cv.setImageResource(R.mipmap.touxiang);
                        }
                    }

                }
            }


        }
    }

    private void init(View view) {
        mListView_mf = (ListView) view.findViewById(R.id.mListView_mf);
        //从资源文件中得到字符串数组
        mItemnames = getResources().getStringArray(R.array.minefragment_itemnames);


        itemlogo = new int[]{R.drawable.ic_mynews, R.drawable.woguanzhudezhubo, R.drawable.myactivity, R.drawable.mycount,
                R.drawable.mycollection, R.drawable.ic_shoutinglishi, R.drawable.ic_yijianfankui, R.drawable.setting};

        mListView_mf.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine_header, null, false));

        cv = (CircleImageView) getActivity().findViewById(R.id.cv);

        mf_username = (TextView) getActivity().findViewById(R.id.mf_username);
        adapter = new ListViewAdapter();
        mListView_mf.setAdapter(adapter);
        mListView_mf.setOnItemClickListener(this);


        String username = MySharePreferrences.getUsername(context);
        if (username != null) {
            isLoaded = false;
        } else {
            isLoaded = true;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 1) {
            startActivity(new Intent(context, MyNewsActivity.class));
        } else if (i == 2) {
            startActivity(new Intent(context, MyZhuBoActivity.class));
        } else {
            if (i == 3) {
                startActivity(new Intent(context, MyActivityActivity.class));
            } else if (i == 4) {
                startActivity(new Intent(context, MyCountActivity.class));
            } else if (i == 5) {
                startActivity(new Intent(context, MyCollectionActivity.class));
            } else if (i == 6) {
                startActivity(new Intent(context, ListenHistoryActivity.class));
            } else if (i == 7) {
                startActivity(new Intent(context, AdviceActivity.class));
            } else if (i == 8) {
                startActivity(new Intent(context, SettingActivity.class));
            }
        }

    }

    class ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mItemnames.length;
        }

        @Override
        public Object getItem(int i) {
            return mItemnames[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.e("--------", "getview..........");
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_minlistview_item, viewGroup, false);
                viewHolder.itemname = (TextView) view.findViewById(R.id.itemname);
                viewHolder.itemlogo = (ImageView) view.findViewById(R.id.itemlog);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            String s = mItemnames[i];
            viewHolder.itemname.setText(s);
            viewHolder.itemlogo.setImageResource(itemlogo[i]);
            return view;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TAG", "onActivityCreated: ======>");
        cv = (CircleImageView) getActivity().findViewById(R.id.cv);
        mf_username = (TextView) getActivity().findViewById(R.id.mf_username);

        isLoadAgain();

        if (cv == null) {
            Log.e("TAG", "onActivityCreated: =========>cv==null");
        } else {

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLoaded) {
                        Intent intent = new Intent(context, PersonInfoActivity.class);
                        intent.putExtra("iconurl", iconurl);
                        intent.putExtra("username", username);
                        Log.e("TAG", "onClick: ===>" + iconurl);
                        Log.e("TAG", "onClick: ===>" + username);
                        startActivityForResult(intent, 101);
                    } else {

                        startActivityForResult(new Intent(context, LoginActivity.class), 100);

                    }
                }
            });


        }
    }


    static class ViewHolder {
        TextView itemname;
        ImageView itemlogo;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            MySharePreferrences.setLoadState(getContext(), true);


            iconurl = data.getStringExtra("iconurl");
            username = data.getStringExtra("username");

            Log.e("TAG", "onActivityResult: ====>跳回来了" + iconurl);
            Log.e("TAG", "onActivityResult: username=====>" + username);

            if ((cv != null) || (mf_username != null)) {
                Glide.with(context).load(iconurl).into(cv);
                mf_username.setText(username);
                isLoaded = true;

            } else {
                Log.e("TAG", "onActivityResult: " + (cv == null));
            }
        } else if (resultCode == 201) {
            Log.e("TAG", "onActivityResult: =====>201");
            cv.setImageResource(R.mipmap.touxiang);
            mf_username.setText("用户名 ");
            isLoaded = false;
        }
    }
}

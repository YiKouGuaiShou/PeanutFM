package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.yikouguaishou.peanutfm.fragment.MineFragment;
import com.yikouguaishou.peanutfm.fragment.RadioStationFragment;
import com.yikouguaishou.peanutfm.fragment.RecommendFragment;
import com.yikouguaishou.peanutfm.fragment.SortFragment;
import com.yikouguaishou.peanutfm.fragment.adapter.MainActivityViewPagerAdapter;
import com.yikouguaishou.peanutfm.utils.MySharePreferrences;
import com.yikouguaishou.peanutfm.utils.TimeTranstor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private MainActivityViewPagerAdapter mAdapter;

    private long firstTime=0;
    private String iconurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        //初始化指示条
        mTabLayout.setSelectedTabIndicatorHeight(0);
        mTabLayout.scheduleLayoutAnimation();

        mFragments = new ArrayList<>();
        mFragments.add(new MineFragment());
        mFragments.add(new RecommendFragment());
        mFragments.add(new RadioStationFragment());
        mFragments.add(new SortFragment());

        mAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(1);

        mViewPager.addOnPageChangeListener(new MyPagerChangeListener());

        long nowtime=System.currentTimeMillis();
        Log.e("TAG", "onCreate: "+"这里获取的nowtime"+nowtime);
        long lasttime= MySharePreferrences.getLasttime(this);
        Log.e("TAG", "onCreate: "+"这里获取的上次的lasttime"+lasttime);

//        Toast.makeText(MainActivity.this, "上次登录是"+(nowtime-lasttime)/1000+"秒前", Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "上次登录是"+ TimeTranstor.getTime(lasttime), Toast.LENGTH_SHORT).show();


        if (nowtime-lasttime<360000000){

        }
    }





    /**
     * 点击搜索按钮跳转。
     *
     * @param view
     */
    public void onSearchClick(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 偏移量不为0 就显示指示条。
         *
         * @param position
         * @param offset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
            if (offset != 0) {
                mTabLayout.setSelectedTabIndicatorHeight(1);
            } else {
                mTabLayout.setSelectedTabIndicatorHeight(0);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {

            long secondTime=System.currentTimeMillis();
            if (secondTime-firstTime>800)
            {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime=secondTime;
                return  true;
            }
            else
            {
                ShareSDK.initSDK(MainActivity.this);
                Platform qq= ShareSDK.getPlatform(this, QQ.NAME);
                if (qq.isAuthValid())
                {
                    Log.e("TAG", "onKeyUp:===>授权情况 "+qq.isAuthValid());

                    qq.removeAccount(true);
                    Log.e("TAG", "onKeyUp:===>授权情况 "+qq.isAuthValid());

                }
                MySharePreferrences.setTime(MainActivity.this);
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);

    }
}

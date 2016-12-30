package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yikouguaishou.peanutfm.fragment.MineFragment;
import com.yikouguaishou.peanutfm.fragment.RadioStationFragment;
import com.yikouguaishou.peanutfm.fragment.RecommendFragment;
import com.yikouguaishou.peanutfm.fragment.SortFragment;
import com.yikouguaishou.peanutfm.fragment.adapter.MainActivityViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private MainActivityViewPagerAdapter mAdapter;

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
}

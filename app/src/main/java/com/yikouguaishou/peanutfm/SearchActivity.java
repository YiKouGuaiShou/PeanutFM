package com.yikouguaishou.peanutfm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yikouguaishou.peanutfm.bean.SearchResult;
import com.yikouguaishou.peanutfm.fragment.adapter.SearchAdapter;
import com.yikouguaishou.peanutfm.view.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        SearchView.SearchViewListener,
        RadioGroup.OnCheckedChangeListener {
    private ListView lv_results;
    private RadioGroup radioGroup;
    private SearchView searchView;
    private List<SearchResult> resultDatas;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
//        initData();
        initViews();
        setListener();
    }



    private void initViews() {
        lv_results = (ListView) findViewById(R.id.mListView_search);
        searchView = (SearchView) findViewById(R.id.search_layout);
        radioGroup = (RadioGroup) findViewById(R.id.mRadioGroup_search);
    }

//    private void initData() {
//        getResultData(null);
//    }

    private void setListener() {
        searchView.setSearchViewListener(this);
        lv_results.setOnItemClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    /**
     * 获取搜索结果
     * @param text
     */
//    private void getResultData(String text){
//        if (resultDatas == null){
//            //初始化
//            resultDatas = new ArrayList<>();
//        } else {
//            resultDatas.clear();
//        }
//        if (searchAdapter == null){
//            searchAdapter = new SearchAdapter(SearchActivity.this, resultDatas);
//        } else {
//            searchAdapter.notifyDataSetChanged();
//        }
//    }

    /**
     * 点击搜索按钮时editText触发回调
     * @param text
     */
    public void onSearch(String text){
        //更新搜索结果数据
//        getResultData(text);
        lv_results.setVisibility(View.VISIBLE);
        //第一次获取结果，还未配置适配器
        if (lv_results.getAdapter() == null){
            Toast.makeText(SearchActivity.this, "加载中...", Toast.LENGTH_SHORT).show();
            //获取搜索结果，设置适配器
            lv_results.setAdapter(searchAdapter);
        } else {
            //更新搜索数据
            searchAdapter.notifyDataSetChanged();
            Toast.makeText(SearchActivity.this, "搜索完成！", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(SearchActivity.this, i + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.mRadioButton_album:
                Toast.makeText(this, "专辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mRadioButton_single:
                Toast.makeText(this, "单曲", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}

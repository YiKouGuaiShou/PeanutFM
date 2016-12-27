package com.yikouguaishou.peanutfm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yikouguaishou.peanutfm.apiservice.HttpApiService;
import com.yikouguaishou.peanutfm.bean.SearchResult;
import com.yikouguaishou.peanutfm.fragment.adapter.SearchAdapter;
import com.yikouguaishou.peanutfm.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        SearchView.SearchViewListener,
        RadioGroup.OnCheckedChangeListener {
    private static String baseUrl = "http://fsapp.linker.cc/";
    private EditText et_search;
    private ListView lv_results;
    private RadioGroup radioGroup;
    private SearchView searchView;
    private RelativeLayout rl_search_result;
    private SearchAdapter searchAdapter;
    private List<SearchResult.ConBean> resultDatas;
    private int type;
    private List<SearchResult.ConBean> resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initData();
        initViews();
        setListener();
    }


    private void initViews() {
        et_search = (EditText) findViewById(R.id.mEditText_search);
        lv_results = (ListView) findViewById(R.id.mListView_search);
        searchView = (SearchView) findViewById(R.id.search_layout);
        radioGroup = (RadioGroup) findViewById(R.id.mRadioGroup_search);
        rl_search_result = (RelativeLayout) findViewById(R.id.rl_search_result);
    }

    private void initData() {
        getResultData(null);
    }

    private void setListener() {
        searchView.setSearchViewListener(this);
        lv_results.setOnItemClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    /**
     * 获取搜索结果
     *
     * @param text
     */
    private void getResultData(String text) {
        if (resultDatas == null) {
            //初始化
            resultDatas = new ArrayList<>();
        } else {
            resultDatas.clear();
            if (resultData != null) {
                resultDatas.addAll(resultData);
            }
            searchAdapter.notifyDataSetChanged();
        }
        if (searchAdapter == null) {
            searchAdapter = new SearchAdapter(SearchActivity.this, resultDatas);
        } else {
            searchAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 点击搜索按钮时editText触发回调
     *
     * @param text
     */
    public void onSearch(String text) {

        //更新搜索结果数据
        getResultData(text);
        rl_search_result.setVisibility(View.VISIBLE);
        //第一次获取结果，还未配置适配器
        if (lv_results.getAdapter() == null) {
            Toast.makeText(SearchActivity.this, "加载中...", Toast.LENGTH_SHORT).show();
            //获取搜索结果，设置适配器
            lv_results.setAdapter(searchAdapter);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            HttpApiService httpApiService = retrofit.create(HttpApiService.class);

            Observable<SearchResult> resultCall = httpApiService.getEditTextData(et_search.getText().toString());

            resultCall.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()
                    ).subscribe(new Subscriber<SearchResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(SearchResult searchResult) {
                    resultData = searchResult.getCon();
                    if (resultData != null) {
                        resultDatas.addAll(resultData);
                    }
                    searchAdapter.notifyDataSetChanged();
                }
            });

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
    public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
        switch (radioButtonId) {
            case R.id.mRadioButton_album:
                for (int i = 0; i < resultDatas.size(); i++) {
                    if (type == 3) {
                        searchAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.mRadioButton_single:
                for (int i = 0; i < resultDatas.size(); i++) {
                    if (type == 4) {
                        searchAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}

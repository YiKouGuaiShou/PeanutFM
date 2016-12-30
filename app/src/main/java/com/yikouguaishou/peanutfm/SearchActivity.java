package com.yikouguaishou.peanutfm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yikouguaishou.peanutfm.apiservice.HttpApiService;
import com.yikouguaishou.peanutfm.bean.SearchResult;
import com.yikouguaishou.peanutfm.fragment.adapter.SearchAdapter;

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
        implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, TextView.OnEditorActionListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private RadioGroup radioGroup;
    private EditText et_search;
    private RelativeLayout rl_search_result;
    private Button btn_back;
    private ImageView iv_delete;
    private ListView lv_results;

    private List<SearchResult.ConBean> resultDatas = new ArrayList<>();
    private List<SearchResult.ConBean> resultData;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initViews();
        initData();
        setListener();
    }

    private void initViews() {
        lv_results = (ListView) findViewById(R.id.mListView_search);
        et_search = (EditText) findViewById(R.id.mEditText_search);
        iv_delete = (ImageView) findViewById(R.id.mImageView_delete);
        btn_back = (Button) findViewById(R.id.mButton_back);
        radioGroup = (RadioGroup) findViewById(R.id.mRadioGroup_search);
        rl_search_result = (RelativeLayout) findViewById(R.id.rl_search_result);
    }

    private void initData() {
        searchAdapter = new SearchAdapter(this, resultDatas);
        lv_results.setAdapter(searchAdapter);
    }

    private void setListener() {
        radioGroup.setOnCheckedChangeListener(this);
        iv_delete.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        et_search.addTextChangedListener(new EditChangeListener());
        et_search.setOnClickListener(this);
        et_search.setOnEditorActionListener(this);
    }

    private void notifyStartSearching() {
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 点击搜索时触发
     *
     * @param textView 输入框
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            //显示搜索结果所在的控件
            rl_search_result.setVisibility(View.VISIBLE);
            notifyStartSearching();
            getResultData();
            return true;
        }
        return false;
    }

    /**
     * 输入框状态改变的监听
     */
    private class EditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            iv_delete.setVisibility(View.GONE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!"".equals(charSequence.toString())) {
                iv_delete.setVisibility(View.VISIBLE);
            } else {
                iv_delete.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 0) {
                iv_delete.setVisibility(View.GONE);
            } else {
                iv_delete.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.mRadioButton_album:   //专辑
                if (resultData != null) {
                    resultDatas.clear();
                    for (int i = 0; i < this.resultData.size(); i++) {
                        SearchResult.ConBean conBean = this.resultData.get(i);
                        int type = conBean.getType();
                        if (type == 3) {
                            resultDatas.add(conBean);
                        }
                    }
                    searchAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.mRadioButton_single:  //单曲
                if (resultData != null) {
                    resultDatas.clear();
                    for (int i = 0; i < this.resultData.size(); i++) {
                        SearchResult.ConBean conBean = this.resultData.get(i);
                        int type = conBean.getType();
                        if (type == 4) {
                            resultDatas.add(conBean);
                        }
                    }
                    searchAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 获取搜索结果
     */
    private void getResultData() {
        String word = et_search.getText().toString();
        Log.e("======word===", "===word==" + word);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        HttpApiService httpApiService = retrofit.create(HttpApiService.class);

        Observable<SearchResult> resultCall = httpApiService.getEditTextData(word);

        resultCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "=====" + e.getMessage());
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        resultData = searchResult.getCon();
                        for (int i = 0; i < 20; i++) {
                            Log.e("======onNext===", "===name===" + resultData.get(i).getName());
                            Log.e("======onNext===", "===Artist===" + resultData.get(i).getArtist());
                            Log.e("======onNext===", "===ProviderName===" + resultData.get(i).getProviderName());
                            Log.e("======onNext===", "===LogoUrl===" + resultData.get(i).getLogoUrl());
                            Log.e("======onNext===", "===Type===" + resultData.get(i).getType());
                            Log.e("======onNext===", "===PlayUrl===" + resultData.get(i).getPlayUrl());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mImageView_delete:    //删除
                et_search.setText("");
                iv_delete.setVisibility(View.GONE);
                break;
            case R.id.mButton_back:         //返回
                finish();
                break;
        }
    }

}
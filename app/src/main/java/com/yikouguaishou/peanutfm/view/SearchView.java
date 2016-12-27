package com.yikouguaishou.peanutfm.view;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yikouguaishou.peanutfm.R;

/**
 * Created by snowflake on 2016/12/27.
 */
public class SearchView extends LinearLayout implements View.OnClickListener {
    //搜索框输入框
    private EditText et_search;
    //返回按钮
    private Button btn_back;
    //删除键
    private ImageView iv_delete;

    private Context context;

    //搜索回调接口
    private SearchViewListener searchViewListener;

    /**
     * 设置搜索回调接口
     *
     * @param searchViewListener
     */
    public void setSearchViewListener(SearchViewListener searchViewListener) {
        this.searchViewListener = searchViewListener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        //初始化控件
        initViews();
        //设置监听
        setListener();

    }

    /**
     * 初始化控件
     */
    private void initViews() {
        et_search = (EditText) findViewById(R.id.mEditText_search);
        iv_delete = (ImageView) findViewById(R.id.mImageView_delete);
        btn_back = (Button) findViewById(R.id.mButton_back);
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        iv_delete.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        et_search.addTextChangedListener(new EditChangeListener());
        et_search.setOnClickListener(this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    notifyStartSearching(et_search.getText().toString());
                }
                return true;
            }
        });
    }

    private void notifyStartSearching(String text) {
        if (searchViewListener != null) {
            searchViewListener.onSearch(et_search.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class EditChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!"".equals(charSequence.toString())) {
                iv_delete.setVisibility(VISIBLE);
            } else {
                iv_delete.setVisibility(GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mImageView_delete:
                et_search.setText("");
                iv_delete.setVisibility(GONE);
                break;
            case R.id.mButton_back:
                ((Activity) context).finish();
                break;
        }
    }

    public interface SearchViewListener {
        //开始搜索
        void onSearch(String text);
    }
}

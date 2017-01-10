package com.yikouguaishou.peanutfm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 跳转这个界面注意：
 * linkType
 * 1 代表是typeZero webView布局：（对应"热点粤谈" item点击事件）
 * 2 代表typeTwo webView布局： （对应"俊青K歌榜" item点击事件）
 * 3 代表turnThree webView布局：（对应"商城" 点击事件）
 * 1,2,3 是自己定义的 不是服务器给的。
 * 因为服务器那个没找到规律。
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mBack;
    private TextView mTitle;
    private ImageButton mShare;
    private WebView mWebView;
    private String name;
    private String linkUrl;
    private ProgressBar bar;
    private int linkType;
    private PopupWindow popupWindow;
    private CardView mCardBtn;
    private TextView mCardBtnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        linkUrl = getIntent().getStringExtra("linkUrl");
        //linkType  1 代表是typeZero webView布局，2 代表typeTwo webView布局，3 代表turnThree webView布局。
        linkType = getIntent().getIntExtra("linkType", 0);
        setContentView(R.layout.activity_web_view);
        mBack = (ImageButton) findViewById(R.id.web_back);
        mTitle = (TextView) findViewById(R.id.web_title);
        mShare = (ImageButton) findViewById(R.id.web_share);
        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        mWebView = (WebView) findViewById(R.id.web_content);
        mCardBtn = (CardView) findViewById(R.id.card_btn);
        mCardBtnTv = (TextView) findViewById(R.id.card_btn_tv);
        if (linkType == 2) {
            mCardBtn.setVisibility(View.VISIBLE);
            mCardBtn.setOnClickListener(this);
        } else {
            mCardBtn.setVisibility(View.GONE);
        }
        initData();
        setLisener();
    }

    public void initData() {
        popupWindow = new PopupWindow(this);
        if (null == name) {
            mTitle.setText("图文详情");
        } else {
            mTitle.setText(name);
        }
        //设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.getSettings().setJavaScriptEnabled(true);

        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                bar.setMax(100);
                if (newProgress == 100) {
                    bar.setProgress(100);
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        boolean http = linkUrl.startsWith("http");
        if (http) {
            mWebView.loadUrl(linkUrl);
        } else {
            mWebView.loadDataWithBaseURL(null, linkUrl, "text/html", "UTF-8", null);
        }
    }

    private void setLisener() {
        mBack.setOnClickListener(this);
        mShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.web_back:
                finish();
                break;
            case R.id.web_share:
                if (linkType == 3) {
                    setLinkTypeThree();
                } else {
                    setLinkTypeOther();
                }
                if (!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(mShare);
                } else {
                    popupWindow.dismiss();
                }
                break;
            case R.id.card_home:
                popupWindow.dismiss();
                finish();
                break;
            case R.id.card_refresh:
                mWebView.reload();
                popupWindow.dismiss();
                break;
            case R.id.card_comments:
                //TODO 评论
                popupWindow.dismiss();
                break;
            case R.id.card_share:
                //TODO 分享
                popupWindow.dismiss();
                break;
            case R.id.card_btn:
                Toast.makeText(WebViewActivity.this, "我要报名", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setLinkTypeOther() {
        View vie = LayoutInflater.from(this).inflate(R.layout.opwindow_more_layout, null);
        CardView card_share = (CardView) vie.findViewById(R.id.card_share);
        CardView card_refresh = (CardView) vie.findViewById(R.id.card_refresh);
        CardView card_comments = (CardView) vie.findViewById(R.id.card_comments);
        CardView card_home = (CardView) vie.findViewById(R.id.card_home);
        card_share.setOnClickListener(this);
        card_refresh.setOnClickListener(this);
        card_comments.setOnClickListener(this);
        card_home.setOnClickListener(this);
        popupWindow.setContentView(vie);
    }

    public void setLinkTypeThree() {
        //linkType3 menu 比linkType1和linkType2，少了分享和评论。
        View vie = LayoutInflater.from(this).inflate(R.layout.popwindow_less_layout, null);
        CardView card_refresh = (CardView) vie.findViewById(R.id.card_refresh);
        CardView card_home = (CardView) vie.findViewById(R.id.card_home);
        card_refresh.setOnClickListener(this);
        card_home.setOnClickListener(this);
        popupWindow.setContentView(vie);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        return super.onTouchEvent(event);
    }
}

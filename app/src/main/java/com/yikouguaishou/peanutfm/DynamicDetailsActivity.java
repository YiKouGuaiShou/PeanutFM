package com.yikouguaishou.peanutfm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yikouguaishou.peanutfm.bean.AnchorDynamicListBean;

import de.hdodenhof.circleimageview.CircleImageView;

public class DynamicDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private AnchorDynamicListBean.ConBean dynamicDatas;

    private CircleImageView civ_dynamicDetails_photo;
    private RoundedImageView riv_dynamicDetails_contnetPic;
    private TextView tv_dynamicDetails_name;
    private TextView tv_dynamicDetails_content;
    private TextView tv_dynamicDetails_createTime;
    private TextView tv_dynamicDetails_praiseCount;
    private TextView tv_dynamicDetails_commentNum;
    private Button btn_comment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        bundle = getIntent().getExtras();
        dynamicDatas = (AnchorDynamicListBean.ConBean) bundle.getSerializable("dynamicDatas");

        initViews();
        setListener();
    }

    private void initViews() {
        civ_dynamicDetails_photo = (CircleImageView) findViewById(R.id.mCircleImageView_dynamicDetails_photo);
        riv_dynamicDetails_contnetPic = (RoundedImageView) findViewById(R.id.mRoundedImageView_dynamicDetails_contentPic);
        tv_dynamicDetails_name = (TextView) findViewById(R.id.mTextView_dynamicDetails_name);
        tv_dynamicDetails_content = (TextView) findViewById(R.id.mTextView_dynamicDetails_content);
        tv_dynamicDetails_createTime = (TextView) findViewById(R.id.mTextView_dynamicDetails_createTime);
        tv_dynamicDetails_praiseCount = (TextView) findViewById(R.id.mTextView_dynamicDetails_praiseCount);
        tv_dynamicDetails_commentNum = (TextView) findViewById(R.id.mTextView_dynamicDetails_commentNum);
        btn_comment = (Button) findViewById(R.id.mButton_dynamicDetails_comment);

        if (bundle != null) {
            String anchorName = dynamicDatas.getAnchorName();
            tv_dynamicDetails_name.setText(anchorName);
            String content = dynamicDatas.getContent();
            tv_dynamicDetails_content.setText(content);
            String createTime = dynamicDatas.getCreateTime();
            tv_dynamicDetails_createTime.setText(createTime);
            int praiseCount = dynamicDatas.getPraiseCount();
            tv_dynamicDetails_praiseCount.setText("" + praiseCount);
            int commentNum = dynamicDatas.getCommentNum();
            tv_dynamicDetails_commentNum.setText("" + commentNum);

            String imgUrl = dynamicDatas.getImgList().get(0).getUrl();
            if (imgUrl != null) {
                Glide.with(this)
                        .load(imgUrl)
                        .into(riv_dynamicDetails_contnetPic);
            }
            String anchorImg = dynamicDatas.getAnchorImg();
            if (anchorImg != null) {
                Glide.with(this)
                        .load(anchorImg)
                        .into(civ_dynamicDetails_photo);
            }
        }
    }

    private void setListener() {
        riv_dynamicDetails_contnetPic.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mButton_dynamicDetails_comment:
                Toast.makeText(this, "评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mRoundedImageView_dynamicDetails_contentPic:

                break;
        }
    }
}

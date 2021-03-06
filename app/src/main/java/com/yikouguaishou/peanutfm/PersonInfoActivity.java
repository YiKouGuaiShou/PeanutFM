package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.utils.MySharePreferrences;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfoActivity extends AppCompatActivity {

    private TextView pc_username;
    private CircleImageView pc_cv;
    private TextView pc_nickname;
    private Intent intent;
    private boolean isExit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        intent = getIntent();

        initView();

        String iconurl=MySharePreferrences.getIconurl(PersonInfoActivity.this);
        String username=MySharePreferrences.getUsername(PersonInfoActivity.this);

        if ((iconurl!=null)&&(username!=null))
        {
            Log.e("TAG", "读取到了onCreateView:  iconurl"+iconurl);
            Log.e("TAG", "读取到了onCreateView:  username"+username);


            if (pc_cv==null||pc_username==null)
            {
                Log.e("TAG", "onCreateView: ===========>null");
            }
            else {
                Glide.with(PersonInfoActivity.this).load(iconurl).into(pc_cv);
                pc_username.setText(username);
                pc_nickname.setText(username);

            }
        }



    }

    private void initView() {



        pc_cv = (CircleImageView) findViewById(R.id.pc_cv);
        pc_username = (TextView) findViewById(R.id.pc_username);
        pc_nickname = (TextView) findViewById(R.id.pc_nickname);

        if (intent !=null)
        {


            Log.e("TAG", "initView: ====>"+intent.getStringExtra("iconurl"));
            Log.e("TAG", "initView: ====>"+intent.getStringExtra("username"));


            pc_username.setText(intent.getStringExtra("username"));
            pc_nickname.setText("昵称             "+intent.getStringExtra("username"));
            Glide.with(this).load(intent.getStringExtra("iconurl")).into(pc_cv);
        }

    }

    public void goBack(View view) {
        if (isExit)
        {

            MySharePreferrences.setLoadState(PersonInfoActivity.this,false);
            Intent intent=new Intent();
            intent.putExtra("isExit",isExit);
            setResult(201,intent);
        }
        finish();
    }

    public void exitSafe(View view) {
        ShareSDK.initSDK(PersonInfoActivity.this);
        Platform qq= ShareSDK.getPlatform(this,QQ.NAME);
        if (qq.isAuthValid())
        {
            qq.removeAccount();

        }
        Toast.makeText(PersonInfoActivity.this, "已退出", Toast.LENGTH_SHORT).show();

        pc_cv.setImageResource(R.mipmap.touxiang);
        pc_nickname.setText("昵称");
        pc_username.setText("用户名");
        MySharePreferrences.setLoadState(PersonInfoActivity.this,false);

        MySharePreferrences.setIconurl(PersonInfoActivity.this,null);
        MySharePreferrences.setUsername(PersonInfoActivity.this,null);
        isExit=true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            if (isExit)
            {

                MySharePreferrences.setLoadState(PersonInfoActivity.this,false);
                Intent intent=new Intent();
                intent.putExtra("isExit",isExit);
                setResult(201,intent);
            }
        }
        return super.onKeyUp(keyCode, event);

    }
}

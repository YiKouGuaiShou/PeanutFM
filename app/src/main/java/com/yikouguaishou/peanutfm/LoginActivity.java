package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yikouguaishou.peanutfm.fragment.MineFragment;
import com.yikouguaishou.peanutfm.utils.APP;
import com.yikouguaishou.peanutfm.utils.MySharePreferrences;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends AppCompatActivity {
    APP app;
    private String iconurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void goRegist(View view) {
        startActivity(new Intent(this,RegistActivity.class));
    }

    public void goBack(View view) {
        finish();
    }

    public void QQlogin(View view) {
        ShareSDK.initSDK(this);
        Platform platform=ShareSDK.getPlatform(this, QQ.NAME);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("TAG", "onComplete: ======>登录成功");


                    iconurl = hashMap.get("figureurl_qq_2").toString();


                String userid=platform.getDb().getUserId();
                if (userid!=null)
                {
                    Log.e("TAG", "onComplete: userid==:"+userid);
                    MySharePreferrences.setUserID(LoginActivity.this,userid);
                }



                String username=platform.getDb().getUserName();

                Intent intent = new Intent();
                intent.putExtra("iconurl", iconurl);
                intent.putExtra("username",username);
                setResult(200,intent);
                finish();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("TAG", "onError: ======>登录失败！原因是:"+throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        platform.showUser(null);


    }

//    public void SinaLogin(View view) {
//        ShareSDK.initSDK(this);
//        Platform platform=ShareSDK.getPlatform(this, SinaWeibo.NAME);
//        platform.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Log.e("TAG", "onComplete: ======>登录成功");
//
//
//
//
//                    iconurl = hashMap.get("avatar_large").toString();
//
//
//                String username=platform.getDb().getUserName();
//
//                Intent intent = new Intent();
//                intent.putExtra("iconurl", iconurl);
//                intent.putExtra("username",username);
//                setResult(200,intent);
//                finish();
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Log.e("TAG", "onError: ======>登录失败！原因是:"+throwable.getMessage());
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//
//            }
//        });
//        platform.showUser(null);
//    }


}

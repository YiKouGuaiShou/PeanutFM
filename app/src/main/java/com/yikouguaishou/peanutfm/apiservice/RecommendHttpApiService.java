package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.RecommendBean;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/27.
 */
public interface RecommendHttpApiService {
    @POST("fslhsrv/srv/interactive/index")
    Observable<RecommendBean> getRecommendBean();
}

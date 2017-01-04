package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.RecommendBean;
import com.yikouguaishou.peanutfm.bean.TurnOneItemBean;
import com.yikouguaishou.peanutfm.bean.TurnTwoItemBean;
import com.yikouguaishou.peanutfm.bean.TypeOneItemBean;
import com.yikouguaishou.peanutfm.bean.TypeThreeItemBean;
import com.yikouguaishou.peanutfm.bean.TypeZeroItemBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/27.
 */
public interface RecommendHttpApiService {
    @POST("fslhsrv/srv/interactive/index")
    Observable<RecommendBean> getRecommendBean();

    @GET("fslhsrv/srv/radio/channelAlbumDetail/list/{id}/{pageIndex}")
    Observable<TypeThreeItemBean> getTypeThreeItemBean
            (@Path("id") int id, @Path("pageIndex") int pageIndex);

    @GET("fslhsrv/srv/radio/news_list/{id}")
    Observable<TypeZeroItemBean> getTypeZeroItemBean
            (@Path("id") int id);

    @GET("fslhsrv/srv/radio/news_list/{id}")
    Observable<TypeOneItemBean> getTypeOneItemBean
            (@Path("id") int id);

    @GET("fslhsrv/srv/radio/coampaign_list/null")
    Observable<TurnOneItemBean> getTurnOneItemBean();

    @FormUrlEncoded
    @POST("fslhsrv/srv/video/videoList")
    Observable<TurnTwoItemBean> getTurnTwoItemBean(@Field("sort") int sort);
}

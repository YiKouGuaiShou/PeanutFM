package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.RecommendBean;
import com.yikouguaishou.peanutfm.bean.TypeThreeItemBean;

import retrofit2.http.GET;
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
}
//    @Query("id") String id,
//    @Query("pageIndex") String pageIndex);
package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.AnchorDynamicListBean;
import com.yikouguaishou.peanutfm.bean.AnchorPersonInfoBean;
import com.yikouguaishou.peanutfm.bean.AnchorProductBean;
import com.yikouguaishou.peanutfm.bean.RadioStationBannerBean;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/27.
 */
public interface RadioStationAPIService {
    @GET("fslhsrv/srv/radio/live_radiostudio/list/0")
    Observable<RadioStationBean> RadioStationList();

    @Headers({"Content-Type:text/html; charset=UTF-8"})
    @POST("fslhsrv/srv/djStudio/details")
    Observable<RadioStationBannerBean> RadioStationBannerList(
            @Query("broadCastId") String broadCastId);

    @Headers({"Content-Type: application/x-www-form-urlencoded; charset=UTF-8"})
    @POST("fslhsrv/srv/interactive/getAnchorPersonInfo")
    Observable<AnchorPersonInfoBean> getAnchorPersonInfo(
            @Query("anchorId") String anchorId,
            @Query("userId") String userId);

    @FormUrlEncoded
    @POST("fslhsrv/srv/interactive/getAnchorDynamicList")
    Observable<AnchorDynamicListBean> getAnchorDynamicList(
            @Field("fId") int fId,
            @Field("anchorId") String anchorId,
            @Field("userId") String userId);

    @FormUrlEncoded
    @POST("fslhsrv/srv/interactive/getAnchorProduct")
    Observable<AnchorProductBean> getAnchorProduct(
            @Field("anchorId") String anchorId);
}

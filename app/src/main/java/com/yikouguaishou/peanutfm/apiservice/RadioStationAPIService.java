package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.RadioStationBannerBean;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;

import retrofit2.http.GET;
import retrofit2.http.Header;
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
}

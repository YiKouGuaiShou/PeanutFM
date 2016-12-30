package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.SortDetailsBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by snowflake on 2016/12/30.
 */
public interface SortDetailsApiService {
    @Headers({"Content-Type:text/html; charset=UTF-8"})
    @GET("fslhsrv/srv/classification/channelIndex/{id}/13010")
    Observable<SortDetailsBean> getSortDetailsData(
            @Path("id") int id);
}

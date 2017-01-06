package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.ColumnListBean;
import com.yikouguaishou.peanutfm.bean.SortBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by snowflake on 2016/12/29.
 */
public interface SortApiService {
    @GET("fslhsrv/srv/classification/channelType/13010")
    Observable<SortBean> getSortData();

    @Headers({"Content-Type:application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("fslhsrv/srv/wifimusicbox/demand/detail")
    Observable<ColumnListBean> getColumnListData(
            @Query("providerCode") String providerCode,
            @Query("sortType") int sortType,
            @Query("pid") String pid,
            @Query("mobileId") String mobileId,
            @Query("pageIndex") int pageIndex
    );
}

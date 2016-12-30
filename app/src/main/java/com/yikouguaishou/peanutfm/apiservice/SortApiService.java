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
    @Headers({"Content-Type:text/html; charset=UTF-8"})
    @GET("fslhsrv/srv/classification/channelType/13010")
    Observable<SortBean> getSortData();

    @Headers({"Content-Type:text/html; charset=UTF-8"})
    @POST("/fslhsrv/srv/wifimusicbox/demand/detail")
    Observable<ColumnListBean> getColumnListData(
            @Query("providerCode") int providerCode,
            @Query("pid") int pid,
            @Query("pageIndex") int pageIndex
    );
}

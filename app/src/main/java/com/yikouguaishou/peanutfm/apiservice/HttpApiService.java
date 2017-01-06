package com.yikouguaishou.peanutfm.apiservice;


import com.yikouguaishou.peanutfm.bean.SearchResultBean;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by snowflake on 2016/12/27.
 */
public interface HttpApiService {

    @Headers({"Content-Type:text/html; charset=UTF-8"})
    @POST("fslhsrv/srv/wifimusicbox/search/-1/0")
    Observable<SearchResultBean> getEditTextData(
            @Query("word") String word);
}

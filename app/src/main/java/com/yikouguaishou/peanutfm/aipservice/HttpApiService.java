package com.yikouguaishou.peanutfm.aipservice;


import com.yikouguaishou.peanutfm.bean.SearchResult;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by snowflake on 2016/12/27.
 */
public interface HttpApiService {

    @POST("fslhsrv/srv/wifimusicbox/search/-1/0")
    Observable<SearchResult> getEditTextData(
            @Query("word") String word
    );
}

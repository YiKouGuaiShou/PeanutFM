package com.yikouguaishou.peanutfm.apiservice;


import com.yikouguaishou.peanutfm.bean.CollectBean;
import com.yikouguaishou.peanutfm.bean.SearchResultBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @FormUrlEncoded
    @POST("fslhsrv/srv/collect/addAlbum")
    Observable<CollectBean> addAlbum(
            @Field("providerCode") String providerCode,
            @Field("columnName") String columnName,
            @Field("mobileId") String mobileId,
            @Field("columnId") String columnId,
            @Field("pic") String pic);

    @FormUrlEncoded
    @POST("fslhsrv/srv/collect/deleteAlbum")
    Observable<CollectBean> deleteAlbum(
            @Field("columnId") String columnId,
            @Field("providerCode") String providerCode,
            @Field("mobileId") String mobileId);
}

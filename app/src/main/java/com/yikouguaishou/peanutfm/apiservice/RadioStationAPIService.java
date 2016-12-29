package com.yikouguaishou.peanutfm.apiservice;

import com.yikouguaishou.peanutfm.bean.RadioStationBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/27.
 */
public interface RadioStationAPIService {
    @GET("fslhsrv/srv/radio/live_radiostudio/list/0")
    Observable<RadioStationBean> RadioStationList();
}

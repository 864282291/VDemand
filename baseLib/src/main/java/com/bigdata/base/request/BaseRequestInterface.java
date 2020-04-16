package com.bigdata.base.request;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * Created by 通子 on 2017/12/21.
 */

public interface BaseRequestInterface {
    Call<CommonBean> call(@Query("request") String request);
}

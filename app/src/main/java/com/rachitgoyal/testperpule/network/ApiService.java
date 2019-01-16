package com.rachitgoyal.testperpule.network;

import com.rachitgoyal.testperpule.model.TestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public interface ApiService {

    @GET("networkCall")
    Call<TestModel> getModels(@Query("string") String temp);
}

package com.rachitgoyal.testperpule.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.ws.RealWebSocket;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public class RetrofitSingleton {

    private static RetrofitSingleton INSTANCE;
    private String mBaseUrl;
    private Retrofit mRetrofit;

    public static RetrofitSingleton get() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else {
            throw new IllegalStateException("Singleton not initialized");
        }
    }

    public Retrofit getHttpClient() {
        return mRetrofit;
    }

    private RetrofitSingleton(Context context, String baseUrl) {
        this.mBaseUrl = baseUrl;
        mRetrofit = getRetrofit(getGson(), getOkhttpClient(getHttpCache(context)));
    }

    public static void init(Context context, String baseUrl) {
        INSTANCE = new RetrofitSingleton(context, baseUrl);
    }

    private Cache getHttpCache(Context appContext) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(appContext.getCacheDir(), cacheSize);
    }

    private Gson getGson() {
        //setLenient() : By default, Gson is strict and only accepts JSON as specified by.
        //This option makes the parser liberal in what it accepts.
        GsonBuilder gsonBuilder = new GsonBuilder().setLenient();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    private class CustomOkHttpClient extends OkHttpClient {
        @Override
        public WebSocket newWebSocket(Request request, @NonNull WebSocketListener listener) {
            RealWebSocket webSocket = new RealWebSocket(request, listener, new SecureRandom(), 0);
            webSocket.connect(this);
            return webSocket;
        }
    }

    private OkHttpClient getOkhttpClient(Cache cache) {
        CustomOkHttpClient.Builder client = new CustomOkHttpClient.Builder();

        client.connectTimeout(5, TimeUnit.MINUTES);
        client.readTimeout(5, TimeUnit.MINUTES);
        client.addInterceptor(new HttpInterceptor());

        client.cache(cache);
        client.retryOnConnectionFailure(true);
        client.connectionPool(new ConnectionPool(0, 1, TimeUnit.NANOSECONDS));

        return client.build();
    }

    private Retrofit getRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }
}

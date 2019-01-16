package com.rachitgoyal.testperpule.network;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public class HttpInterceptor implements Interceptor {

    HttpInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = null;
        try {
            Request request = chain.request();
            //Build new request
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Authorization", "get Auth Token from secured shared prefs");

            builder.addHeader("Accept", "application/json"); //if necessary, say to consume JSON

            HttpUrl url = request.url().newBuilder().addQueryParameter("source", "VPOS").build();
            builder.url(url);

            request = builder.build(); //overwrite old request
            response = chain.proceed(request); //perform request, here original request will be executed
            if (response.code() == 401) { //if unauthorized
                refreshToken();
                Request newRequest;
                newRequest = request.newBuilder().header("Authorization", "get Auth Token from secured shared prefs").build();
                return chain.proceed(newRequest);


            } else if (response.code() == 403 || response.code() == 410) {
                logout();
            }
        } catch (SocketTimeoutException ignored) {
        }
        return response;
    }

    private void refreshToken() {
        // refresh token
    }

    private void logout() {
        // logout your user
    }
}
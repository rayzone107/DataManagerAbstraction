package com.rachitgoyal.testperpule.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public abstract class ServiceAbstract {

    protected final String mUniqueDeviceId;
    protected String mErrorMessage;
    protected ApiService mApiService;
    protected ConnectivityManager mConnectivityManager;
    protected Context mContext;

    @SuppressLint("HardwareIds")
    public ServiceAbstract(@NonNull Context context) {
        mContext = context;
        mUniqueDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Retrofit retrofit = RetrofitSingleton.get().getHttpClient();
        mApiService = retrofit.create(ApiService.class);
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    protected boolean isNetworkConnected() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        return !(info == null || !info.isConnected());
    }
}

package com.rachitgoyal.testperpule.module.main;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rachitgoyal.testperpule.exceptions.NoInternetException;
import com.rachitgoyal.testperpule.exceptions.PerpuleException;
import com.rachitgoyal.testperpule.model.TestModel;
import com.rachitgoyal.testperpule.network.ServiceAbstract;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public class MainServiceImpl extends ServiceAbstract implements MainServiceApi {

    public MainServiceImpl(@NonNull Context context) {
        super(context);
    }

    @Override
    public Observable<TestModel> getTestModels(final String test) {
        Observable<TestModel> responseObservable = Observable.create(new ObservableOnSubscribe<TestModel>() {
            @Override
            public void subscribe(ObservableEmitter<TestModel> emitter) throws Exception {
                if (isNetworkConnected()) {
                    Call<TestModel> call = mApiService.getModels(test);
                    try {
                        Response<TestModel> serviceResponse = call.execute();
                        if (serviceResponse.isSuccessful()) {
                            TestModel serviceResponseList = serviceResponse.body();
                            emitter.onNext(serviceResponseList);
                        } else {
                            emitter.onError(new PerpuleException(serviceResponse.code(), serviceResponse.message(), serviceResponse.errorBody()
                                    .string()));
                        }
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                } else {
                    emitter.onError(new NoInternetException());
                }
                emitter.onComplete();
            }
        });
        return responseObservable;
    }
}

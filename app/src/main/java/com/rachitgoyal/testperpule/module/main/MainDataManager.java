package com.rachitgoyal.testperpule.module.main;

import com.rachitgoyal.testperpule.model.TestModel;

import io.reactivex.Observable;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public class MainDataManager {

    private MainServiceApi mMainServiceApi;
    private MainPersistenceApi mMainPersistenceApi;

    public MainDataManager(MainServiceApi mainServiceApi, MainPersistenceApi mainPersistenceApi) {
        mMainServiceApi = mainServiceApi;
        mMainPersistenceApi = mainPersistenceApi;
    }

    public Observable<TestModel> getTest(String test) {
        return mMainServiceApi.getTestModels(test);
    }
}

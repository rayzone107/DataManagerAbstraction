package com.rachitgoyal.testperpule.module.main;

import com.rachitgoyal.testperpule.model.TestModel;

import io.reactivex.Observable;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public interface MainServiceApi {

    Observable<TestModel> getTestModels(String test);
}

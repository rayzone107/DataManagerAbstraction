package com.rachitgoyal.testperpule.module.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rachitgoyal.testperpule.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainDataManager mainDataManager = new MainDataManager(new MainServiceImpl(this), new MainPersistenceImpl());
    }
}

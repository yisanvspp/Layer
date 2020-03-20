package com.yisan.ui;

import android.app.Application;
import android.content.Context;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.ui
 * @date：2020/3/20 0020 上午 9:58
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }



    public static App getInstance(){
        return instance;
    }


    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
}

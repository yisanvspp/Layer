package com.yisan.ui.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.yisan.ui.App;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.ui.utils
 * @date：2020/3/20 0020 上午 9:55
 */
public class ToastUtils {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void show(final String msg){

        if (Looper.getMainLooper() == Looper.myLooper()){
            Toast.makeText(App.getAppContext(),msg,Toast.LENGTH_SHORT).show();
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(App.getAppContext(),msg,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

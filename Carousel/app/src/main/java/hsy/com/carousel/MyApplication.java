package hsy.com.carousel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

/**
 * Created by hsy on 16/6/1.
 */
public class MyApplication extends Application {
    private static final String TAG = "JPush";
    private static Context context;
    @Override
    public void onCreate() {
        Log.d(TAG, "[MyApplication] onCreate");
        super.onCreate();
        context = getApplicationContext();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        RongIM.init(this);
    }

    public static Context getContext(){
        return context;
    }
}

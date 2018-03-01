package cn.hugo.android.scanner;

import android.app.Application;

/**
 * Created by kay on 2017/11/23.
 */

public class QRApplication extends Application {
    public static Application sInstance;
    public static int state;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }
}

package eu.fbk.calc;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        this.context = getApplicationContext();
        super.onCreate();
    }
}

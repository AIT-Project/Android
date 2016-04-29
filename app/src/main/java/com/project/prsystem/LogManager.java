package com.project.prsystem;

        import android.util.Log;

/**
 * Created by skplanet on 2016-01-20.
 */
public class LogManager {
    private static final String TAG = "DataCheck";
    private static final boolean DEBUG = true;

    public static void logPrint(String text) {
        if(DEBUG) {
            Log.v(TAG, text);
        }
    }
}

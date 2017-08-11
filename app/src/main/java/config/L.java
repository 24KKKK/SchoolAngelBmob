package config;

import android.util.Log;

/**
 * Created by diy on 2017-08-11.
 */

public class L
{
    private static  final String TAG = "--Main--";
    private static final boolean DEBUG = true;

    public static void e(String s) {
        if (DEBUG) {
            Log.e(TAG , s);
        }
    }

    public static void e(String tag, String s) {
        if (DEBUG) {
            Log.e(tag, s);
        }
    }

    public static void i(String tag,String s) {
        if (DEBUG) {
            Log.i(tag, s);
        }
    }
}

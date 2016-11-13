package com.sayaanand.loantrackerv1.utils;

/**
 * Created by Nandkishore.Powar on 29/12/2015.
 */
public class LoggerUtils {

    static String TAG = "LaonTracker";

    public static void logInfo(String s){
        printLog(s);
    }

    private static void printLog(String s) {
        android.util.Log.i(TAG,s);
    }
}

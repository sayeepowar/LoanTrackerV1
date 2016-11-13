package com.sayaanand.loantrackerv1.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Nandkishore.Powar on 31/01/2016.
 */
public class Utility {

    public static void showToast(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}

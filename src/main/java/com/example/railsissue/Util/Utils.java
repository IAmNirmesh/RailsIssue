package com.example.railsissue.Util;

import android.app.Activity;
import android.widget.Toast;
import com.example.railsissue.MyApplication;

public class Utils {

    public static void handleException(Exception e) {
        // For now just print the stacktrace
        e.printStackTrace();
    }

    public static void showShotToast(int stringId) {
        Activity activity = MyApplication.getInstance().getCurrentActivity();
        Toast.makeText(activity, activity.getString(stringId), Toast.LENGTH_SHORT).show();
    }
}

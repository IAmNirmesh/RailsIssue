package com.example.railsissue.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.widget.BaseAdapter;
import com.example.railsissue.MyApplication;
import com.example.railsissue.R;

public class DialogFactory {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(int msgId) {
        Activity activity = MyApplication.getInstance().getCurrentActivity();
        String msg;
        if (msgId > 0) {
            msg = activity.getString(msgId);
        } else {
            msg = activity.getString(R.string.progress_dialog_msg);
        }
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void showCommentsDialog(BaseAdapter adapter) {
        Activity activity = MyApplication.getInstance().getCurrentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.title_comments);
        builder.setAdapter(adapter, null);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }
}

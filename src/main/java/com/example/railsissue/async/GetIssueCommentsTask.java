package com.example.railsissue.async;

import android.os.AsyncTask;
import com.example.railsissue.R;
import com.example.railsissue.dialog.DialogFactory;
import com.example.railsissue.model.Info;
import com.example.railsissue.webservice.WebServices;
import java.util.List;

public class GetIssueCommentsTask extends AsyncTask<String, Void, List<Info>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        DialogFactory.showProgressDialog(R.string.progress_dialog_comments_msg);
    }

    @Override
    protected List<Info> doInBackground(String... params) {
        return WebServices.getIssueComents(params[0]);
    }

    @Override
    protected void onPostExecute(List<Info> list) {
        super.onPostExecute(list);
        DialogFactory.dismissProgressDialog();
    }
}

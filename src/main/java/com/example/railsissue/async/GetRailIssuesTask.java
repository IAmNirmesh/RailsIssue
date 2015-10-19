package com.example.railsissue.async;

import android.os.AsyncTask;
import com.example.railsissue.R;
import com.example.railsissue.dialog.DialogFactory;
import com.example.railsissue.model.IssueInfo;
import com.example.railsissue.webservice.WebServices;
import java.util.List;

public class GetRailIssuesTask extends AsyncTask<Void, Void, List<IssueInfo>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        DialogFactory.showProgressDialog(R.string.progress_dialog_issue_msg);
    }

    @Override
    protected List<IssueInfo> doInBackground(Void... voids) {
        return WebServices.getRailRepoIssues();
    }

    @Override
    protected void onPostExecute(List<IssueInfo> list) {
        super.onPostExecute(list);
        DialogFactory.dismissProgressDialog();
    }
}

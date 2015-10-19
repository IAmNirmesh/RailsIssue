package com.example.railsissue.webservice;

import com.example.railsissue.Util.Utils;
import com.example.railsissue.model.Info;
import com.example.railsissue.model.IssueInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebServices {

    private static String callAPI(String api) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(api);
            HttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            Utils.handleException(e);
        }
        return null;
    }

    public static List<IssueInfo> getRailRepoIssues() {
        final String API = "https://api.github.com/repos/crashlytics/secureudid/issues?sort=updated";
        String response = callAPI(API);
        try {
            JSONArray jsonArray = new JSONArray(response);
            int size = jsonArray.length();
            List<IssueInfo> issueList = new ArrayList<>();
            for (int i=0;i<size;i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                IssueInfo issueInfo = new IssueInfo();
                issueInfo.setTitle(obj.getString("title"));
                issueInfo.setDescription(obj.getString("body"));
                issueInfo.setCommentsUrl(obj.getString("comments_url"));
                issueList.add(issueInfo);
            }

            return issueList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Info> getIssueComents(String commentsUrl) {
        String response = callAPI(commentsUrl);
        try {
            JSONArray jsonArray = new JSONArray(response);
            int size = jsonArray.length();
            List<Info> commentsList = new ArrayList<>();
            for (int i=0;i<size;i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Info comment = new Info();
                comment.setTitle(obj.getJSONObject("user").getString("login"));
                comment.setDescription(obj.getString("body"));
                commentsList.add(comment);
            }

            return commentsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

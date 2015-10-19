package com.example.railsissue.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.railsissue.R;
import com.example.railsissue.Util.Utils;
import com.example.railsissue.async.GetIssueCommentsTask;
import com.example.railsissue.async.GetRailIssuesTask;
import com.example.railsissue.dialog.DialogFactory;
import com.example.railsissue.model.Info;
import com.example.railsissue.model.IssueInfo;
import java.util.List;

public class IssueFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button loadApiButton;
    private ListView issueListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_railissues, null);
        loadApiButton = (Button) view.findViewById(R.id.apiButton);
        loadApiButton.setOnClickListener(this);
        issueListView = (ListView) view.findViewById(R.id.listView);
        issueListView.setOnItemClickListener(this);
        return view;
    }

    public void refresh() {
        new GetRailIssuesTask(){
            @Override
            protected void onPostExecute(List<IssueInfo> list) {
                super.onPostExecute(list);
                if (isAdded()) {
                    if (list != null) {
                        issueListView.setAdapter(new DisplayAdapter(list));
                        issueListView.setVisibility(View.VISIBLE);
                        loadApiButton.setVisibility(View.GONE);
                    } else {
                        Utils.showShotToast(R.string.error_getting_issue_list);
                    }
                }
            }
        }.execute();
    }

    @Override
    public void onClick(View view) {
       refresh();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ListAdapter listAdapter = issueListView.getAdapter();
        if (listAdapter instanceof DisplayAdapter) {
            Object item = ((DisplayAdapter)listAdapter).getItem(i);
            if (item instanceof IssueInfo) {
                new GetIssueCommentsTask() {
                    @Override
                    protected void onPostExecute(List<Info> list) {
                        super.onPostExecute(list);
                        if(isAdded()) {
                            DialogFactory.showCommentsDialog(new DisplayAdapter(list).setLimitBodyLength(false));
                        }
                    }
                }.execute(((IssueInfo)item).getCommentsUrl());
            }
        }
    }

    private class DisplayAdapter extends BaseAdapter {

        private List<? extends Info> issueList;
        private LayoutInflater inflater;
        private boolean limitBodyLength = true;

        public DisplayAdapter(List<? extends Info> issueList) {
            this.issueList = issueList;
            inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return issueList.size();
        }

        @Override
        public Object getItem(int position) {
            return issueList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public DisplayAdapter setLimitBodyLength(boolean limitBodyLength) {
            this.limitBodyLength = limitBodyLength;
            return this;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.issue_item, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.body = (TextView) convertView.findViewById(R.id.body);
                if (limitBodyLength) {
                    holder.body.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(140)});
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.title.setText(issueList.get(position).getTitle());
            holder.body.setText(issueList.get(position).getDescription());

            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView body;
        }
    }
}

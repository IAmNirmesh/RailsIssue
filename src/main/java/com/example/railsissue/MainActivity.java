package com.example.railsissue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.railsissue.fragment.IssueFragment;

public class MainActivity extends AppCompatActivity {

    private IssueFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().setCurrentActivity(this);
        setContentView(R.layout.activity_main);

        fragment = new IssueFragment();
        getFragmentManager().beginTransaction().add(R.id.containor, fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setCurrentActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            if (fragment != null) {
                fragment.refresh();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

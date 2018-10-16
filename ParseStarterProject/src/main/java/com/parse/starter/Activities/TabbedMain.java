package com.parse.starter.Activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.models.YourLocation;

public class TabbedMain extends TabActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_main);
        TabHost host = (TabHost) findViewById(16908306);
        host.setup();
        TabSpec spec = host.newTabSpec("Tab One");
        Intent i = new Intent(this, UserList.class);
        spec.setContent(R.id.tab1);
        spec.setContent(i);
        spec.setIndicator("Chat");
        host.addTab(spec);
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        i = new Intent(getApplicationContext(), YourLocation.class);
        spec.setIndicator("Make Request");
        spec.setContent(i);
        host.addTab(spec);
        spec = host.newTabSpec("Tab Three");
        i = new Intent(getApplicationContext(), vw2.class);
        spec.setContent(R.id.tab3);
        spec.setContent(i);
        spec.setIndicator("View REquests");
        host.addTab(spec);
        spec = host.newTabSpec("Tab Four");
        i = new Intent(getApplicationContext(), MainActivity.class);
        spec.setContent(R.id.tab3);
        if (ParseUser.getCurrentUser().getUsername() != null) {
            i.putExtra("User", ParseUser.getCurrentUser().getUsername());
            spec.setContent(i);
        } else {
            spec.setContent(i);
        }
        spec.setIndicator("Main");
        host.addTab(spec);
    }
}

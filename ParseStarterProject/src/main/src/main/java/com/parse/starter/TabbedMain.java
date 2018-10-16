package com.parse.starter;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TabHost;

import com.parse.ParseUser;

public class TabbedMain extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_main);
// Initialize a TabSpec for each tab and add it to the TabHost
        TabHost host = (TabHost)findViewById(android.R.id.tabhost);

        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        Intent i= new Intent(this,UserList.class);
        spec.setContent(R.id.tab1);
        spec.setContent(i);
        spec.setIndicator("Chat");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
       i=new Intent(getApplicationContext(),YourLocation.class);
        spec.setIndicator("Make Request");
        spec.setContent(i);
        host.addTab(spec);
        //Tab 3
        spec = host.newTabSpec("Tab Three");
       i=new Intent(getApplicationContext(),vw2.class);
        spec.setContent(R.id.tab3);
        spec.setContent(i);
        spec.setIndicator("View REquests");
        host.addTab(spec);
        spec = host.newTabSpec("Tab Four");
        i=new Intent(getApplicationContext(),MainActivity.class);
        spec.setContent(R.id.tab3);
       if(ParseUser.getCurrentUser().getUsername()!=null){
        i.putExtra("User", ParseUser.getCurrentUser().getUsername());
        spec.setContent(i);}
        else{spec.setContent(i);};
        spec.setIndicator("Main");
        host.addTab(spec);


    }}
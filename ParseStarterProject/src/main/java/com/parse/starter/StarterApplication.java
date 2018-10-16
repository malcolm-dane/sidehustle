package com.parse.starter;

import android.app.Application;
import com.parse.Parse;
import com.parse.Parse.Configuration.Builder;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.starter.models.Bids;
import com.parse.starter.models.Message;
import com.parse.starter.models.aRequest;

public class StarterApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(Bids.class);
        ParseObject.registerSubclass(aRequest.class);
        Parse.initialize(new Builder(getApplicationContext()).applicationId("traintrackapp").clientKey("bcdeafg").server("https://traintrackapp.herokuapp.com/parse/").build());
        ParseACL.setDefaultACL(new ParseACL(), true);
    }
}

package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.starter.models.Bids;
import com.parse.starter.models.Message;
import com.parse.starter.models.aRequest;

public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);
    ParseObject.registerSubclass(Message.class);
    ParseObject.registerSubclass(Bids.class);
    ParseObject.registerSubclass(aRequest.class);
    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("traintrackapp")
            .clientKey("bcdeafg")
            .server("https://traintrackapp.herokuapp.com/parse/")
            .build()
    );



    //ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.starter.mUtil.PHelper;
import com.parse.starter.models.aRequest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.katso.livebutton.LiveButton;


public class MainActivity extends AppCompatActivity {
    PHelper mP;
    Switch riderOrDriverSwitch;
    EditText eText;
    EditText mEmail;
    EditText username;
    EditText password;
    LiveButton mRider;
    LiveButton mDriver;
    Button login;
    Button mChatView;
    String riderOrDriver;
    String mmEmail;
    String editString;
    String aUser;
    ArrayList<String> aList;
    boolean itsChecked=false;
    ProgressBar mprogress;
    private SharedPreferences sp;
    private SharedPreferences.Editor editsp;
 protected String theUser;


    public  void rider(){
        Intent i = new Intent(getApplicationContext(), YourLocation.class);
        startActivity(i);
    }
    public void driver()
    {
        Intent i = new Intent(getApplicationContext(), vw2.class);
        startActivity(i);  //ParseUser.getCurrentUser().put("driveemail", mmEmail);
    }


public void clicked(View view){
    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(i,0);

}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
final ArrayList uRequests=new ArrayList();
     //   final ParseFile a=null;
        if (resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();
                uRequests.clear();
                final ParseFile file = new ParseFile("image.png", byteArray);
  file.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
         if(e==null){
          putPref("newestURL",file.getUrl(),getApplicationContext());}
      }
  });



                //uRequests.add(file);

                ParseObject newRequest=new ParseObject("userpro");

                ParseACL acl=new ParseACL();
                acl.setPublicReadAccess(true);
                acl.setPublicWriteAccess(true);
                newRequest.setACL(acl);
                newRequest.put(ParseUser.getCurrentUser().getUsername(),file);
                newRequest.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Log.i("saved", "saved");
                           startActivity(new Intent(getApplicationContext(),TabbedMain.class));
                        }

                        else{Log.i("Delete", e.getMessage()); }}});}catch(Exception r){r.getMessage();}}





            }





    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        Log.i(key,value);
        editor.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        riderOrDriver=null;
        aList=new ArrayList<String>();
        boolean noFocus=false;
        setContentView(R.layout.activity_main);

        mprogress=(ProgressBar)findViewById(R.id.progressBar2); mprogress.setVisibility(View.INVISIBLE);mRider=(LiveButton)findViewById(R.id.rider);
        mRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rider();
            }
        });
        mDriver=(LiveButton)findViewById(R.id.driver);
        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driver();
            }
        });
        mRider.setVisibility(View.INVISIBLE);
        mDriver.setVisibility(View.INVISIBLE);
        password=(EditText)findViewById(R.id.password);
        username=(EditText)findViewById(R.id.username);
        login=(Button)findViewById(R.id.login);
mChatView=(Button)findViewById(R.id.button3);
        sp = getSharedPreferences("url",Context.MODE_APPEND);

        // ParseUser.getCurrentUser().put("riderOrDriver", "rider");
        //  riderOrDriverSwitch = (Switch) findViewById(R.id.riderOrDriverSwitch);
        //riderOrDriverSwitch.setChecked(false);

        // mEmail=(EditText)findViewById(R.id.email);
        // mEmail.setText(R.string.enter_email);
        //eText=(EditText)findViewById(R.id.editText);
        // mEmail.setOnClickListener(new View.OnClickListener() {
//
        ///   @Override
        //  public void onClick(View v) {
        //  mEmail.setText("");
        //  mEmail.getText();

        ///}});
        //  eText.setText(R.string.enter_text);
        // eText.setOnClickListener(new View.OnClickListener() {

        // @Override
        // public void onClick(View v) {
        //     eText.setText("");
        //     eText.getText();

        //  }});

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        //getSupportActionBar().hide();

 //else {

        //if ( riderOrDriver!=null) {

        //  getStarted(view);

        //}

        //}
if(getIntent().getStringExtra("User")!=null){
    password.setVisibility(View.INVISIBLE);
    username.setVisibility(View.INVISIBLE);
    login.setText("LogOut");
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         boolean isLoggedin= ParseUser.getCurrentUser().isAuthenticated();
            isLoggedin=false;
            theUser=null;
        }
    });
}



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void signupOrLogin(View view)
    {
       mprogress.setVisibility(View.VISIBLE);
         ParseUser.logInInBackground(String.valueOf(username.getText()), String.valueOf(password.getText()),
                 new LogInCallback()
                 {
            public void done(ParseUser user, ParseException e)
            {
                if (user != null)
                {
                     aUser=String.valueOf(username.getText());
                   ParseUser.getCurrentUser().setUsername(aUser);
                    ParseObject requests= new ParseObject("Requests");
                    requests.put("username",aUser);
                    requests.saveInBackground();
                    aList.clear();
                    aList.add(aUser);
                    mprogress.setVisibility(View.INVISIBLE);
                    Log.i("LoginEvent", "Logged In");
                    mDriver.setVisibility(View.VISIBLE);
                    mRider.setVisibility(View.VISIBLE);
                    login.setVisibility(View.INVISIBLE);
                    username.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    Intent I=new Intent(getApplicationContext(),TabbedMain.class);
               startActivity(I);
                }
                else
                {
                    mprogress.setVisibility(View.VISIBLE);
                    ParseUser newUser = new ParseUser();


                    newUser.setUsername(String.valueOf(username.getText()));
                    newUser.setPassword(password.getText().toString());

                    newUser.signUpInBackground(new SignUpCallback()
                    {
                        public void done(ParseException e)
                        {
                            if (e == null)
                            {
                                aUser=String.valueOf(username.getText());
                                ParseObject requests= new ParseObject("Requests");
                                requests.put("username",aUser);
                                requests.saveInBackground(new SaveCallback()
                                {
                                    @Override
                                    public void done(ParseException e)
                                    {
                                        Log.i("saved,","username");
                                    }
                                });
                                ParseUser.getCurrentUser().setUsername(aUser);
                                ParseUser.getCurrentUser().put("username",aUser);;
                                mprogress.setVisibility(View.INVISIBLE);
                                Log.i("AppInfo", "Signed up");
                                mDriver.setVisibility(View.VISIBLE);
                                mRider.setVisibility(View.VISIBLE);
                                login.setVisibility(View.INVISIBLE);
                                username.setVisibility(View.INVISIBLE);
                                password.setVisibility(View.INVISIBLE);
                                Intent I=new Intent(getApplicationContext(),TabbedMain.class);
                                startActivity(I);
                            }
                            else
                            {

                                Log.i("a","b");

                            }
                        }
                    });


                }
            }
         });


    }
public void ChatView(View view)
{
    Intent i= new Intent(MainActivity.this,UserList.class);
    startActivity(i);
}
@Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("username",aUser);
    }
    @Override
    protected	void	onRestoreInstanceState(Bundle	savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if( savedInstanceState.getString("username")!= null){
            password.setVisibility(View.INVISIBLE);
            username.setVisibility(View.INVISIBLE);

        }

    }

    }
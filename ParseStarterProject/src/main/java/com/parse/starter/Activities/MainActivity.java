package com.parse.starter.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.plus.PlusShare;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.starter.MVC.helperMethods;
import com.parse.starter.mUtil.PHelper;
import com.parse.starter.models.YourLocation;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import ru.katso.livebutton.LiveButton;
import com.parse.starter.R;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> aList;
    String aUser;
    EditText eText;
    String editString;
    private Editor editsp;
    double gettheDistance;
    boolean itsChecked = false;
    Button login;
    private AdView mAdView;
    Button mChatView;
    LiveButton mDriver;
    EditText mEmail;
    PHelper mP;
    LiveButton mRider;
    String mmEmail;
    ProgressBar mprogress;
    boolean nearphilly;
    EditText password;
    String riderOrDriver;
    Switch riderOrDriverSwitch;
    private SharedPreferences sp;
    protected String theUser;
    EditText username;

    public void rider() {
        startActivity(new Intent(getApplicationContext(), YourLocation.class));
    }

    public void driver() {
        startActivity(new Intent(getApplicationContext(), vw2.class));
    }

    public void clicked(View view) {
        startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList uRequests = new ArrayList();
        if (resultCode == -1 && data != null) {
            try {
                Bitmap bitmap = Media.getBitmap(getContentResolver(), data.getData());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                uRequests.clear();
                final ParseFile file = new ParseFile("image.png", byteArray);
                file.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            MainActivity.putPref("newestURL", file.getUrl(), MainActivity.this.getApplicationContext());
                        }
                    }
                });
                ParseObject newRequest = new ParseObject("userpro");
                ParseACL acl = new ParseACL();
                acl.setPublicReadAccess(true);
                acl.setPublicWriteAccess(true);
                newRequest.setACL(acl);
                newRequest.put(ParseUser.getCurrentUser().getUsername(), file);
                newRequest.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("saved", "saved");
                            MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), TabbedMain.class));
                            return;
                        }
                        Log.i("Delete", e.getMessage());
                    }
                });
            } catch (Exception r) {
                r.getMessage();
            }
        }
    }

    public static void putPref(String key, String value, Context context) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        Log.i(key, value);
        editor.commit();
    }

    public void pCheck() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.riderOrDriver = null;
        this.aList = new ArrayList();
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), " ca-app-pub-3993853389831349~2393643917");
        this.mAdView = (AdView) findViewById(R.id.adView);
        this.mAdView.loadAd(new Builder().build());
        ParseUser.enableAutomaticUser();
        this.mprogress = (ProgressBar) findViewById(R.id.progressBar2);
        this.mprogress.setVisibility(4);
        this.mRider = (LiveButton) findViewById(R.id.rider);
        this.mRider.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.rider();
            }
        });
        this.mDriver = (LiveButton) findViewById(R.id.driver);
        this.mDriver.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.driver();
            }
        });
        this.mRider.setVisibility(4);
        this.mDriver.setVisibility(4);
        this.password = (EditText) findViewById(R.id.password);
        this.username = (EditText) findViewById(R.id.username);
        this.login = (Button) findViewById(R.id.login);
        this.mChatView = (Button) findViewById(R.id.button3);
        this.sp = getSharedPreferences(PlusShare.KEY_CALL_TO_ACTION_URL, 32768);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseUser.enableAutomaticUser();
        if (getIntent().getStringExtra("User") != null) {
            this.password.setVisibility(4);
            this.username.setVisibility(4);
            this.login.setText("LogOut");
            this.login.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    boolean isLoggedin = ParseUser.getCurrentUser().isAuthenticated();
                    ParseUser.logOut();
                }
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getIt() {
        this.gettheDistance = helperMethods.nearPhilly(getApplicationContext()).doubleValue();
    }

    public void signupOrLogin(View view) {
        getIt();
       // if (this.gettheDistance < 75.0d) {
            ParseUser.logInInBackground(String.valueOf(this.username.getText()), String.valueOf(this.password.getText()), new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        MainActivity.this.aUser = String.valueOf(MainActivity.this.username.getText());
                        ParseUser.getCurrentUser().setUsername(MainActivity.this.aUser);
                        ParseObject requests = new ParseObject("Requests");
                        requests.put("username", MainActivity.this.aUser);
                        requests.saveInBackground();
                        MainActivity.this.aList.clear();
                        MainActivity.this.aList.add(MainActivity.this.aUser);
                        MainActivity.this.mprogress.setVisibility(4);
                        Log.i("LoginEvent", "Logged In");
                        MainActivity.this.mDriver.setVisibility(0);
                        MainActivity.this.mRider.setVisibility(0);
                        MainActivity.this.login.setVisibility(4);
                        MainActivity.this.username.setVisibility(4);
                        MainActivity.this.password.setVisibility(4);
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), TabbedMain.class));
                        return;
                    }
                    MainActivity.this.mprogress.setVisibility(0);
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(String.valueOf(MainActivity.this.username.getText()));
                    newUser.setPassword(MainActivity.this.password.getText().toString());
                    newUser.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                MainActivity.this.aUser = String.valueOf(MainActivity.this.username.getText());
                                ParseObject requests = new ParseObject("Requests");
                                requests.put("username", MainActivity.this.aUser);
                                requests.saveInBackground(new SaveCallback() {
                                    public void done(ParseException e) {
                                        Log.i("saved,", "username");
                                    }
                                });
                                ParseUser.getCurrentUser().setUsername(MainActivity.this.aUser);
                                ParseUser.getCurrentUser().put("username", MainActivity.this.aUser);
                                MainActivity.this.mprogress.setVisibility(4);
                                Log.i("AppInfo", "Signed up");
                                MainActivity.this.mDriver.setVisibility(0);
                                MainActivity.this.mRider.setVisibility(0);
                                MainActivity.this.login.setVisibility(4);
                                MainActivity.this.username.setVisibility(4);
                                MainActivity.this.password.setVisibility(4);
                                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), TabbedMain.class));
                                return;
                            }
                            Log.i("a", "b");
                        }
                    });
                }
            });
       // }
    }

    public void ChatView(View view) {
        startActivity(new Intent(this, UserList.class));
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("username", this.aUser);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString("username") == null) {
        }
    }
}

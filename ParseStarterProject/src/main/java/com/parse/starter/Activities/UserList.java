package com.parse.starter.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.parse.ParseUser;
import com.parse.starter.CustomList;
import com.parse.starter.R;
import com.parse.starter.adapters.UserListCustom;
import com.parse.starter.mUtil.testing;
import com.parse.starter.newChat.newChatActivity;
import java.util.ArrayList;
import ru.katso.livebutton.LiveButton;

public class UserList extends AppCompatActivity {
    Intent J;
    String[] aa;
    UserListCustom arrayAdapter;
    Bitmap bitmap;
    LiveButton button3;
    LiveButton button4;
    CustomList customList;
    EditText gotMail;
    Integer[] imageId = new Integer[]{Integer.valueOf(R.drawable.mail)};
    ListView listView;
    private AdView mAdView;
    String me = ParseUser.getCurrentUser().getUsername();
    String other;
    private Button searchButton;
    ArrayList<String> usernames;
    View view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_user_list);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6405485161020407~7683183944");
        this.mAdView = (AdView) findViewById(R.id.adView);
        this.mAdView.loadAd(new Builder().build());
        this.gotMail = (EditText) findViewById(R.id.gotMail);
        this.button4 = (LiveButton) findViewById(R.id.button4);
        this.button3 = (LiveButton) findViewById(R.id.Refresh);
        this.J = getIntent();
        this.usernames = new ArrayList();
        this.searchButton = (Button) findViewById(R.id.searchbutton);
        this.listView = (ListView) findViewById(R.id.userListLV);
        this.arrayAdapter = new UserListCustom(this, this.usernames, this.imageId);
        this.listView.setAdapter(this.arrayAdapter);
        generateList();
        this.button4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UserList.this.J = null;
                testing.grabAllUsers(UserList.this.usernames, UserList.this.me, UserList.this.arrayAdapter);
                UserList.this.arrayAdapter.notifyDataSetChanged();
            }
        });
        this.searchButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UserList.this.J = null;
                testing.searchUsers(UserList.this.usernames, UserList.this.gotMail.getText().toString(), UserList.this.arrayAdapter);
                UserList.this.arrayAdapter.notifyDataSetChanged();
            }
        });
        this.button3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UserList.this.gotMail.setText("Click on the User below to see your messages");
                testing.grabUserNames(UserList.this.usernames, UserList.this.me, UserList.this.arrayAdapter);
                UserList.this.arrayAdapter.notifyDataSetChanged();
            }
        });
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(UserList.this, newChatActivity.class);
                i.putExtra("username", (String) UserList.this.usernames.get(position));
                UserList.this.startActivity(i);
            }
        });
    }

    void generateList() {
        testing.grabAllUsers(this.usernames, this.me, this.arrayAdapter);
        this.arrayAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.starter.adapters.UserListCustom;
import com.parse.starter.mUtil.testing;
import com.parse.starter.newChat.newChatActivity;

import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

public class UserList extends AppCompatActivity {

    ArrayList<String> usernames;
    UserListCustom arrayAdapter;
    ListView listView;
    Bitmap bitmap;
    Intent J;
    View view;
    //istView lv;
    Integer[] imageId = {
            R.drawable.mail};
LiveButton button4;
    LiveButton button3;
    String other;
    String me=ParseUser.getCurrentUser().getUsername();
    TextView gotMail; CustomList customList; String []aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        gotMail=(TextView)findViewById(R.id.gotMail) ;
   button4=(LiveButton)findViewById(R.id.button4);
        button3=(LiveButton)findViewById(R.id.Refresh);
     J = getIntent();
        usernames = new ArrayList<String>();

   listView=(ListView)findViewById(R.id.userListLV);
arrayAdapter=new UserListCustom(this,usernames,imageId);

        listView.setAdapter(arrayAdapter);  generateList();// arrayAdapter.notifyDataSetChanged();
        gotMail.setText("Chat Landing");
     //   aa = usernames.toArray(new String[usernames.size()]);

       // arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames);
       // listView.setAdapter(arrayAdapter);
    //    testing.grabAllUsers(usernames,ParseUser.getCurrentUser().getUsername());

       // customList.notifyDataSetChanged();
       button4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            J=null;
//usernames.clear();//arrayAdapter.notifyDataSetChanged();
//
               testing.grabAllUsers(usernames,me,arrayAdapter);
           //    aa = {usernames.toArray(new String[usernames.size()])};
           arrayAdapter.notifyDataSetChanged();}});


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotMail.setText("Click on the User below to see your messages");
              //  usernames.clear();
                testing.grabUserNames(usernames,me,arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(UserList.this,newChatActivity.class);
i.putExtra("username",usernames.get(position));
                startActivity(i);

            }
        });}


       // ParseQuery<ParseUser> query = ParseUser.getQuery();

        //Log.i("asdf", ParseUser.getCurrentUser().getUsername());

        //query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        //query.findInBackground(new FindCallback<ParseUser>() {
          //  public void done(List<ParseUser> objects, ParseException e) {
            //    if (e == null) {

              //      if (objects.size() > 0) {

                        //usernames.clear();

                       // for (ParseUser user : objects) {
                //           String a=J.getStringExtra("username");
                  ///          usernames.add(J.getStringExtra("username"));

                       // }

                     //   arrayAdapter.notifyDataSetChanged();

                    //}


                //} //else {
                    // Something went wrong.
                //}
            //}
        //});

        //checkForImages();


    //}



    void generateList() {
        testing.grabAllUsers(usernames,me,arrayAdapter);
      arrayAdapter.notifyDataSetChanged();

  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

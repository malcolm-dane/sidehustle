package com.parse.starter.newChat;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.mUtil.testing;
import com.parse.starter.models.Message;

import java.util.ArrayList;

public class newChatActivity extends AppCompatActivity {
    static final String TAG = newChatActivity.class.getSimpleName();

    EditText etMessage;
    FloatingActionButton btSend;
    ListView lvChat;
    ArrayList<Message> mMessages;
    ArrayList<String> userImage;
    ArrayList<String> userImage2;
    ChatListAdapter mAdapter;
    ArrayList<Message> aMessage;
    ArrayList<Message> aMessagetwo;
    // Keep track of initial load to scroll to the bottom of the ListView
    boolean mFirstLoad;
    public String other;

    private String me=ParseUser.getCurrentUser().getUsername();
    Intent I;
    // Create a handler which can run code periodically
    static final int POLL_INTERVAL = 15000; // milliseconds
    Handler mHandler = new Handler();  // android.os.Handler
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            testing.check(other,mMessages,me);

            Log.i("check","check");
          mAdapter.notifyDataSetChanged();
            mMessages.clear();
            mHandler.postDelayed(this, POLL_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_chat);
       Intent I=getIntent();
        other=I.getStringExtra("username");
        userImage=new ArrayList<String>();
        userImage2=new ArrayList<String>();
        final String sender = ParseUser.getCurrentUser().getUsername();
        final String RUserId=I.getStringExtra("username");
        mAdapter = new ChatListAdapter(newChatActivity.this,sender,RUserId, mMessages,userImage,userImage2);
        new RemoteDataTask().execute();
        setupMessagePosting();
        mRefreshMessagesRunnable.run();}
       // mHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);}
       // testing.acheck(I.getStringExtra("username"),userImage,userImage2,me);
        // User login
       // if (ParseUser.getCurrentUser() != null)
       // { // start with existing user
           // startWithCurrentUser();
       // } else { // If not logged in, login as a new anonymous user
          //  login();
     //   }

  //  }

    // Get thesender from the cached currentUser object
   // void startWithCurrentUser() {
        //setupMessagePosting();
  //  }
    void setupMessagePosting() {
        // Find the text field and button
        I=getIntent();
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (FloatingActionButton) findViewById(R.id.btSend);
        lvChat = (ListView) findViewById(R.id.lvChat);
        mMessages = new ArrayList<Message>();

        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        final String sender = ParseUser.getCurrentUser().getUsername();
        final String RUserId=I.getStringExtra("username");
        //   mAdapter = new ChatListAdapter(newChatActivity.this,sender,RUserId, mMessages,userImage,userImage2);
        //  lvChat.setAdapter(mAdapter);
        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                ParseObject message = ParseObject.create("Message");
                message.put(ParseUser.getCurrentUser().getUsername(),I.getStringExtra("username"));
                message.put(Message.USER_ID_KEY,sender);
                message.put(Message.RECEIVER_ID_KEY, I.getStringExtra("username"));
                message.put(Message.BODY_KEY, data);
                ParseACL parseACL = new ParseACL();
                parseACL.setPublicWriteAccess(true);//yeah this doesn't look safe
                parseACL.setPublicReadAccess(true);
                message.setACL(parseACL);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(newChatActivity.this, "Successfully created message on Parse",
                                Toast.LENGTH_SHORT).show();
                        testing.check(other,mMessages,me);// refreshMessages();
                    }
                });
                etMessage.setText(null);
            }
        });
    }
    // Create an anonymous user using ParseAnonymousUtils and set sUserId


    // Setup button event handler which posts the entered message to Parse


    // Query messages from Parse so we can load them into the chat adapter

public void aQUery(){
    ParseQuery aQuery= ParseQuery.getQuery("userpro");

}
    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        void refreshMessages() {
            // Construct query to execute


            testing.acheck(getApplicationContext(),other,userImage,userImage2,me);
            //  mAdapter.notifyDataSetChanged();
        }
        @Override
        protected Void doInBackground(Void... params) {


           I=getIntent();
            other=I.getStringExtra("username");
           userImage.clear();
            testing.acheck(getApplicationContext(),other,userImage,userImage2,me);

             mAdapter.notifyDataSetChanged();
            return null;}

        @Override
        protected void onPostExecute(Void result) {
            lvChat = (ListView) findViewById(R.id.lvChat);


            final String sender = ParseUser.getCurrentUser().getUsername();
            final String RUserId=I.getStringExtra("username");
            mAdapter = new ChatListAdapter(newChatActivity.this,sender,RUserId, mMessages,userImage,userImage2);
            lvChat.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


    }}}

      /*
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
      //  query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
for(Message message:messages){
    other=I.getStringExtra("username");
    if(me.equals(message.getreceiverId())&&(message.getUserId().equals(other)))
    {
                    mMessages.add(message);}
    else if(other.equals(message.getString("receiver"))&&(me.equals(message.getUserId()))){
        mMessages.add(message);
    }
                    mAdapter.notifyDataSetChanged();  } // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
               else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        };
    });
}}
*/             /*   if  (message.getreceiverId().equals(me)){

                  if (message.getUserId().equals(I.getStringExtra("username")))

                  {

                      mMessages.add(message);
                  }  mAdapter.notifyDataSetChanged();}

                  else if(me.equals(message.getUserId()))
                  {
                      if (I.getStringExtra("username").equals(message.getreceiverId())){
                          mMessages.add(message);
                      }
                  }mAdapter.notifyDataSetChanged() ;



                  }}

                                  // if((message.getreceiverId().equals(I.getStringExtra("username"))))
                 //  {

                  //     if (message.getUserId().equals(me))  {
                   //       mMessages.add(message);
                   //    mAdapter.notifyDataSetChanged();}
                //   }}
             //   if (mFirstLoad) {
                  //  lvChat.setSelection(mAdapter.getCount() - 1);
                  //  mFirstLoad = false;
              //  }   }
             // else if(message.getreceiverId().equals(me)&&message.getUserId().equals(I.getStringExtra("username"))) {
                // mMessages.add(message);mAdapter.notifyDataSetChanged();}
           //     });

            // update adapter
            // Scroll to the bottom of the list on initial load

       // /}
       // }
// update adapter
                // Scroll to the bottom of the list on initial load
*/

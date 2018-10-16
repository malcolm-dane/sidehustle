package com.parse.starter.newChat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.mUtil.testing;
import com.parse.starter.models.Message;
import java.util.ArrayList;

public class newChatActivity extends AppCompatActivity {
    static final int POLL_INTERVAL = 15000;
    static final String TAG = newChatActivity.class.getSimpleName();
    Intent I;
    ArrayList<Message> aMessage;
    ArrayList<Message> aMessagetwo;
    FloatingActionButton btSend;
    EditText etMessage;
    ListView lvChat;
    ChatListAdapter mAdapter;
    boolean mFirstLoad;
    Handler mHandler = new Handler();
    ArrayList<Message> mMessages;
    Runnable mRefreshMessagesRunnable = new Runnable() {
        public void run() {
            testing.check(newChatActivity.this.other, newChatActivity.this.mMessages, newChatActivity.this.me);
            Log.i("check", "check");
            newChatActivity.this.mAdapter.notifyDataSetChanged();
            newChatActivity.this.mMessages.clear();
            newChatActivity.this.mHandler.postDelayed(this, 15000);
        }
    };
    private String me = ParseUser.getCurrentUser().getUsername();
    public String other;
    ArrayList<String> userImage;
    ArrayList<String> userImage2;

    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        void refreshMessages() {
            testing.acheck(newChatActivity.this.other, newChatActivity.this.userImage, newChatActivity.this.userImage2, newChatActivity.this.me);
        }

        protected Void doInBackground(Void... params) {
            newChatActivity.this.I = newChatActivity.this.getIntent();
            newChatActivity.this.other = newChatActivity.this.I.getStringExtra("username");
            newChatActivity.this.userImage.clear();
            testing.acheck(newChatActivity.this.other, newChatActivity.this.userImage, newChatActivity.this.userImage2, newChatActivity.this.me);
            newChatActivity.this.mAdapter.notifyDataSetChanged();
            return null;
        }

        protected void onPostExecute(Void result) {
            newChatActivity.this.lvChat = (ListView) newChatActivity.this.findViewById(R.id.lvChat);
            String sender = ParseUser.getCurrentUser().getUsername();
            String RUserId = newChatActivity.this.I.getStringExtra("username");
            newChatActivity.this.mAdapter = new ChatListAdapter(newChatActivity.this, sender, RUserId, newChatActivity.this.mMessages, newChatActivity.this.userImage, newChatActivity.this.userImage2);
            newChatActivity.this.lvChat.setAdapter(newChatActivity.this.mAdapter);
            newChatActivity.this.mAdapter.notifyDataSetChanged();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_chat);
        Intent I = getIntent();
        this.other = I.getStringExtra("username");
        this.userImage = new ArrayList();
        this.userImage2 = new ArrayList();
        this.mAdapter = new ChatListAdapter(this, ParseUser.getCurrentUser().getUsername(), I.getStringExtra("username"), this.mMessages, this.userImage, this.userImage2);
        new RemoteDataTask().execute();
        setupMessagePosting();
        this.mRefreshMessagesRunnable.run();
    }

    void setupMessagePosting() {
        this.I = getIntent();
        this.etMessage = (EditText) findViewById(R.id.etMessage);
        this.btSend = (FloatingActionButton) findViewById(R.id.btSend);
        this.lvChat = (ListView) findViewById(R.id.lvChat);
        this.mMessages = new ArrayList();
        this.lvChat.setTranscriptMode(1);
        this.mFirstLoad = true;
        final String sender = ParseUser.getCurrentUser().getUsername();
        String RUserId = this.I.getStringExtra("username");
        this.btSend.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String data = newChatActivity.this.etMessage.getText().toString();
                ParseObject message = ParseObject.create("Message");
                message.put(ParseUser.getCurrentUser().getUsername(), newChatActivity.this.I.getStringExtra("username"));
                message.put("sender", sender);
                message.put("receiver", newChatActivity.this.I.getStringExtra("username"));
                message.put(Message.BODY_KEY, data);
                ParseACL parseACL = new ParseACL();
                parseACL.setPublicWriteAccess(true);
                parseACL.setPublicReadAccess(true);
                message.setACL(parseACL);
                message.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        Toast.makeText(newChatActivity.this, "Successfully created message on Parse", 0).show();
                        testing.check(newChatActivity.this.other, newChatActivity.this.mMessages, newChatActivity.this.me);
                    }
                });
                newChatActivity.this.etMessage.setText(null);
            }
        });
    }

    public void aQUery() {
        ParseQuery aQuery = ParseQuery.getQuery("userpro");
    }
}

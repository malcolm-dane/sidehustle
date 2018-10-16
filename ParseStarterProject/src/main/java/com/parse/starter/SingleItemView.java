package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.adapters.singleItemView.singleItemView;
import com.parse.starter.models.Bids;
import com.parse.starter.models.Message;
import com.parse.starter.models.SPModel;
import com.parse.starter.models.aRequest;
import java.util.ArrayList;
import java.util.List;

public class SingleItemView extends AppCompatActivity {
    TextView Amessage;
    Intent I;
    private String aDriver;
    private ListView aListView;
    private String aName;
    private String aobjID;
    private singleItemView cAdapter;
    private Button floata;
    private String message;
    TextView name;
    private String phone;
    TextView pnumber;
    private ArrayList<SPModel> spList;
    TextView type;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemlayout);
        this.I = getIntent();
        this.name = (TextView) findViewById(R.id.name);
        this.type = (TextView) findViewById(R.id.type);
        this.type.setText("Type of request:" + this.I.getStringExtra("type"));
        this.spList = new ArrayList();
        this.aListView = (ListView) findViewById(R.id.bids);
        this.cAdapter = new singleItemView(this, this.spList);
        this.aListView.setAdapter(this.cAdapter);
        this.aobjID = this.I.getStringExtra("objID");
        Log.i("ID", this.aobjID);
        this.name.setText("UserName" + ParseUser.getCurrentUser().getUsername());
        this.Amessage = (TextView) findViewById(R.id.driversname);
        bidQuery();
        this.aListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SingleItemView.this.aDriver = SingleItemView.this.spList.get(position).getName();
                Log.i("adriver", SingleItemView.this.aDriver);
                SingleItemView.this.AcceptBit();
                SingleItemView.this.Amessage.setText("Selected" + SingleItemView.this.aDriver + "offer");
            }
        });
    }

    public void AcceptBit() {
        ParseQuery<ParseObject> query = new ParseQuery("aRequest");
        query.whereEqualTo(aRequest.USER_ID_KEY, ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        if (object.getObjectId().equals(SingleItemView.this.I.getStringExtra("objID"))) {
                            object.put("RequestOpen", "no");
                            object.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        ParseObject message = ParseObject.create("Message");
                                        message.put(ParseUser.getCurrentUser().getUsername(), SingleItemView.this.I.getStringExtra("username"));
                                        message.put("sender", ParseUser.getCurrentUser().getUsername());
                                        message.put("receiver", SingleItemView.this.aDriver);
                                        message.put(Message.BODY_KEY, "Hi I've Accepted your bid");
                                        ParseACL parseACL = new ParseACL();
                                        parseACL.setPublicWriteAccess(true);
                                        parseACL.setPublicReadAccess(true);
                                        message.setACL(parseACL);
                                        message.saveInBackground(new SaveCallback() {
                                            public void done(ParseException e) {
                                                Toast.makeText(SingleItemView.this.getApplicationContext(), "Informed " + SingleItemView.this.aDriver + " that you have accepted their bid", 0).show();
                                            }
                                        });
                                        Log.i("Closed", "Closed");
                                        return;
                                    }
                                    Log.i(e.getMessage(), e.getMessage());
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    public void findIt() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("aRequest");
        query.whereEqualTo("_id", this.I.getStringExtra("objID"));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    String driversUsername = ParseUser.getCurrentUser().getUsername();
                    Log.i("nu ll", "you'regood");
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            if (object.getObjectId().equals(SingleItemView.this.I.getStringExtra("objID"))) {
                            }
                        }
                    }
                }
            }
        });
    }

    public void bidQuery() {
        ParseQuery<ParseObject> query = new ParseQuery("Bids");
        query.whereEqualTo("objID", this.aobjID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                SPModel amodel;
                if (e == null) {
                    for (ParseObject object : objects) {
                        amodel = new SPModel();
                        if (object.get(Bids.Bid_KEY) != null) {
                            amodel.setMessage(object.get(Bids.Bid_KEY).toString());
                        }
                        if (object.get("sender") != null) {
                            amodel.setName(object.get("sender").toString());
                        }
                        if (object.get("type") != null) {
                            amodel.setStatus(object.getString("type"));
                        }
                        SingleItemView.this.spList.add(amodel);
                        SingleItemView.this.cAdapter.notifyDataSetChanged();
                    }
                    return;
                }
                amodel = new SPModel();
                amodel.setMessage("null");
                amodel.setName("null");
                amodel.setStatus("null");
                SingleItemView.this.spList.add(amodel);
                SingleItemView.this.cAdapter.notifyDataSetChanged();
            }
        });
    }
}

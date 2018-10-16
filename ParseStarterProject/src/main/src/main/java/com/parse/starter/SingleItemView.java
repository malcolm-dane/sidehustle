package com.parse.starter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.adapters.ListViewAdapter;
import com.parse.starter.models.Bids;

import static com.parse.ParseQuery.getQuery;

public class SingleItemView extends AppCompatActivity{
    // Declare Variables
    String rank;
    String country;
    String flag;
    String timestamp;
    ProgressDialog mProgressDialog;
    Bitmap bmImg = null;
    Intent i;
    ListView anotherList;
    ArrayList bid;
    ArrayAdapter a;
    ListViewAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemlayout);
        // Execute loadSingleView AsyncTask
        bid=new ArrayList<String>();
i=getIntent();
        a=new ArrayAdapter(this,R.layout.singletext,bid);
        anotherList=(ListView)findViewById(R.id.bids);
        a.notifyDataSetChanged();
        new loadSingleView().execute();
    }

    public class loadSingleView extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog

            mProgressDialog = new ProgressDialog(SingleItemView.this);
            // Set progressdialog title
            mProgressDialog.setTitle("TiP:Be clear in what you need done");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                // Retrieve data from ListViewAdapter on click event

                // Get the result of rank
               if(i.getStringExtra("rank")!=null){
                rank = i.getStringExtra("rank");
                  }
                // Get the result of country
                if(i.getStringExtra("country")!=null){
                country = i.getStringExtra("country");}
                if(i.getStringExtra("timestamp")!=null){
                   Log.i("i","i");
                    timestamp = i.getStringExtra("timestamp");}
                // Get the result of population
                // Get the result of flag
                if( i.getStringExtra("flag")!=null){
                flag = i.getStringExtra("flag");}
                String objid=i.getStringExtra("objid");
                // Download the Image from the result URL given by flag
                URL url = new URL(flag);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the TextViews in singleitemview.xml


bid.add("test");
            anotherList.setAdapter(a);

            i =getIntent();
//            Log.i(objid,objid);

            TextView txtrank = (TextView) findViewById(R.id.rank);
            TextView txtcountry = (TextView) findViewById(R.id.country);
         //   TextView txtpopulation = (TextView) findViewById(R.id.population);
            // Locate the ImageView in singleitemview.xml
            ImageView imgflag = (ImageView) findViewById(R.id.flag);

            // Set results to the TextViews
            txtrank.setText(rank);
            txtcountry.setText(country);


            // Set results to the ImageView
            imgflag.setImageBitmap(bmImg);

            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }

}
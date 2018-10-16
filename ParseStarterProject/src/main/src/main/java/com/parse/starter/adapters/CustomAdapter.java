package com.parse.starter.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends ArrayAdapter {

    private final Activity context;
    private final ArrayList<HashMap> web;
ArrayList <String> D;

    // private final ArrayList<Bitmap> imageId;
    String[] a;
    public  CustomAdapter(Activity context,
                          ArrayList web) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.requestlist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txhint);
D=new ArrayList<>();
        ImageView imageViewer = (ImageView) rowView.findViewById(R.id.btmap);
        ImageDownloader aImage= new ImageDownloader(imageViewer);

        // ImageDownloader aImage= new ImageDownloader(imageViewer);
        // new ImageDownloader(imageViewer).execute(weburl.listIterator().next());
        // for(int i=0;i<weburl.size();i++){
for(int i=0;i<web.size();i++){
  String aKey="images"+Integer.toString(i);
if(web.get(i).containsKey(aKey)){


    Log.i( web.get(i).get(aKey).toString(),  web.get(i).get(aKey).toString());
D.add(web.get(i).get(aKey).toString());

    //Picasso.with(getContext()).load(web.get(i).get(aKey).toString()).into(imageViewer );
//String txtey="Message"+Integer.toString(i);
   // if(web.get(i).containsKey(txtey)){
//txtTitle.setText(web.get(i).get(txtey).toString());}

    } }new ImageDownloader(imageViewer).Execute(D.listIterator().next().toString());return rowView;}}



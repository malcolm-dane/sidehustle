package com.parse.starter.adapters;
//Not used
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.starter.R;

import com.parse.starter.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends ArrayAdapter {
    ArrayList<String> D;
    String[] a;
    private final Activity context;
    private final ArrayList<HashMap> web;

    public CustomAdapter(Activity context, ArrayList web) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(R.layout.requestlist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txhint);
        this.D = new ArrayList();
        ImageView imageViewer = (ImageView) rowView.findViewById(R.id.btmap);
        ImageDownloader aImage = new ImageDownloader(imageViewer);
        for (int i = 0; i < this.web.size(); i++) {
            String aKey = "images" + Integer.toString(i);
            if (this.web.get(i).containsKey(aKey)) {
                Log.i(this.web.get(i).get(aKey).toString(), this.web.get(i).get(aKey).toString());
                this.D.add(this.web.get(i).get(aKey).toString());
            }
        }
        new ImageDownloader(imageViewer).Execute(this.D.listIterator().next().toString());
        return rowView;
    }
}

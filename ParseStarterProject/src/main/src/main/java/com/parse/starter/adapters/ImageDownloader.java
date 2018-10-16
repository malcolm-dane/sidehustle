package com.parse.starter.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by temp on 3/30/2017.
 */
class ImageDownloader {
    ImageView bmImage;
    Handler handler;
    public ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String urls) {
        String url =urls;
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
        }
        return mIcon;
    }


    protected void Execute(final String a) {

        handler.post(new Runnable() {
            @Override
            public void run() {
              Bitmap inbac=  doInBackground(a); bmImage.setImageBitmap(inbac);  // updateLocation(location);
            }
        });
        }
}
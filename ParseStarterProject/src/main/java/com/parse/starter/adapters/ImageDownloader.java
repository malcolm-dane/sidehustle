package com.parse.starter.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import java.net.URL;
import com.parse.starter.R;

class ImageDownloader {
    ImageView bmImage;
    Handler handler;

    public ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String urls) {
        Bitmap mIcon = null;
        try {
            mIcon = BitmapFactory.decodeStream(new URL(urls).openStream());
        } catch (Exception e) {
        }
        return mIcon;
    }

    protected void Execute(final String a) {
        this.handler.post(new Runnable() {
            public void run() {
                ImageDownloader.this.bmImage.setImageBitmap(ImageDownloader.this.doInBackground(a));
            }
        });
    }
}

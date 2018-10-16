package com.parse.starter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FullscreenActivity extends AppCompatActivity {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private View mContentView;
    private View mControlsView;
    private final OnTouchListener mDelayHideTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            FullscreenActivity.this.delayedHide(3000);
            return false;
        }
    };
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint({"InlinedApi"})
        public void run() {
            FullscreenActivity.this.mContentView.setSystemUiVisibility(4871);
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        public void run() {
            FullscreenActivity.this.hide();
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        public void run() {
            ActionBar actionBar = FullscreenActivity.this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            FullscreenActivity.this.mControlsView.setVisibility(0);
        }
    };
    private boolean mVisible;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void toggle() {
        if (this.mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        this.mControlsView.setVisibility(8);
        this.mVisible = false;
        this.mHideHandler.removeCallbacks(this.mShowPart2Runnable);
        this.mHideHandler.postDelayed(this.mHidePart2Runnable, 300);
    }

    @SuppressLint({"InlinedApi"})
    private void show() {
        this.mContentView.setSystemUiVisibility(1536);
        this.mVisible = AUTO_HIDE;
        this.mHideHandler.removeCallbacks(this.mHidePart2Runnable);
        this.mHideHandler.postDelayed(this.mShowPart2Runnable, 300);
    }

    private void delayedHide(int delayMillis) {
        this.mHideHandler.removeCallbacks(this.mHideRunnable);
        this.mHideHandler.postDelayed(this.mHideRunnable, (long) delayMillis);
    }
}

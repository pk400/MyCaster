package com.example.joel.mycaster;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Joel on 4/11/2015.
 */
public class OperationHandler {
    int downx, upx;

    public void handleGestures(RelativeLayout rl, final Activity a, final Intent right, final Intent left) {
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downx = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        upx = (int) event.getX();
                        if (upx > downx && (upx - downx) > 250) {
                            // RIGHT
                            a.startActivity(right);
                            a.finish();
                        } else if (upx < downx && (downx - upx) > 250) {
                            // LEFT
                            a.startActivity(left);
                            a.finish();
                        }
                        return true;
                }
                return false;
            }
        });
    }
}

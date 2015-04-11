package com.example.joel.mycaster;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class Almanac extends Activity {

    XMLData data;
    RelativeLayout rl;
    TextView todaysDateTV, lastModifiedTV,locationTV;
    int downx, upx;

    public Almanac() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almanac);

        long timerStart = new Date().getTime();
        rl = (RelativeLayout) findViewById(R.id.rlalmanac);
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
                            Intent i = new Intent(getBaseContext(), WeeklyView.class);
                            startActivity(i);
                            finish();
                        } else if (upx < downx && (downx - upx) > 250) {
                            // LEFT
                            Intent i = new Intent(getBaseContext(), SunRiseSet.class);
                            startActivity(i);
                            finish();
                        }
                        return true;
                }
                return false;
            }
        });

        findViews();

        todaysDateTV.setText(new MCDate().getTodaysDate());
        locationTV.setText(MainActivity.data.getLocationName() + ", " + MainActivity.data.getLocationCountry());
        checkTime(timerStart);
    }

    public void findViews() {
        todaysDateTV = (TextView) findViewById(R.id.todaysDate);
        lastModifiedTV  = (TextView) findViewById(R.id.lastModified);
        locationTV      = (TextView) findViewById(R.id.location);
    }

    public void checkTime(final long start) {
        final Calendar c = Calendar.getInstance();
        final Date timeNow = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Timer timer = new Timer();
        TimerTask timerTask;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        long difference = (new Date().getTime() - start) / 1000;
                        int minute = (int) difference / 60;
                        if(minute < 1) {
                            lastModifiedTV.setText("Last updated: Less than a minute ago at " + dateFormat.format(c.getTime()));
                        } else if(minute == 1) {
                            lastModifiedTV.setText("Last updated: " + minute + " minute ago at " + dateFormat.format(timeNow));
                        } else {
                            lastModifiedTV.setText("Last updated: " + minute + " minutes ago at " + dateFormat.format(timeNow));
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 30000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_almanac, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

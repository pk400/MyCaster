package com.example.joel.mycaster;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class SunRiseSet extends Activity {

    RelativeLayout rl;
    TextView todaysDateTV, lastModifiedTV,locationTV, sunriseEXTV, sunriseOCTV, sunsetEXTV, sunsetOCTV;
    OperationHandler oh;

    public SunRiseSet() {
        super();
        oh = new OperationHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sun_rise_set_layout);

        displayData();
    }

    private void displayData() {
        long timerStart = new Date().getTime();

        findViews();

        oh.handleGestures(rl, this,
                new Intent(getBaseContext(), Almanac.class),
                new Intent(getBaseContext(), DayView.class));

        todaysDateTV.setText(new MCDate().getTodaysDate());
        locationTV.setText(MainActivity.data.getLocationName() + ", " + MainActivity.data.getLocationCountry());
        checkTime(timerStart);

        SimpleDateFormat format1 = new SimpleDateFormat("hhmm");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
        format2.setTimeZone(TimeZone.getTimeZone("EST"));
        String newRise = null;
        try {
            Date rise = format1.parse(MainActivity.data.getSunriseHourUTC() + MainActivity.data.getSunriseMinuteUTC());
            newRise = format2.format(rise);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        sunriseOCTV.setText("Expected: " + newRise);
        sunsetOCTV.setText("Expected: " + MainActivity.data.getSunsetHourUTC() + ":" + MainActivity.data.getSunsetMinuteUTC());
    }

    public void findViews() {
        rl = (RelativeLayout) findViewById(R.id.rlsunriseset);
        todaysDateTV    = (TextView) findViewById(R.id.todaysDate);
        lastModifiedTV  = (TextView) findViewById(R.id.lastModified);
        locationTV      = (TextView) findViewById(R.id.location);
        sunriseOCTV     = (TextView) findViewById(R.id.sunriseoccurred);
        sunsetOCTV      = (TextView) findViewById(R.id.sunsetoccurred);
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
}

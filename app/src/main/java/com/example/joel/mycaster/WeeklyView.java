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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class WeeklyView extends Activity {

    RelativeLayout rl;
    TextView todaysDateTV, lastModifiedTV,locationTV;
    OperationHandler oh;

    public WeeklyView() {
        super();
        oh = new OperationHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);

        displayData();
    }

    private void displayData() {
        long timerStart = new Date().getTime();

        findViews();

        oh.handleGestures(rl, this,
                new Intent(getBaseContext(), DayView.class),
                new Intent(getBaseContext(), Almanac.class));

        todaysDateTV.setText(new MCDate().getTodaysDate());
        locationTV.setText(MainActivity.data.getLocationName() + ", " + MainActivity.data.getLocationCountry());
        checkTime(timerStart);

        TextView day1 = (TextView) findViewById(R.id.day1);
        ImageView day1icon = (ImageView) findViewById(R.id.day1icon);
        TextView day1c = (TextView) findViewById(R.id.day1c);

        TextView day2 = (TextView) findViewById(R.id.day2);
        ImageView day2icon = (ImageView) findViewById(R.id.day2icon);
        TextView day2c = (TextView) findViewById(R.id.day2c);

        TextView day3 = (TextView) findViewById(R.id.day3);
        ImageView day3icon = (ImageView) findViewById(R.id.day3icon);
        TextView day3c = (TextView) findViewById(R.id.day3c);

        TextView day4 = (TextView) findViewById(R.id.day4);
        ImageView day4icon = (ImageView) findViewById(R.id.day4icon);
        TextView day4c = (TextView) findViewById(R.id.day4c);

        TextView day5 = (TextView) findViewById(R.id.day5);
        ImageView day5icon = (ImageView) findViewById(R.id.day5icon);
        TextView day5c = (TextView) findViewById(R.id.day5c);


        day1.setText(MainActivity.data.getForecast1Day());
        getWeatherIcon(day1icon, MainActivity.data.getForecast1Condition());
        day1c.setText(MainActivity.data.getForecast1Temperature());

        day2.setText(MainActivity.data.getForecast2Day());
        getWeatherIcon(day2icon, MainActivity.data.getForecast2Condition());
        day2c.setText(MainActivity.data.getForecast2Temperature());

        day3.setText(MainActivity.data.getForecast3Day());
        getWeatherIcon(day3icon, MainActivity.data.getForecast3Condition());
        day3c.setText(MainActivity.data.getForecast3Temperature());

        day4.setText(MainActivity.data.getForecast4Day());
        getWeatherIcon(day4icon, MainActivity.data.getForecast4Condition());
        day4c.setText(MainActivity.data.getForecast4Temperature());

        day5.setText(MainActivity.data.getForecast5Day());
        getWeatherIcon(day5icon, MainActivity.data.getForecast5Condition());
        day5c.setText(MainActivity.data.getForecast5Temperature());

    }

    public void getWeatherIcon(ImageView wv, String s) {
        switch(s) {
            case "0": case "30":
                wv.setImageResource(R.drawable.sunny); break;
            case "01": case "31":
                wv.setImageResource(R.drawable.mainly_sunny); break;
            case "02": case "32":
                wv.setImageResource(R.drawable.partly_cloudy); break;
            case "03": case "33":
                wv.setImageResource(R.drawable.mostly_cloudy); break;
            case "06": case "11": case "12": case "36":
                wv.setImageResource(R.drawable.light_rain); break;
            case "07": case "37":
                wv.setImageResource(R.drawable.light_rain_snowshower); break;
            case "08": case "38":
                wv.setImageResource(R.drawable.snow); break;
            case "10":
                wv.setImageResource(R.drawable.cloudy); break;
            case "13":
                wv.setImageResource(R.drawable.heavy_rain); break;
            case "14":
                wv.setImageResource(R.drawable.freezing); break;
            case "15":
                wv.setImageResource(R.drawable.light_rain_snowshower); break;
            case "16": case "17": case "18": case "25": case "26": case "27": case "28":
                wv.setImageResource(R.drawable.snow); break;
            case "19":
                wv.setImageResource(R.drawable.thunderstorm_rain); break;
            case "39":
                wv.setImageResource(R.drawable.thunderstorm); break;
            case "23": case "24":
                wv.setImageResource(R.drawable.fog); break;
            default:
                wv.setImageResource(R.drawable.sunny); break;
        }
    }

    public void findViews() {
        rl = (RelativeLayout) findViewById(R.id.rlweeklyview);
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
}

package com.example.joel.mycaster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class DayView extends Activity {

    RelativeLayout rl;
    TextView todaysDateTV, lastModifiedTV, conditionTV, temperatureTV, locationTV, warningsTV,
            windSpeedTV, visibilityTV;
    ImageView weatherIconIV;
    OperationHandler oh;

    public DayView() {
        super();
        oh = new OperationHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        displayData();
    }

    public void displayData() {
        long timerStart = new Date().getTime();

        findViews();

        oh.handleGestures(rl, this,
                new Intent(getBaseContext(), SunRiseSet.class),
                new Intent(getBaseContext(), WeeklyView.class));

        // Set current date
        todaysDateTV.setText(new MCDate().getTodaysDate());

        locationTV.setText(MainActivity.data.getLocationName() + ", " + MainActivity.data.getLocationCountry());
        Log.d("SOMETHINGS WRONG", "ITS HERE");

        // Update last updated
        checkTime(timerStart);

        conditionTV.setText(MainActivity.data.getCurrentCondition());
        temperatureTV.setText(MainActivity.data.getCurrentTemperature() + " \u2103");

        String wind_direction;
        switch(MainActivity.data.getCurrentWindDirection()) {
            case "N":   wind_direction = "North"; break;
            case "NNE": wind_direction = "North North-East"; break;
            case "NE":  wind_direction = "North East"; break;
            case "ENE": wind_direction = "East North-East"; break;
            case "E":   wind_direction = "East"; break;
            case "ESE": wind_direction = "East South-East"; break;
            case "SE":  wind_direction = "South East"; break;
            case "SSE": wind_direction = "South South-East"; break;
            case "S":   wind_direction = "South"; break;
            case "SSW": wind_direction = "South South-West"; break;
            case "SW":  wind_direction = "South West"; break;
            case "WSW": wind_direction = "West South-West"; break;
            case "W":   wind_direction = "West"; break;
            case "WNW": wind_direction = "West North-West"; break;
            case "NW":  wind_direction = "North West"; break;
            case "NNW": wind_direction = "North North-West"; break;
            default:    wind_direction = null; break;
        }
        windSpeedTV.setText(MainActivity.data.getCurrentWindSpeed() + " km/h " + wind_direction);
        visibilityTV.setText(MainActivity.data.getCurrentVisibility() + " km");

        //if weather is sunny/cloudy/clear/etc.
        switch(MainActivity.data.getIconCode()) {
            case "0": case "30":
                weatherIconIV.setImageResource(R.drawable.sunny); break;
            /*case "01": case "31":
                weatherIconIV.setImageResource(R.drawable.mainly_sunny); break;
            case "02": case "32":
                weatherIconIV.setImageResource(R.drawable.partly_cloudy); break;
            case "03": case "33":
                weatherIconIV.setImageResource(R.drawable.mostly_cloudy); break;
            case "06": case "11": case "12": case "36":
                weatherIconIV.setImageResource(R.drawable.light_rain); break;
            case "07": case "37":
                weatherIconIV.setImageResource(R.drawable.light_rain_snowshower); break;
            case "08": case "38":
                weatherIconIV.setImageResource(R.drawable.snow); break;
            case "10":
                weatherIconIV.setImageResource(R.drawable.cloudy); break;
            case "13":
                weatherIconIV.setImageResource(R.drawable.heavy_rain); break;
            case "14":
                weatherIconIV.setImageResource(R.drawable.freezing); break;
            case "15":
                weatherIconIV.setImageResource(R.drawable.light_rain_snowshower); break;
            case "16": case "17": case "18": case "25": case "26": case "27": case "28":
                weatherIconIV.setImageResource(R.drawable.snow); break;
            case "19":
                weatherIconIV.setImageResource(R.drawable.thunderstorm_rain); break;
            case "39":
                weatherIconIV.setImageResource(R.drawable.thunderstorm); break;
            case "23": case "24":
                weatherIconIV.setImageResource(R.drawable.fog); break;
            default:
                weatherIconIV.setImageResource(R.drawable.warnings); break;*/
            default:
                weatherIconIV.setImageResource(R.drawable.sunny); break;
        }


        if(MainActivity.data.getWarningPriority() != null) {
            if (MainActivity.data.getWarningPriority().equals("low")) {
                warningsTV.setTextColor(Color.GREEN);
            } else if (MainActivity.data.getWarningPriority().equals("medium")) {
                warningsTV.setTextColor(Color.YELLOW);
            } else if (MainActivity.data.getWarningPriority().equals("high")) {
                warningsTV.setTextColor(Color.RED);
            }
            warningsTV.setText(MainActivity.data.getWarningDescription());
        }
    }

    private void findViews() {
        rl              = (RelativeLayout) findViewById(R.id.rlmain);

        // TEXTVIEWS
        todaysDateTV    = (TextView) findViewById(R.id.todaysDate);
        lastModifiedTV  = (TextView) findViewById(R.id.lastModified);
        conditionTV     = (TextView) findViewById(R.id.condition);
        temperatureTV   = (TextView) findViewById(R.id.temperature);
        locationTV      = (TextView) findViewById(R.id.location);
        windSpeedTV     = (TextView) findViewById(R.id.windspeed);
        visibilityTV    = (TextView) findViewById(R.id.visibility);
        warningsTV      = (TextView) findViewById(R.id.warnings);

        // IMAGEVIEWS
        weatherIconIV   = (ImageView) findViewById(R.id.weathericon);

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

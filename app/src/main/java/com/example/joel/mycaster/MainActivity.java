package com.example.joel.mycaster;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends Activity {

    static String baseurl   = "http://dd.weather.gc.ca/citypage_weather/xml";
    static File xmldir      = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataset");
    static File xmlfile     = new File(xmldir + "/data.xml");
    RelativeLayout rl;
    TextView todaysDateTV, lastModifiedTV, conditionTV, temperatureTV, locationTV, warningsTV,
            windSpeedTV, visibilityTV;
    ImageView weatherIconIV;
    Button weeklyViewBtn;
    XMLData data;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 1;
    ProgressDialog pd;

    int downx, upx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadData().execute();

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

    // Downloads a copy of the xml file from the site
    public class DownloadData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                if(!xmldir.exists()) {
                    xmldir.mkdirs();
                }

                URL url = new URL(baseurl + "/ON/s0000458_e.xml");
                URLConnection cxn = url.openConnection();
                cxn.connect();

                int lengthOfFile = cxn.getContentLength();
                File file = new File(xmldir, "data.xml");

                InputStream in = new BufferedInputStream(url.openStream());
                OutputStream out = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;
                int count;
                while ((count = in.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    out.write(data, 0, count);
                }

                out.flush();
                out.close();
                in.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            parseData();
            displayData();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            pd.setProgress(Integer.parseInt(values[0]));
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                pd = new ProgressDialog(this);
                pd.setMessage("Grabbing dataset...");
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.show();
                return pd;
            default:
                return null;
        }

    }

    public void parseData() {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            XMLHandler handler = new XMLHandler();
            sp.parse(xmlfile, handler);

            data = handler.getXMLData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void displayData() {
        long timerStart = new Date().getTime();

        findViews();

        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downx = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        upx = (int) event.getX();
                        if(upx > downx && (upx - downx) > 250) {
                            Log.d("Swiping", "Right" + upx + " " + downx);
                            Intent i = new Intent(getBaseContext(), WeeklyView.class);
                            startActivity(i);
                        } else if(upx < downx && (downx - upx) > 250) {
                            Log.d("Swiping", "Left" + upx + " " + downx);
                            Log.d("Swiping", "Right" + upx + " " + downx);
                            Intent i = new Intent(getBaseContext(), SunRiseSet.class);
                            startActivity(i);
                        }
                        return true;
                }
                return false;
            }
        });

        // Set current date
        todaysDateTV.setText(new MCDate().getTodaysDate());

        locationTV.setText(data.getLocationName() + ", " + data.getLocationCountry());

        // Update last updated
        checkTime(timerStart);

        conditionTV.setText(data.getCurrentCondition());
        temperatureTV.setText(data.getCurrentTemperature() + " \u2103");

        String wind_direction;
        switch(data.getCurrentWindDirection()) {
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
        windSpeedTV.setText(data.getCurrentWindSpeed() + " km/h " + wind_direction);
        visibilityTV.setText(data.getCurrentVisibility() + " km");

        //if weather is sunny/cloudy/clear/etc.
        Log.v("testing", data.getIconCode());
        switch(data.getIconCode()) {
            case "0": case "30":
                weatherIconIV.setImageResource(R.drawable.sunny); break;
            case "01": case "31":
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
                weatherIconIV.setImageResource(R.drawable.warnings); break;
        }


        if(data.getWarningPriority() != null) {
            if (data.getWarningPriority().equals("low")) {
                warningsTV.setTextColor(Color.GREEN);
            } else if (data.getWarningPriority().equals("medium")) {
                warningsTV.setTextColor(Color.YELLOW);
            } else if (data.getWarningPriority().equals("high")) {
                warningsTV.setTextColor(Color.RED);
            }
            warningsTV.setText(data.getWarningDescription());
        }
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

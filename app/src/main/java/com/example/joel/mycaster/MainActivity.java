package com.example.joel.mycaster;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkForUpdates();
        getTodaysDate();
        getLastModified();
        TextView city = (TextView) findViewById(R.id.city);
        try {
            URL website = new URL("http://dd.weather.gc.ca/citypage_weather/xml/ON/s0000458_e.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();


            XMLHandler doingStuff = new XMLHandler();
            sp.parse(new FileInputStream("/dataset/test.xml"), doingStuff);
            city.setText(doingStuff.getInformation());


/*
            XMLReader xr = sp.getXMLReader();
            XMLHandler doingStuff = new XMLHandler();
            city.setText(doingStuff.getInformation());
            xr.setContentHandler(doingStuff);
            xr.parse(new InputSource(website.openStream()));
            String information = doingStuff.getInformation();
            city.setText(information);*/
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForUpdates() {
        // static final String baseURL = "http://dd.weather.gc.ca/citypage_weather/xml/";
        String url = "http://dd.weather.gc.ca/citypage_weather/xml/ON/s0000458_e.xml";
        String fileName = "data.xml";
        new DownloadFile().execute(url, fileName);
    }

    public void getTodaysDate() {
        TextView todaysDateTV = (TextView) findViewById(R.id.todaysDate);
        Calendar c = Calendar.getInstance();
        StringBuilder todaysDateStr = new StringBuilder();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch(dayOfWeek) {
            case 0: todaysDateStr.append("Saturday");  break;
            case 1: todaysDateStr.append("Sunday");    break;
            case 2: todaysDateStr.append("Monday");    break;
            case 3: todaysDateStr.append("Tuesday");   break;
            case 4: todaysDateStr.append("Wednesday"); break;
            case 5: todaysDateStr.append("Thursday");  break;
            case 6: todaysDateStr.append("Friday");    break;
        }
        todaysDateStr.append(", ");
        int month = c.get(Calendar.MONTH);
        switch(month) {
            case 0:  todaysDateStr.append("January");   break;
            case 1:  todaysDateStr.append("February");  break;
            case 2:  todaysDateStr.append("March");     break;
            case 3:  todaysDateStr.append("April");     break;
            case 4:  todaysDateStr.append("May");       break;
            case 5:  todaysDateStr.append("June");      break;
            case 6:  todaysDateStr.append("July");      break;
            case 7:  todaysDateStr.append("August");    break;
            case 8:  todaysDateStr.append("September"); break;
            case 9:  todaysDateStr.append("October");   break;
            case 10: todaysDateStr.append("November");  break;
            case 11: todaysDateStr.append("December");  break;
        }
        todaysDateStr.append(" " + c.get(Calendar.DATE));
        todaysDateStr.append(", " + c.get(Calendar.YEAR));
        todaysDateTV.setText(todaysDateStr);
    }

    public void getLastModified() {
        TextView lastModifiedTV = (TextView) findViewById(R.id.lastModified);
        StringBuilder lastModifiedStr = new StringBuilder();
        int minutes;

        minutes = 1;
        lastModifiedStr.append("Recently Updated: " + minutes + "minute");
        if(minutes > 1) {
            lastModifiedStr.append("s");
        }
        lastModifiedStr.append(" ago");

        lastModifiedTV.setText(lastModifiedStr);
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

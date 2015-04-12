package com.example.joel.mycaster;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity {

    private static String UserLocation = null;
    private static String UserLocationCode = null;
    private static String UserLocationProvince = null;

    private static String baseurl   = "http://dd.weather.gc.ca/citypage_weather/xml";
    private static File xmldir      = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataset");
    private static File xmlfile     = new File(xmldir + "/" + UserLocationCode + ".xml");
    private static File siteList    = new File(xmldir + "/siteList.xml");
    private static File locationsPath   = new File(xmldir + "/locations.sqlite");

    public static List<LocationXMLData> locData;
    public static XMLData data;
    private static final int DIALOG_DOWNLOAD_PROGRESS = 1;
    private ProgressDialog pd;
    private enum ParseType { LOCATION_DATA, WEATHER_DATA }
    private DBHandler db = new DBHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if(!siteList.exists()) {
            new DownloadData().execute("/siteList.xml");
        //} //else {
            //parseData(ParseType.LOCATION_DATA);
        //}

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries);
        AutoCompleteTextView enterRegion = (AutoCompleteTextView) findViewById(R.id.regionfinder);
        enterRegion.setAdapter(adapter);*/

/*
        ListView lv = (ListView) findViewById(R.id.selectregion);

        ArrayAdapter<LocationXMLData> adapter = new ArrayAdapter<LocationXMLData>(this, android.R.layout.simple_list_item_1, locData);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);*/

        //new DownloadData().execute("/data.xml");
    }

    // Downloads a copy of the xml file from the site
    public class DownloadData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            InputStream in = null;
            OutputStream out = null;

            try {
                if (!xmldir.exists()) {
                    xmldir.mkdirs();
                }

                URL url = new URL(baseurl + params[0]);
                URLConnection cxn = url.openConnection();
                cxn.connect();

                int lengthOfFile = cxn.getContentLength();
                File file = new File(xmldir, params[0]);

                in = new BufferedInputStream(url.openStream());
                out = new FileOutputStream(file);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return params[0];
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

            if(s.equals("/siteList.xml"))
                parseData(ParseType.LOCATION_DATA);
            else {
                parseData(ParseType.WEATHER_DATA);
                Intent i = new Intent(getBaseContext(), DayView.class);
                startActivity(i);
                finish();
            }

            if(!locationsPath.exists()) {
                for (LocationXMLData x : locData) {
                    db.addLocation(x);
                }
            }

            final List<LocationXMLData> dbLocationData = db.getAllLocations();
            List<String> locNames = new ArrayList<String>();

            for(LocationXMLData d : dbLocationData) {
                /*String log = "ID: " + d.getID()
                        + " , CODE: " + d.getCode()
                        + " , NAMEEN: " + d.getNameEN()
                        + " , NAMEFR: " + d.getNameFR()
                        + " , PROVINCE: " + d.getProvinceCode();*/
                String log = "Name: " + d.getNameEN();
                Log.d("STUFF", log);

                //locNames[j++] = d.getNameEN();
                locNames.add(d.getNameEN());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, locNames);
            AutoCompleteTextView enterRegion = (AutoCompleteTextView) findViewById(R.id.regionfinder);
            enterRegion.setAdapter(adapter);
            final Button regionBtn = (Button) findViewById(R.id.regionbutton);
            enterRegion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    regionBtn.setVisibility(View.VISIBLE);
                    UserLocation = parent.getItemAtPosition(position).toString();
                    for (LocationXMLData d : dbLocationData) {
                        if(d.getNameEN().equals(UserLocation)) {
                            UserLocationCode = d.getCode();
                            UserLocationProvince = d.getProvinceCode();
                            break;
                        }
                    }
                }
            });
            regionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("TAG: ", UserLocation + " " + UserLocationCode + " " + UserLocationProvince);
                    new DownloadData().execute("/" + UserLocationCode + ".xml");
                    parseData(ParseType.LOCATION_DATA);
                }
            });
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
                pd.setMessage("Grabbing location data...");
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.show();
                return pd;
            default:
                return null;
        }
    }

    public void parseData(ParseType pt) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            if(pt.equals(ParseType.LOCATION_DATA)) {
                // Location data
                LocationXMLHandler handler = new LocationXMLHandler();
                InputStream ins = new FileInputStream(siteList);
                Reader r = new InputStreamReader(ins, "UTF-8");

                InputSource is = new InputSource(r);
                is.setEncoding("UTF-8");

                sp.parse(is, handler);

                locData = handler.getData();
            } else {
                // Weather data
                XMLHandler handler = new XMLHandler();
                sp.parse(xmlfile, handler);

                data = handler.getXMLData();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select");
        for(LocationXMLData l : locData) {
            menu.add(0, 0, 0, l.getNameEN());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }*/
}

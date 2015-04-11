package com.example.joel.mycaster;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity {

    private static String baseurl   = "http://dd.weather.gc.ca/citypage_weather/xml";
    private static File xmldir      = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataset");
    private static File xmlfile     = new File(xmldir + "/data.xml");
    public static XMLData data;
    private static final int DIALOG_DOWNLOAD_PROGRESS = 1;
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new DownloadData().execute();
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
            Intent i = new Intent(getBaseContext(), DayView.class);
            startActivity(i);
            finish();
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
}

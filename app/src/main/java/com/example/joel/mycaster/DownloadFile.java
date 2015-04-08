package com.example.joel.mycaster;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by joel on 08/04/15.
 */
public class DownloadFile extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataset");
            if(!dir.exists()) {
                dir.mkdirs();
            }

            URL url = new URL(params[0]);
            URLConnection cxn = url.openConnection();
            cxn.connect();

            File file = new File(dir, params[1]);

            InputStream in = new BufferedInputStream(url.openStream());
            OutputStream out = new FileOutputStream(file);

            byte data[] = new byte[1024];

            long total = 0;
            int count;
            while ((count = in.read(data)) != -1) {
                total += count;
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

    /*@Override
    protected String doInBackground(String... params) {
        int count;

        URL url = null;
        try {
            url = new URL(params[0]);
            URLConnection cxn = url.openConnection();
            cxn.connect();

            int lengthOfFile = cxn.getContentLength();

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataset";
            File dir = new File(path);

            InputStream in = new BufferedInputStream(url.openStream());
            OutputStream out = new FileOutputStream(dir + "/test.xml");

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = in.read(data)) != -1) {
                total += count;
                publishProgress(""+(int)((total*100)/lengthOfFile));
                out.write(data, 0, count);
            }

            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }*/

}

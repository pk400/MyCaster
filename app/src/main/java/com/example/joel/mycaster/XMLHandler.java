package com.example.joel.mycaster;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by joel on 08/04/15.
 */
public class XMLHandler extends DefaultHandler {

    ParsedData info = new ParsedData();

    /*@Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(localName.equals("region")) {
            String city = attributes.getValue("data");
            info.setCity(city);
        } else if(localName.equals("temperature")) {
            String t = attributes.getValue("data");
            int temp = Integer.parseInt(t);
            info.setTemp(temp);
        }
    }*/

    public String getInformation() {
        return info.dataToString();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.v("HEREROERWERWIEUJRHEWOIRHJWEOIR", qName);
        Log.v("SEONSDOISDHNFOSDFN", uri);
        if(localName.equals("region")) {
            String city = attributes.getValue("data");
            info.setCity(city);
        } else if(localName.equals("temperature")) {
            String t = attributes.getValue("data");
            int temp = Integer.parseInt(t);
            info.setTemp(temp);
        }
    }
}

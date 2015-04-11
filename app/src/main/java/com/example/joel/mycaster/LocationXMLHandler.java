package com.example.joel.mycaster;

import android.location.Location;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel on 4/11/2015.
 */
public class LocationXMLHandler extends DefaultHandler {
    List<LocationXMLData> locData;
    LocationXMLData data;
    StringBuilder content;
    Boolean inSite;

    public LocationXMLHandler() {
        locData = new ArrayList<LocationXMLData>();
        content = new StringBuilder();
        inSite = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        content = new StringBuilder();

        if(localName.equals("site")) {
            inSite = true;
            data = new LocationXMLData();
            data.setCode(attributes.getValue("code"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(localName.equals("nameEn") && inSite)
            data.setNameEN(content.toString());
        else if(localName.equals("nameFr") && inSite)
            data.setNameFR(content.toString());
        else if(localName.equals("provinceCode") && inSite)
            data.setProvinceCode(content.toString());
        else if(localName.equals("site") && inSite) {
            locData.add(data);
            inSite = false;
        }
    }

    public void characters(char[] buffer, int start, int length) {
        content.append(buffer, start, length);
    }

    public List<LocationXMLData> getData() {
        return locData;
    }
}

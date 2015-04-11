package com.example.joel.mycaster;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Joel on 4/8/2015.
 */
public class XMLHandler extends DefaultHandler {

    XMLData data;
    StringBuilder content;
    Boolean inDateTimeUTC, inDateTimeEDT, inLocation, inWarnings, inCurrentConditions,
            inForecastGroup, inRiseSet, inSunrise, inSunset;

    public XMLHandler() {
        data = new XMLData();
        content = new StringBuilder();
        inDateTimeUTC = false;
        inDateTimeEDT = false;
        inLocation = false;
        inWarnings = false;
        inCurrentConditions = false;
        inForecastGroup = false;
        inRiseSet = false;
        inSunrise = false;
        inSunset = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        content = new StringBuilder();

        if(localName.equals("dateTime")) {
            if(attributes.getValue("name").equals("xmlCreation") && attributes.getValue("zone").equals("UTC")) {
                inDateTimeUTC = true;
            } else if(attributes.getValue("name").equals("xmlCreation") && attributes.getValue("zone").equals("EDT")) {
                inDateTimeEDT = true;
            } else if(attributes.getValue("name").equals("sunrise") && attributes.getValue("zone").equals("UTC")) {
                inSunrise = true;
            } else if(attributes.getValue("name").equals("sunset") && attributes.getValue("zone").equals("UTC")) {
                inSunset = true;
            }
        } else if(localName.equals("location")) {
            inLocation = true;
        } else if(localName.equals("event")) {
            data.setWarningPriority(attributes.getValue("priority"));
            data.setWarningDescription(attributes.getValue("description"));
            inWarnings = true;
        } else if(localName.equals("currentConditions")) {
            inCurrentConditions = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if(localName.equals("dateTime")) {
            if(inDateTimeUTC) {
                data.setXmlCreationStringUTC(content.toString());
                inDateTimeUTC = false;
            } else if(inDateTimeEDT) {
                data.setXmlCreationStringEDT(content.toString());
                inDateTimeEDT = false;
            }
        // LOCATION
        } else if(localName.equals("continent")) {
            if(inLocation)
                data.setLocationContinent(content.toString());
        } else if(localName.equals("country")) {
            if(inLocation)
                data.setLocationCountry(content.toString());
        } else if(localName.equals("province")) {
            if(inLocation)
                data.setLocationProvince(content.toString());
        } else if(localName.equals("name")) {
            if(inLocation)
                data.setLocationName(content.toString());
        } else if(localName.equals("region")) {
            if(inLocation) {
                data.setLocationRegion(content.toString());
                inLocation = false;
            }
        // CURRENT CONDITION
        } else if(localName.equals("condition")) {
            if (inCurrentConditions)
                data.setCurrentCondition(content.toString());
        } else if(localName.equals("iconCode")) {
            if(inCurrentConditions)
                data.setIconCode(content.toString());
        } else if(localName.equals("temperature")) {
            if (inCurrentConditions) {
                data.setCurrentTemperature(content.toString());
            }
        } else if(localName.equals("visibility")) {
            if(inCurrentConditions)
                data.setCurrentVisibility(content.toString());
        } else if(localName.equals("speed")) {
            if(inCurrentConditions) {
                data.setCurrentWindSpeed(content.toString());
            }
        } else if(localName.equals("direction")) {
            if(inCurrentConditions) {
                data.setCurrentWindDirection(content.toString());
                inCurrentConditions = false;
            }
        // SUNRISE/SUNSET
        } else if(localName.equals("hour")) {
            if(inSunrise)
                data.setSunriseHourUTC(content.toString());
            if(inSunset)
                data.setSunsetHourUTC(content.toString());
        } else if(localName.equals("minute")) {
            if(inSunrise) {
                data.setSunriseMinuteUTC(content.toString());
                inSunrise = false;
            }
            if(inSunset) {
                data.setSunsetMinuteUTC(content.toString());
                inSunset = false;
            }
        }
    }

    public void characters(char[] buffer, int start, int length) {
        content.append(buffer, start, length);
    }

    public XMLData getXMLData() {
        return data;
    }
}

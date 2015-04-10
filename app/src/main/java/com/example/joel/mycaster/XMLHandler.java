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
            inForecastGroup, inSunrise, inSunset;

    public XMLHandler() {
        data = new XMLData();
        content = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        content = new StringBuilder();

        if(localName.equals("dateTime")) {
            if(attributes.getValue("zone").equals("UTC")) {
                inDateTimeUTC = true;
            } else if(attributes.getValue("zone").equals("EDT")) {
                inDateTimeEDT = true;
            }
        } else if(localName.equals("location")) {
            inLocation = true;
        } else if(localName.equals("event")) {
            data.setWarningPriority(attributes.getValue("priority"));
            data.setWarningDescription(attributes.getValue("description"));
            inWarnings = true;
        } else if(localName.equals("currentConditions")) {
            inCurrentConditions = true;
        } else if(localName.equals("forecastGroup")) {
            inForecastGroup = true;
        } // DO SUNRISET
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
        } else if(localName.equals("continent")) {
            if(inLocation) {
                data.setLocationContinent(content.toString());
            }
        } else if(localName.equals("country")) {
            if(inLocation) {
                data.setLocationCountry(content.toString());
            }
        } else if(localName.equals("province")) {
            if(inLocation) {
                data.setLocationProvince(content.toString());
            }
        } else if(localName.equals("name")) {
            if(inLocation) {
                data.setLocationName(content.toString());
            }
        } else if(localName.equals("region")) {
            if(inLocation) {
                data.setLocationRegion(content.toString());
                inLocation = false;
            }
        }

        if(inCurrentConditions) {
            if (localName.equals("condition")) {
                //if (inCurrentConditions)
                    data.setCurrentCondition(content.toString());
            } else if (localName.equals("temperature")) {
                //if (inCurrentConditions) {
                    data.setCurrentTemperature(content.toString());
                    inCurrentConditions = false;
                //}
            }/* else if(localName.equals("speed")) {
                if(inCurrentConditions) {

                }
            }*/
        }
    }

    public void characters(char[] buffer, int start, int length) {
        content.append(buffer, start, length);
    }

    public XMLData getXMLData() {
        return data;
    }
}

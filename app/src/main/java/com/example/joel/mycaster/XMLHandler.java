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
            inForecastGroup, inSunrise, inSunset, inDayFC, inTemp, inAlmanac,
            exMax, exMin, nMax, nMin, nMean, exRain, exSnow, exPrec, exSOG;
    int inForecast;

    public XMLHandler() {
        data = new XMLData();
        content = new StringBuilder();
        inDateTimeUTC = false;
        inDateTimeEDT = false;
        inLocation = false;
        inWarnings = false;
        inCurrentConditions = false;
        inForecastGroup = false;
        inSunrise = false;
        inSunset = false;
        inForecast = 0;
        inDayFC = false;
        inTemp = false;
        inAlmanac = false;
        exMax = false;
        exMin = false;
        nMax = false;
        nMin = false;
        nMean = false;
        exRain = false;
        exSnow = false;
        exPrec = false;
        exSOG = false;
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
        } else if(localName.equals("forecastGroup")) {
            inForecastGroup = true;
        } else if(localName.equals("period") && inForecastGroup) {
            inForecast++;
            switch(inForecast) {
                case 1: data.setForecast1Day(attributes.getValue("textForecastName")); break;
                case 2: data.setForecast2Day(attributes.getValue("textForecastName")); break;
                case 3: break;
                case 4: data.setForecast3Day(attributes.getValue("textForecastName")); break;
                case 5: data.setForecast4Day(attributes.getValue("textForecastName")); break;
                case 6: data.setForecast5Day(attributes.getValue("textForecastName")); break;
                default: break;
            }
        } else if(localName.equals("abbreviatedForecast")) {
            inDayFC = true;
        } else if(localName.equals("temperatures")) {
            inTemp = true;
        } else if(localName.equals("almanac")) {
            inAlmanac = true;
        } else if(localName.equals("temperature") && inAlmanac) {
            if(attributes.getValue("class").equals("extremeMax"))
                exMax = true;
            if(attributes.getValue("class").equals("extremeMin"))
                exMin = true;
            if(attributes.getValue("class").equals("normalMax"))
                nMax = true;
            if(attributes.getValue("class").equals("normalMin"))
                nMin = true;
            if(attributes.getValue("class").equals("normalMean"))
                nMean = true;
        } else if(localName.equals("precipitation") && inAlmanac) {
            if(attributes.getValue("class").equals("extremeRainfall"))
                exRain = true;
            if(attributes.getValue("class").equals("extremeSnowfall"))
                exSnow = true;
            if(attributes.getValue("class").equals("extremePrecipitation"))
                exPrec = true;
            if(attributes.getValue("class").equals("extremeSnowOnGround"))
                exSOG = true;
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
        } else if(localName.equals("iconCode") && !inDayFC) {
            if(inCurrentConditions)
                data.setIconCode(content.toString());
        } else if(localName.equals("temperature")) {
            if (inCurrentConditions) {
                data.setCurrentTemperature(content.toString());
            }
            if(inAlmanac) {
                if(exMax) {
                    data.setExtremeMax(content.toString() + " \u2103");
                    exMax = false;
                } else if(exMin) {
                    data.setExtremeMin(content.toString() + " \u2103");
                    exMin = false;
                } else if(nMax) {
                    data.setNormalMax(content.toString() + " \u2103");
                    nMax = false;
                } else if(nMin) {
                    data.setNormalMin(content.toString() + " \u2103");
                    nMin = false;
                } else if(nMean) {
                    data.setNormalMean(content.toString() + " \u2103");
                    nMean = false;
                }
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
        } else if(localName.equals("precipitation")) {
            if(inAlmanac) {
                if(exRain) {
                    data.setExtremeRainfall(content.toString() + " mm");
                    exRain = false;
                } else if(exSnow) {
                    data.setExtremeSnowfall(content.toString() + " cm");
                    exSnow = false;
                } else if(exPrec) {
                    data.setExtremePrecipitation(content.toString() + " mm");
                    exPrec = false;
                } else if(exSOG) {
                    data.setExtremeSnowOnGround(content.toString() + " cm");
                    exSOG = false;
                    inAlmanac = false;
                }
            }
        } else if(inForecast > 0) {
            switch(inForecast) {
                case 1:
                    if(localName.equals("iconCode") && inDayFC) {
                        data.setForecast1Condition(content.toString());
                    } else if(localName.equals("textSummary") && inTemp) {
                        data.setForecast1Temperature(content.toString());
                    }
                    inTemp = false;
                    inDayFC = false;
                case 2:
                    if(localName.equals("iconCode") && inDayFC) {
                        data.setForecast2Condition(content.toString());
                    } else if(localName.equals("textSummary") && inTemp) {
                        data.setForecast2Temperature(content.toString());
                    }
                    inTemp = false;
                    inDayFC = false;
                case 3:
                    break;
                case 4:
                    if(localName.equals("iconCode") && inDayFC) {
                        data.setForecast3Condition(content.toString());
                    } else if(localName.equals("textSummary") && inTemp) {
                        data.setForecast3Temperature(content.toString());
                    }
                    inTemp = false;
                    inDayFC = false;
                case 5:
                    if(localName.equals("iconCode") && inDayFC) {
                        data.setForecast4Condition(content.toString());
                    } else if(localName.equals("textSummary") && inTemp) {
                        data.setForecast4Temperature(content.toString());
                    }
                    inTemp = false;
                    inDayFC = false;
                case 6:
                    if(localName.equals("iconCode") && inDayFC) {
                        data.setForecast5Condition(content.toString());
                    } else if(localName.equals("textSummary") && inTemp) {
                        data.setForecast5Temperature(content.toString());
                    }
                    inTemp = false;
                    inDayFC = false;
                default:
                    break;
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

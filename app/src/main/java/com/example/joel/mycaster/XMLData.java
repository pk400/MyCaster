package com.example.joel.mycaster;

import android.util.Log;

/**
 * Created by Joel on 4/8/2015.
 */
public class XMLData {



    // XML CREATION UTC
    private String xmlCreationStringUTC = null;

    // XML CREATION EDT
    private String  xmlCreationStringEDT = null;

    // LOCATION
    private String locationContinent    = null;
    private String locationCountry      = null;
    private String locationProvince     = null;
    private String locationName         = null;
    private String locationRegion       = null;

    // WARNINGS
    private String warningPriority      = null;
    private String warningDescription   = null;
    private String warningStringUTC     = null;
    private String warningStringEDT     = null;

    // CURRENT CONDITION
    private String currentCondition         = null;
    private String currentTemperature       = null;
    private String iconCode                 = null;
    private String currentDewPoint          = null;
    private String currentVisibility        = null;
    private String currentHumidity          = null;
    private String currentWindSpeed         = null;
    private String currentWindDirection     = null;
    private String currentWindBearing       = null;

    // FORECAST GROUP
    // FORECAST TODAY
    private String forecast1Day             = null;
    private String forecast1Temperature     = null;
    private String forecast1Wind            = null;
    private String forecast1Precipitation   = null;
    private String forecast1Visibility      = null;

    // FORECAST DAY 2
    private String forecast2Day             = null;
    private String forecast2Temperature     = null;
    private String forecast2Wind            = null;
    private String forecast2Precipitation   = null;
    private String forecast2Visibility      = null;

    // FORECAST DAY 3
    private String forecast3Day             = null;
    private String forecast3Temperature     = null;
    private String forecast3Wind            = null;
    private String forecast3Precipitation   = null;
    private String forecast3Visibility      = null;

    // FORECAST DAY 4
    private String forecast4Day             = null;
    private String forecast4Temperature     = null;
    private String forecast4Wind            = null;
    private String forecast4Precipitation   = null;
    private String forecast4Visibility      = null;

    // FORECAST DAY 5
    private String forecast5Day             = null;
    private String forecast5Temperature     = null;
    private String forecast5Wind            = null;
    private String forecast5Precipitation   = null;
    private String forecast5Visibility      = null;

    // FORECAST DAY 6
    private String forecast6Day             = null;
    private String forecast6Temperature     = null;
    private String forecast6Wind            = null;
    private String forecast6Precipitation   = null;
    private String forecast6Visibility      = null;

    // FORECAST DAY 7
    private String forecast7Day             = null;
    private String forecast7Temperature     = null;
    private String forecast7Wind            = null;
    private String forecast7Precipitation   = null;
    private String forecast7Visibility      = null;

    // SUNRISE
    private String sunriseHourUTC = null;
    private String sunriseMinuteUTC = null;

    // SUNSET
    private String sunsetHourUTC = null;
    private String sunsetMinuteUTC = null;


    //private String variable = null;

    //=========================================================================
    // SETTERS

    public void setXmlCreationStringUTC(String s)       { this.xmlCreationStringUTC = s; }

    public void setXmlCreationStringEDT(String s)       { this.xmlCreationStringEDT = s; }

    public void setLocationContinent(String s)          { this.locationContinent = s; }
    public void setLocationCountry(String s)            { this.locationCountry = s; }
    public void setLocationProvince(String s)           { this.locationProvince = s; }
    public void setLocationName(String s)               { this.locationName = s; }
    public void setLocationRegion(String s)             { this.locationRegion = s; }

    public void setWarningPriority(String s)            { this.warningPriority = s; }
    public void setWarningDescription(String s)         { this.warningDescription = s; }
    public void setWarningStringUTC(String s)           { this.warningStringUTC = s; }
    public void setWarningStringEDT(String s)           { this.warningStringEDT = s; }

    public void setCurrentCondition(String s)           { this.currentCondition = s; }
    public void setIconCode(String s)                   { this.iconCode = s; }
    public void setCurrentTemperature(String s)         { this.currentTemperature = s; }
    public void setCurrentDewPoint(String s)            { this.currentDewPoint = s; }
    public void setCurrentVisibility(String s)          { this.currentVisibility = s; }
    public void setCurrentHumidity(String s)            { this.currentHumidity = s; }
    public void setCurrentWindSpeed(String s)           { this.currentWindSpeed = s; }
    public void setCurrentWindDirection(String s)       { this.currentWindDirection = s; }
    public void setCurrentWindBearing(String s)         { this.currentWindBearing = s; }

    public void setForecast1Day(String s)               { this.forecast1Day = s; }
    public void setForecast1Temperature(String s)       { this.forecast1Temperature = s; }
    public void setForecast1Wind(String s)              { this.forecast1Wind = s; }
    public void setForecast1Precipitation(String s)     { this.forecast1Precipitation = s; }
    public void setForecast1Visibility(String s)        { this.forecast1Visibility = s; }

    public void setForecast2Day(String s)               { this.forecast2Day = s; }
    public void setForecast2Temperature(String s)       { this.forecast2Temperature = s; }
    public void setForecast2Wind(String s)              { this.forecast2Wind = s; }
    public void setForecast2Precipitation(String s)     { this.forecast2Precipitation = s; }
    public void setForecast2Visibility(String s)        { this.forecast2Visibility = s; }

    public void setForecast3Day(String s)               { this.forecast3Day = s; }
    public void setForecast3Temperature(String s)       { this.forecast3Temperature = s; }
    public void setForecast3Wind(String s)              { this.forecast3Wind = s; }
    public void setForecast3Precipitation(String s)     { this.forecast3Precipitation = s; }
    public void setForecast3Visibility(String s)        { this.forecast3Visibility = s; }

    public void setForecast4Day(String s)               { this.forecast4Day = s; }
    public void setForecast4Temperature(String s)       { this.forecast4Temperature = s; }
    public void setForecast4Wind(String s)              { this.forecast4Wind = s; }
    public void setForecast4Precipitation(String s)     { this.forecast4Precipitation = s; }
    public void setForecast4Visibility(String s)        { this.forecast4Visibility = s; }

    public void setForecast5Day(String s)               { this.forecast5Day = s; }
    public void setForecast5Temperature(String s)       { this.forecast5Temperature = s; }
    public void setForecast5Wind(String s)              { this.forecast5Wind = s; }
    public void setForecast5Precipitation(String s)     { this.forecast5Precipitation = s; }
    public void setForecast5Visibility(String s)        { this.forecast5Visibility = s; }

    public void setForecast6Day(String s)               { this.forecast6Day = s; }
    public void setForecast6Temperature(String s)       { this.forecast6Temperature = s; }
    public void setForecast6Wind(String s)              { this.forecast6Wind = s; }
    public void setForecast6Precipitation(String s)     { this.forecast6Precipitation = s; }
    public void setForecast6Visibility(String s)        { this.forecast6Visibility = s; }

    public void setForecast7Day(String s)               { this.forecast7Day = s; }
    public void setForecast7Temperature(String s)       { this.forecast7Temperature = s; }
    public void setForecast7Wind(String s)              { this.forecast7Wind = s; }
    public void setForecast7Precipitation(String s)     { this.forecast7Precipitation = s; }
    public void setForecast7Visibility(String s)        { this.forecast7Visibility = s; }

    public void setSunriseHourUTC(String s)             { this.sunriseHourUTC = s; }
    public void setSunriseMinuteUTC(String s)           { this.sunriseMinuteUTC = s; }
    public void setSunsetHourUTC(String s)              { this.sunsetHourUTC = s; }
    public void setSunsetMinuteUTC(String s)            { this.sunsetMinuteUTC = s; }



    //=========================================================================
    // GETTERS


    public String getXmlCreationStringUTC()     { return xmlCreationStringUTC; }

    public String getXmlCreationStringEDT()     { return xmlCreationStringEDT; }

    public String getLocationContinent()        { return locationContinent; }
    public String getLocationCountry()          { return locationCountry; }
    public String getLocationProvince()         { return locationProvince; }
    public String getLocationName()             { return locationName; }
    public String getLocationRegion()           { return locationRegion; }

    public String getWarningPriority()          { return warningPriority; }
    public String getWarningDescription()       { return warningDescription; }
    public String getWarningStringUTC()         { return warningStringUTC; }
    public String getWarningStringEDT()         { return warningStringEDT; }

    public String getCurrentCondition()         { return this.currentCondition; }
    public String getIconCode()                 { return iconCode; }
    public String getCurrentTemperature()       { return this.currentTemperature; }
    public String getCurrentVisibility()        { return currentVisibility; }
    public String getCurrentDewPoint()          { return currentDewPoint; }
    public String getCurrentHumidity()          { return currentHumidity; }
    public String getCurrentWindSpeed()         { return currentWindSpeed; }
    public String getCurrentWindBearing()       { return currentWindBearing; }
    public String getCurrentWindDirection()     { return currentWindDirection; }

    public String getForecast1Day()             { return forecast1Day; }
    public String getForecast1Temperature()     { return forecast1Temperature; }
    public String getForecast1Wind()            { return forecast1Wind; }
    public String getForecast1Precipitation()   { return forecast1Precipitation; }
    public String getForecast1Visibility()      { return forecast1Visibility; }

    public String getForecast2Day()             { return forecast2Day; }
    public String getForecast2Temperature()     { return forecast2Temperature; }
    public String getForecast2Wind()            { return forecast2Wind; }
    public String getForecast2Precipitation()   { return forecast2Precipitation; }
    public String getForecast2Visibility()      { return forecast2Visibility; }

    public String getForecast3Day()             { return forecast3Day; }
    public String getForecast3Temperature()     { return forecast3Temperature; }
    public String getForecast3Wind()            { return forecast3Wind; }
    public String getForecast3Precipitation()   { return forecast3Precipitation; }
    public String getForecast3Visibility()      { return forecast3Visibility; }

    public String getForecast4Day()             { return forecast4Day; }
    public String getForecast4Temperature()     { return forecast4Temperature; }
    public String getForecast4Wind()            { return forecast4Wind; }
    public String getForecast4Precipitation()   { return forecast4Precipitation; }
    public String getForecast4Visibility()      { return forecast4Visibility; }

    public String getForecast5Day()             { return forecast5Day; }
    public String getForecast5Temperature()     { return forecast5Temperature; }
    public String getForecast5Wind()            { return forecast5Wind; }
    public String getForecast5Precipitation()   { return forecast5Precipitation; }
    public String getForecast5Visibility()      { return forecast5Visibility; }

    public String getForecast6Day()             { return forecast6Day; }
    public String getForecast6Temperature()     { return forecast6Temperature; }
    public String getForecast6Wind()            { return forecast6Wind; }
    public String getForecast6Precipitation()   { return forecast6Precipitation; }
    public String getForecast6Visibility()      { return forecast6Visibility; }

    public String getForecast7Day()             { return forecast7Day; }
    public String getForecast7Temperature()     { return forecast7Temperature; }
    public String getForecast7Wind()            { return forecast7Wind; }
    public String getForecast7Precipitation()   { return forecast7Precipitation; }
    public String getForecast7Visibility()      { return forecast7Visibility; }

    public String getSunriseHourUTC()           { return sunriseHourUTC; }
    public String getSunriseMinuteUTC()         { return sunriseMinuteUTC; }
    public String getSunsetHourUTC()            { return sunsetHourUTC; }
    public String getSunsetMinuteUTC()          { return sunsetMinuteUTC; }
}

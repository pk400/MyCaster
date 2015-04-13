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
    private String forecast1Condition       = null;
    private String forecast1Temperature     = null;

    // FORECAST DAY 2
    private String forecast2Day             = null;
    private String forecast2Condition       = null;
    private String forecast2Temperature     = null;

    // FORECAST DAY 3
    private String forecast3Day             = null;
    private String forecast3Condition       = null;
    private String forecast3Temperature     = null;

    // FORECAST DAY 4
    private String forecast4Day             = null;
    private String forecast4Condition       = null;
    private String forecast4Temperature     = null;

    // FORECAST DAY 5
    private String forecast5Day             = null;
    private String forecast5Condition       = null;
    private String forecast5Temperature     = null;

    // ALMANAC
    private String extremeMax = "No data";
    private String extremeMin = "No data";
    private String normalMax = "No data";
    private String normalMin = "No data";
    private String normalMean = "No data";
    private String extremeRainfall = "No data";
    private String extremeSnowfall = "No data";
    private String extremePrecipitation = "No data";
    private String extremeSnowOnGround = "No data";

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
    public String getForecast1Condition() { return forecast1Condition; }
    public void setForecast1Temperature(String s)       { this.forecast1Temperature = s; }

    public void setForecast2Day(String s)               { this.forecast2Day = s; }
    public String getForecast2Condition() { return forecast2Condition; }
    public void setForecast2Temperature(String s)       { this.forecast2Temperature = s; }

    public void setForecast3Day(String s)               { this.forecast3Day = s; }
    public String getForecast3Condition() { return forecast3Condition; }
    public void setForecast3Temperature(String s)       { this.forecast3Temperature = s; }

    public void setForecast4Day(String s)               { this.forecast4Day = s; }
    public String getForecast4Condition() { return forecast4Condition; }
    public void setForecast4Temperature(String s)       { this.forecast4Temperature = s; }

    public void setForecast5Day(String s)               { this.forecast5Day = s; }
    public String getForecast5Condition() { return forecast5Condition; }
    public void setForecast5Temperature(String s)       { this.forecast5Temperature = s; }

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
    public void setForecast1Condition(String s) { this.forecast1Condition = s; }
    public String getForecast1Temperature()     { return forecast1Temperature; }

    public String getForecast2Day()             { return forecast2Day; }
    public void setForecast2Condition(String s) { this.forecast2Condition = s; }
    public String getForecast2Temperature()     { return forecast2Temperature; }

    public String getForecast3Day()             { return forecast3Day; }
    public void setForecast3Condition(String s) { this.forecast3Condition = s; }
    public String getForecast3Temperature()     { return forecast3Temperature; }

    public String getForecast4Day()             { return forecast4Day; }
    public void setForecast4Condition(String s) {  this.forecast4Condition = s;}
    public String getForecast4Temperature()     { return forecast4Temperature; }

    public String getForecast5Day()             { return forecast5Day; }
    public void setForecast5Condition(String s) { this.forecast5Condition = s; }
    public String getForecast5Temperature()     { return forecast5Temperature; }

    public String getSunriseHourUTC()           { return sunriseHourUTC; }
    public String getSunriseMinuteUTC()         { return sunriseMinuteUTC; }
    public String getSunsetHourUTC()            { return sunsetHourUTC; }
    public String getSunsetMinuteUTC()          { return sunsetMinuteUTC; }

    public String getExtremeMax() {
        return extremeMax;
    }

    public void setExtremeMax(String extremeMax) {
        this.extremeMax = extremeMax;
    }

    public String getExtremeMin() {
        return extremeMin;
    }

    public void setExtremeMin(String extremeMin) {
        this.extremeMin = extremeMin;
    }

    public String getNormalMax() {
        return normalMax;
    }

    public void setNormalMax(String normalMax) {
        this.normalMax = normalMax;
    }

    public String getNormalMin() {
        return normalMin;
    }

    public void setNormalMin(String normalMin) {
        this.normalMin = normalMin;
    }

    public String getExtremeRainfall() {
        return extremeRainfall;
    }

    public void setExtremeRainfall(String extremeRainfall) {
        this.extremeRainfall = extremeRainfall;
    }

    public String getExtremeSnowfall() {
        return extremeSnowfall;
    }

    public void setExtremeSnowfall(String extremeSnowfall) {
        this.extremeSnowfall = extremeSnowfall;
    }

    public String getExtremePrecipitation() {
        return extremePrecipitation;
    }

    public void setExtremePrecipitation(String extremePrecipitation) {
        this.extremePrecipitation = extremePrecipitation;
    }

    public String getExtremeSnowOnGround() {
        return extremeSnowOnGround;
    }

    public void setExtremeSnowOnGround(String extremeSnowOnGround) {
        this.extremeSnowOnGround = extremeSnowOnGround;
    }

    public String getNormalMean() {
        return normalMean;
    }

    public void setNormalMean(String normalMean) {
        this.normalMean = normalMean;
    }
}

package com.example.joel.mycaster;

/**
 * Created by joel on 08/04/15.
 */
public class ParsedData {
    int temp = 0;
    String city = null;

    public void setCity(String c) {
        this.city  = c;
    }

    public void setTemp(int t) {
        this.temp = t;
    }

    public String dataToString() {
        return "In " + this.city + " the current temperature " + this.temp;
    }
}

package com.example.joel.mycaster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel on 4/11/2015.
 */
public class LocationXMLData {
    protected int ID;
    protected String code;
    protected String nameEN;
    protected String nameFR;
    protected String provinceCode;

    public LocationXMLData() {
        ID = 0;
        code = null;
        nameEN = null;
        nameFR =  null;
        provinceCode = null;
    }

    public LocationXMLData(int i, String s1, String s2, String s3, String s4) {
        ID = i;
        code = s1;
        nameEN = s2;
        nameFR = s3;
        provinceCode = s4;
    }

    public int getID() { return ID; }
    public void setID(int i) { this.ID = i; }

    public String getCode() { return code; }
    public void setCode(String s) { this.code = s; }

    public String getNameEN() { return nameEN; }
    public void setNameEN(String s) { this.nameEN = s; }

    public String getNameFR() { return nameFR; }
    public void setNameFR(String s) { this.nameFR = s; }

    public String getProvinceCode() { return provinceCode; }
    public void setProvinceCode(String s) { this.provinceCode = s; }
}

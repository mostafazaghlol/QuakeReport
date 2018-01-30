package com.example.android.quakereport;

import java.net.URL;

/**
 * Created by mostafa on 12/22/17.
 */

public class EarthquakeData {
    private String place;
    private long data;
    private double mag;
    private String site;

    public EarthquakeData(double mMag,String mPlace,long mData,String mSite){
        this.mag=mMag;
        this.place=mPlace;
        this.data=mData;
        this.site=mSite;
    }

    public String getSite() {
        return site;
    }

    public long getData() {
        return data;
    }

    public double getMag() {
        return mag;
    }

    public String getPlace() {
        return place;
    }

}

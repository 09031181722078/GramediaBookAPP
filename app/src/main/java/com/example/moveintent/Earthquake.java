package com.example.moveintent;

public class Earthquake {

    private String mLocation;
    private int mDate;
    private String mUrl;

    /** URL situs web gempa */


    /**public Earthquake(String magnitudo,String location,String date){
     mMagnitudo = magnitudo;
     mLocation = location;
     mDate = date;
     }*/
    public Earthquake(String location, int Date, String url) {
        mDate = Date;
        mLocation = location;
        mUrl = url;

    }

    public int getDate(){
        return mDate;
    }

    public String getLocation(){
        return mLocation;
    }

    public String getUrl(){
        return mUrl;
    }
    /**
     public String getDate(){
     return mDate;
     }*/

    /**
     * Kembalikan waktu gempa.
     */

}

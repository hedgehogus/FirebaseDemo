package com.sannacode.android.firebasedemo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ldenysiuk on 08.11.16.
 */

public class Model {

    private String reference;
    public String message;
    private int rating;
    private String author;
    private String time;

    public Model(){}

    public Model(String message, int rating, String author){
        this.message = message;
        this.rating = rating;
        this.author = author;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.time = "" + cal.get(Calendar.HOUR) + "." + cal.get(Calendar.MINUTE) + "." + cal.get(Calendar.SECOND);
    }


    public void setReference(String reference){
        this.reference = reference;
    }

    public String getReference (){
        return reference;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        } else if (obj instanceof Model) {
            return this.getReference().equals(((Model) obj).getReference());
        } else return false;

    }
}

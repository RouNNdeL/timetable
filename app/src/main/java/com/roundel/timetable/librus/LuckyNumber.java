package com.roundel.timetable.librus;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class LuckyNumber
{
    public static final String JSON_NUMBER = "LuckyNumber";
    public static final String JSON_ROOT = "LuckyNumber";
    public static final String JSON_DAY = "LuckyNumberDay";
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private Date date;
    private int number;

    public LuckyNumber(int number, Date date)
    {
        this.number = number;
        this.date = date;
    }

    public static LuckyNumber fromJSON(JSONObject jsonObject) throws JSONException, ParseException
    {
        JSONObject o = jsonObject.getJSONObject(JSON_ROOT);

        int number = o.getInt(JSON_NUMBER);
        Date date = DATE_FORMAT.parse(o.getString(JSON_DAY));

        return new LuckyNumber(number, date);
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
}

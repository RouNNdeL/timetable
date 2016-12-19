package com.roundel.timetable.librus;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Krzysiek on 2016-12-19.
 */
public class Lesson
{
    public static final String JSON_FROM = "RawFrom";
    public static final String JSON_TO = "RawTo";

    private int timeFrom;
    private int timeTo;

    public Lesson(int timeFrom, int timeTo)
    {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public static Lesson fromJSON(JSONObject jsonObject) throws JSONException
    {
        int from = jsonObject.getInt(JSON_FROM);
        int to = jsonObject.getInt(JSON_TO);
        return new Lesson(from, to);
    }

    public int getTimeFrom()
    {
        return timeFrom;
    }

    public void setTimeFrom(int timeFrom)
    {
        this.timeFrom = timeFrom;
    }

    public int getTimeTo()
    {
        return timeTo;
    }

    public void setTimeTo(int timeTo)
    {
        this.timeTo = timeTo;
    }
}
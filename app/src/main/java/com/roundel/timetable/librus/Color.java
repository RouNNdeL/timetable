package com.roundel.timetable.librus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2016-12-19.
 */
public class Color
{
    public static final String JSON_ROOT = "Colors";
    public static final String JSON_ID = "Id";
    public static final String JSON_NAME = "Name";
    public static final String JSON_VALUE = "RGB";

    public static final String TAG = "Color";

    private int id;
    private String name;
    private int value;

    public Color(int id, String name, int value)
    {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public static Color fromJSON(JSONObject jsonObject) throws JSONException
    {
        int id = jsonObject.getInt(JSON_ID);
        String name = jsonObject.getString(JSON_NAME);
        int value = Integer.parseInt(jsonObject.getString(JSON_VALUE), 16);

        return new Color(id, name, value);
    }

    public static List<Color> listFromJSON(JSONObject jsonObject) throws JSONException
    {
        List<Color> list = new ArrayList<>();
        JSONArray array = jsonObject.getJSONArray(JSON_ROOT);
        for(int i = 0; i < array.length(); i++)
        {
            list.add(Color.fromJSON(array.getJSONObject(i)));
        }

        return list;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
}

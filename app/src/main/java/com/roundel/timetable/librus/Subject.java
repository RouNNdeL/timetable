package com.roundel.timetable.librus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2016-12-19.
 */
public class Subject
{
    public static final String JSON_ROOT = "Subject";
    public static final String JSON_ID = "Id";
    public static final String JSON_NAME = "Name";
    public static final String JSON_NUMBER = "No";
    public static final String JSON_SHORT_NAME = "Short";
    public static final String JSON_IS_EXTRA = "IsExtracurricular";
    public static final String JSON_IS_BLOCK = "IsBlockLesson";

    public static final String TAG = "Subject";

    private int id;
    private String name;
    private int number;
    private String shortName;
    private boolean isExtracurricular;
    private boolean isBlockLesson;

    public Subject(int id, String name, int number, String shortName, boolean isExtracurricular, boolean isBlockLesson)
    {
        this.id = id;
        this.name = name;
        this.number = number;
        this.shortName = shortName;
        this.isExtracurricular = isExtracurricular;
        this.isBlockLesson = isBlockLesson;
    }

    public static Subject fromJSON(JSONObject jsonObject) throws JSONException
    {
        int id = jsonObject.getInt(JSON_ID);
        String name = jsonObject.getString(JSON_NAME);
        int number = jsonObject.getInt(JSON_NUMBER);
        String shortName = jsonObject.getString(JSON_SHORT_NAME);
        boolean isExtra = jsonObject.getBoolean(JSON_IS_EXTRA);
        boolean isBlock = jsonObject.getBoolean(JSON_IS_BLOCK);

        return new Subject(id, name, number, shortName, isExtra, isBlock);
    }

    public static List<Subject> listFromJSON(JSONObject jsonObject) throws JSONException
    {
        List<Subject> list = new ArrayList<>();
        JSONArray array = jsonObject.getJSONArray(JSON_ROOT);

        for(int i = 0; i < array.length(); i++)
        {
            list.add(Subject.fromJSON(array.getJSONObject(i)));
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

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public boolean isExtracurricular()
    {
        return isExtracurricular;
    }

    public void setExtracurricular(boolean extracurricular)
    {
        isExtracurricular = extracurricular;
    }

    public boolean isBlockLesson()
    {
        return isBlockLesson;
    }

    public void setBlockLesson(boolean blockLesson)
    {
        isBlockLesson = blockLesson;
    }
}

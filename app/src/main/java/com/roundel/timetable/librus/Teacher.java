package com.roundel.timetable.librus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2016-12-19.
 */
public class Teacher extends User
{
    public static final String JSON_ROOT = "Users";
    public static final String JSON_ID = "Id";
    public static final String JSON_FIRST_NAME = "FirstName";
    public static final String JSON_LAST_NAME = "LastName";

    public static final String TAG = "Teacher";

    private String displayName;

    public Teacher(int id, String firstName, String lastName, String displayName)
    {
        super(id, firstName, lastName);
        this.displayName = displayName;
    }

    public static Teacher fromJSON(JSONObject jsonObject) throws JSONException
    {
        int id = jsonObject.getInt(JSON_ID);
        String firstName = jsonObject.getString(JSON_FIRST_NAME);
        String lastName = jsonObject.getString(JSON_FIRST_NAME);
        String displayName = firstName + " " + lastName;

        return new Teacher(id, firstName, lastName, displayName);
    }

    public static List<Teacher> listFromTJSON(JSONObject jsonObject) throws JSONException
    {
        List<Teacher> list = new ArrayList<>();
        JSONArray array = jsonObject.getJSONArray(JSON_ROOT);
        for(int i = 0; i < array.length(); i++)
        {
            list.add(Teacher.fromJSON(array.getJSONObject(i)));
        }

        return list;
    }
}

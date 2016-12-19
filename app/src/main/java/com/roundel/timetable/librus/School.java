package com.roundel.timetable.librus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2016-12-19.
 */
public class School
{
    public static final String JSON_ROOT = "School";
    public static final String JSON_ID = "Id";
    public static final String JSON_NAME = "Name";
    public static final String JSON_TOWN = "Town";
    public static final String JSON_SCHOOL_YEAR = "SchoolYear";
    public static final String JSON_LESSONS_RANGE = "LessonsRange";

    public static final String TAG = "School";

    private int id;
    private String name;
    private String town;
    private int schoolYear;
    private List<Lesson> lessonsRange;

    public School(int id, String name, String town, int schoolYear, List<Lesson> lessonsRange)
    {
        this.id = id;
        this.name = name;
        this.town = town;
        this.schoolYear = schoolYear;
        this.lessonsRange = lessonsRange;
    }

    public static School fromJSON(JSONObject jsonObject) throws JSONException
    {
        JSONObject o = jsonObject.getJSONObject(JSON_ROOT);
        int id = o.getInt(JSON_ID);
        String name = o.getString(JSON_NAME);
        String town = o.getString(JSON_TOWN);
        int year = o.getInt(JSON_SCHOOL_YEAR);
        List<Lesson> lessons = new ArrayList<>();
        JSONArray array = jsonObject.getJSONArray(JSON_LESSONS_RANGE);

        for(int i = 0; i < array.length(); i++)
        {
            lessons.add(Lesson.fromJSON(array.getJSONObject(i)));
        }

        return new School(id, name, town, year, lessons);
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

    public String getTown()
    {
        return town;
    }

    public void setTown(String town)
    {
        this.town = town;
    }

    public int getSchoolYear()
    {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear)
    {
        this.schoolYear = schoolYear;
    }

    public List<Lesson> getLessonRange()
    {
        return lessonsRange;
    }

    public void addLesson(int timeFrom, int timeTo)
    {
        lessonsRange.add(new Lesson(timeFrom, timeTo));
    }

    public void removeLesson(int position)
    {
        lessonsRange.remove(position);
    }

    public void removeLesson(Lesson lesson)
    {
        lessonsRange.remove(lesson);
    }

    public Lesson getLesson(int position)
    {
        return lessonsRange.get(position);
    }
}

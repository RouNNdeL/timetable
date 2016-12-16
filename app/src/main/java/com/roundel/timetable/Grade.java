package com.roundel.timetable;

import java.util.Date;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class Grade
{
    private String grade;
    private Date date;
    private int weight;
    private int type;

    public Grade(String grade, Date date, int weight, int type)
    {
        this.grade = grade;
        this.date = date;
        this.weight = weight;
        this.type = type;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}

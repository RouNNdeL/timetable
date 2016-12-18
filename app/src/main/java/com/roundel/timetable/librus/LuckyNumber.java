package com.roundel.timetable.librus;

import java.util.Date;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class LuckyNumber
{
    private Date date;
    private int number;

    public LuckyNumber(int number, Date date)
    {
        this.number = number;
        this.date = date;
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

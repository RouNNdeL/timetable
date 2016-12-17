package com.roundel.timetable.items;

import java.util.List;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class GradeGroup
{
    public static final int ITEM_TYPE_LUCKY_NUMBER = 100;
    public static final int ITEM_TYPE_GRADE = 101;
    public static final int ITEM_TYPE_ANNOUNCEMENT = 102;

    private List<Grade> grades;
    private int subject;

    public GradeGroup(List<Grade> grades, int subject)
    {
        this.grades = grades;
        this.subject = subject;
    }

    public GradeGroup()
    {
    }

    public List<Grade> getGrades()
    {
        return grades;
    }

    public void setGrades(List<Grade> grades)
    {
        this.grades = grades;
    }

    public int getSubject()
    {
        return subject;
    }

    public void setSubject(int subject)
    {
        this.subject = subject;
    }
}

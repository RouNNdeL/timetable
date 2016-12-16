package com.roundel.timetable;

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
    private String subject;

    public GradeGroup(List<Grade> grades, String subject)
    {
        this.grades = grades;
        this.subject = subject;
    }

    public GradeGroup()
    {
    }
}

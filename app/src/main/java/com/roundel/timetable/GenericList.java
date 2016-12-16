package com.roundel.timetable;

import java.util.ArrayList;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class GenericList <T> extends ArrayList<T>
{
    private Class<T> genericType;

    public GenericList(Class<T> c)
    {
        this.genericType = c;
    }

    public Class<T> getGenericType()
    {
        return genericType;
    }
}
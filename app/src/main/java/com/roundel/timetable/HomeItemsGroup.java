package com.roundel.timetable;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class HomeItemsGroup extends ArrayList
{
    public static final int ITEM_TYPE_GRADE_GROUP = 200;
    public static final int ITEM_TYPE_LUCKY_NUMBER = 201;

    public HomeItemsGroup()
    {
    }

    public int getItemType(int position)
    {
        Object item = this.get(position);
        String className = item.getClass().getName();

        Log.d("TEST", className);

        if(item instanceof LuckyNumber)
        {
            return ITEM_TYPE_LUCKY_NUMBER;
        }

        if(item instanceof GradeGroup)
        {
            return ITEM_TYPE_GRADE_GROUP;
        }

        return -1;
    }
}

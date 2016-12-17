package com.roundel.timetable.items;

import java.util.ArrayList;

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

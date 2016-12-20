package com.roundel.timetable.librus;

import com.roundel.timetable.LoadingProgress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class HomeItemsGroup extends ArrayList<Object>
{
    public static final int ITEM_TYPE_GRADE_GROUP = 200;
    public static final int ITEM_TYPE_LUCKY_NUMBER = 201;
    public static final int ITEM_LOADING_PROGRESS = 299;
    private static final Comparator<Object> COMPARATOR_PROGRESS_LAST = new Comparator<Object>()
    {

        @Override
        public int compare(Object entity1, Object entity2)
        {
            int type1 = getItemType(entity1);
            int type2 = getItemType(entity2);

            if(type1 != ITEM_LOADING_PROGRESS && type2 != ITEM_LOADING_PROGRESS)
                return 0;
            else if(type1 == ITEM_LOADING_PROGRESS)
                return 1;
            else
                return -1;
        }
    };

    public HomeItemsGroup()
    {
    }

    private static int getItemType(Object item)
    {
        if(item instanceof LuckyNumber)
        {
            return ITEM_TYPE_LUCKY_NUMBER;
        }

        if(item instanceof GradeGroup)
        {
            return ITEM_TYPE_GRADE_GROUP;
        }

        if(item instanceof LoadingProgress)
        {
            return ITEM_LOADING_PROGRESS;
        }

        return -1;
    }

    @Override
    public boolean add(Object o)
    {
        if(super.add(o))
        {
            Collections.sort(this, COMPARATOR_PROGRESS_LAST);
            return true;
        }
        else
            return false;

    }

    public int getItemType(int position)
    {
        Object item = this.get(position);

        return getItemType(item);
    }

    public boolean removeProgress()
    {
        if(getItemType(get(size() - 1)) == ITEM_LOADING_PROGRESS)
        {
            remove(size() - 1);
            return true;
        }
        else
        {
            return false;
        }
    }

}

package com.roundel.timetable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class HomeItemsGroup
{
    public static final int ITEM_TYPE_GRADE_GROUP = 200;
    public static final int ITEM_TYPE_LUCKY_NUMBER = 201;

    private GenericList<GradeGroup> gradeGroupList = new GenericList<>(GradeGroup.class);
    private LuckyNumber luckyNumber;

    public HomeItemsGroup()
    {
    }

    public Object getItemAtPosition(int position)
    {
        if(position == 0 && luckyNumber != null)
            return luckyNumber;
        else if(luckyNumber != null)
            position -= 1;

        if(gradeGroupList != null && position < gradeGroupList.size())
            return gradeGroupList.get(position);
        if(gradeGroupList != null)
            position -= gradeGroupList.size();

        return null;
    }

    public int getItemCount()
    {
        int count = 0;

        if(luckyNumber != null)
            count+=1;
        if(gradeGroupList != null)
            count+=gradeGroupList.size();

        return count;
    }

    public int getItemType(int position)
    {
        Object item = getItemAtPosition(position);

        if(item instanceof LuckyNumber)
            return ITEM_TYPE_LUCKY_NUMBER;

        //An ugly check for item Generic Class
        if(item instanceof GenericList && ((GenericList<?>) item).getGenericType() == GradeGroup.class)
            return ITEM_TYPE_GRADE_GROUP;

        return -1;
    }

    public LuckyNumber getLuckyNumber()
    {
        return luckyNumber;
    }

    public void setLuckyNumber(LuckyNumber luckyNumber)
    {
        this.luckyNumber = luckyNumber;
    }

    public List<GradeGroup> getGradeGroupList()
    {
        return gradeGroupList;
    }

    public void setGradeGroupList(GenericList<GradeGroup> gradeGroupList)
    {
        this.gradeGroupList = gradeGroupList;
    }
}

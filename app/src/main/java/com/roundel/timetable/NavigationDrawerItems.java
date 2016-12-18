package com.roundel.timetable;

import java.util.ArrayList;

/**
 * Created by Krzysiek on 2016-12-18.
 */
public class NavigationDrawerItems extends ArrayList<NavigationDrawerItem>
{
    public static final String TAG = "NavigationDrawerItems";

    private int enabled = -1;

    public int getEnabled()
    {
        return this.enabled;
    }

    public void setEnabled(int position) throws IndexOutOfBoundsException
    {
        if(this.size() <= position)
            throw new IndexOutOfBoundsException("Position not in list");
        this.enabled = position;
    }
}

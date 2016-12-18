package com.roundel.timetable;

import android.graphics.drawable.Drawable;

/**
 * Created by Krzysiek on 2016-12-18.
 */

public class NavigationDrawerItem
{
    public static final int TYPE_ITEM = 400;
    public static final int TYPE_SUB_HEADER = 401;
    public static final int TYPE_DIVIDER = 402;

    private int type;
    private String text;
    private Drawable icon;

    public NavigationDrawerItem(int type, String text, Drawable icon)
    {
        this.type = type;
        this.text = text;
        this.icon = icon;
    }

    public NavigationDrawerItem(int type, String text)
    {
        this(type, text, null);
    }

    public NavigationDrawerItem(int type)
    {
        this(type, null);
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Drawable getIcon()
    {
        return icon;
    }

    public void setIcon(Drawable icon)
    {
        this.icon = icon;
    }
}

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
    public static final int TYPE_EMPTY_SPACE = 403;
    /*
    TYPE_EMPTY_SPACE:

    Add 8dp padding at the top and bottom of every list grouping.
     One exception is at the top of a list with a subheader, because subheaders contain their own padding.
     */

    public static final int ID_HOME = 500;
    public static final int ID_CALENDAR = 501;
    public static final int ID_GRADES = 502;
    public static final int ID_ANNOUNCEMENTS = 503;
    public static final int ID_TIMETABLE = 504;
    public static final int ID_CONTACT = 505;
    public static final int ID_SETTINGS = 506;

    private int type;
    private int id;
    private boolean canEnable;
    private String text;
    private Drawable icon;

    public NavigationDrawerItem(int type, int id, String text, Drawable icon, boolean canEnable)
    {
        this.type = type;
        this.id = id;
        this.text = text;
        this.icon = icon;
        this.canEnable = canEnable;
    }

    public NavigationDrawerItem(int type, String text)
    {
        this(type, -1, text, null, false);
    }

    public NavigationDrawerItem(int type)
    {
        this(type, null);
    }

    public boolean canEnable()
    {
        return canEnable;
    }

    public void setCanEnable(boolean canEnable)
    {
        this.canEnable = canEnable;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

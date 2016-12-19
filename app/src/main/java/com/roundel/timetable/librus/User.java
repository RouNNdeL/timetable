package com.roundel.timetable.librus;

/**
 * Created by Krzysiek on 2016-12-19.
 */
public class User
{
    public static final String TAG = "User";

    private int id;
    private String firstName;
    private String lastName;

    public User(int id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}

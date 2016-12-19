package com.roundel.timetable.librus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Krzysiek on 2016-12-19.
 */
public class Me extends User
{
    public static final String JSON_ROOT = "Me";
    public static final String JSON_ACCOUNT = "Account";
    public static final String JSON_CLASS = "Class";
    public static final String JSON_ID = "UserId";
    public static final String JSON_FIRST_NAME = "FirstName";
    public static final String JSON_LAST_NAME = "LastName";
    public static final String JSON_EMAIL = "Email";
    public static final String JSON_GROUP_ID = "GroupId";
    public static final String JSON_IS_ACTIVE = "IsActive";
    public static final String JSON_LOGIN = "Login";
    public static final String JSON_IS_PREMIUM = "IsPremium";
    public static final String JSON_EXPIRED_PREMIUM_DATE = "ExpiredPremiumDate";
    public static final String JSON_REFRESH = "Refresh";
    public static final String JSON_CLASS_ID = "Id";

    public static final String TAG = "Me";

    private String email;
    private int groupId;
    private boolean isActive;
    private String login;
    private boolean isPremium;
    private Date expiredPremiumDate;
    private int refresh;
    private Class aClass;

    public Me(int id, String firstName, String lastName,
              String email, int groupId,
              boolean isActive, String login,
              boolean isPremium, Date expiredPremiumDate,
              int refresh, int class_id)
    {
        super(id, firstName, lastName);
        this.email = email;
        this.groupId = groupId;
        this.isActive = isActive;
        this.login = login;
        this.isPremium = isPremium;
        this.expiredPremiumDate = expiredPremiumDate;
        this.refresh = refresh;
        this.aClass = new Class(class_id);
    }

    public static Me fromJSON(JSONObject jsonObject) throws JSONException
    {
        JSONObject root = jsonObject.getJSONObject(JSON_ROOT);
        JSONObject account = root.getJSONObject(JSON_ACCOUNT);
        JSONObject aClass = root.getJSONObject(JSON_CLASS);

        int id = account.getInt(JSON_ID);
        String firstName = account.getString(JSON_FIRST_NAME);
        String lastName = account.getString(JSON_LAST_NAME);
        String email = account.getString(JSON_EMAIL);
        int groupId = account.getInt(JSON_GROUP_ID);
        boolean isActive = account.getBoolean(JSON_IS_ACTIVE);
        String login = account.getString(JSON_LOGIN);
        boolean isPremium = account.getBoolean(JSON_IS_PREMIUM);
        Date expiredPremiumDate = new Date(account.getInt(JSON_EXPIRED_PREMIUM_DATE));
        int refresh = root.getInt(JSON_REFRESH);
        int class_id = aClass.getInt(JSON_CLASS_ID);

        return new Me(id, firstName, lastName, email, groupId, isActive, login, isPremium, expiredPremiumDate, refresh, class_id);
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getGroupId()
    {
        return groupId;
    }

    public void setGroupId(int groupId)
    {
        this.groupId = groupId;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public boolean isPremium()
    {
        return isPremium;
    }

    public void setPremium(boolean premium)
    {
        isPremium = premium;
    }

    public Date getExpiredPremiumDate()
    {
        return expiredPremiumDate;
    }

    public void setExpiredPremiumDate(Date expiredPremiumDate)
    {
        this.expiredPremiumDate = expiredPremiumDate;
    }

    public int getRefresh()
    {
        return refresh;
    }

    public void setRefresh(int refresh)
    {
        this.refresh = refresh;
    }

    public Class getaClass()
    {
        return aClass;
    }

    public void setaClass(Class aClass)
    {
        this.aClass = aClass;
    }

    private class Class
    {
        int id;

        public Class(int id)
        {

            this.id = id;
        }

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }
    }
}

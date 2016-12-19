package com.roundel.timetable.api;

import android.content.Context;

import com.roundel.timetable.librus.LuckyNumber;
import com.roundel.timetable.librus.Me;

/**
 * Created by Krzysiek on 2016-12-18.
 */

public class LibrusClient
{
    public static int DATA_LUCKY_NUMBER = 300;
    public static int DATA_GRADES = 301;
    public static int DATA_ME = 302;
    public static int DATA_ANNOUNCEMENTS = 303;

    private String token;
    private String tokenType;
    private Context mContext;

    public LibrusClient(Context context, String token, String tokenType)
    {
        this.mContext = context;
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public void fetchLuckyNumber(final LuckyNumberResponseListener listener)
    {
        LuckyNumberTask task = new LuckyNumberTask(this.mContext, new LuckyNumberTask.LuckyNumberResponse()
        {
            @Override
            public void onStart()
            {
                listener.onStart();
            }

            @Override
            public void onSuccess(LuckyNumber result)
            {
                listener.onSuccess(result);
            }

            @Override
            public void onFailure(APIException e)
            {
                listener.onFailure(e);
            }
        });
        task.execute(this.token, this.tokenType);
    }

    public void fetchMe(final MeResponseListener listener)
    {
        MeTask task = new MeTask(this.mContext, new MeTask.MeResponseListener()
        {
            @Override
            public void onStart()
            {
                listener.onStart();
            }

            @Override
            public void onSuccess(Me result)
            {
                listener.onSuccess(result);
            }

            @Override
            public void onFailure(APIException e)
            {
                listener.onFailure(e);
            }
        });
        task.execute(this.token, this.tokenType);
    }

    public interface LuckyNumberResponseListener
    {
        void onStart();

        void onSuccess(LuckyNumber luckyNumber);

        void onFailure(APIException exception);
    }

    public interface MeResponseListener
    {
        void onStart();

        void onSuccess(Me m);

        void onFailure(APIException exception);
    }
}

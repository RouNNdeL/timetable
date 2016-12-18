package com.roundel.timetable.api;

import android.content.Context;
import android.util.Log;

import com.roundel.timetable.items.LuckyNumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    private DateFormat luckNumberDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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
        LuckyNumberTask task = new LuckyNumberTask(this.mContext, new LuckyNumberTask.GetLuckyNumberResponse()
        {
            @Override
            public void onStart()
            {
                listener.onStart();
            }

            @Override
            public void onSuccess(JSONObject result)
            {
                try
                {
                    JSONObject info = result.getJSONObject(LuckyNumberTask.JSON_LUCKY_NUMBER_ROOT);
                    int luckyNumberNumber = info.getInt(LuckyNumberTask.JSON_LUCKY_NUMBER);
                    String luckyNumberDay = info.getString(LuckyNumberTask.JSON_LUCKY_NUMBER_DAY);
                    final LuckyNumber luckyNumber = new LuckyNumber(luckyNumberNumber, luckNumberDateFormat.parse(luckyNumberDay));
                    listener.onSuccess(luckyNumber);
                }
                catch(JSONException | ParseException e)
                {
                    APIException exception = new APIException(
                            APIException.PARSE_JSON,
                            APIException.PARSE_JSON_MESSAGE,
                            e.getMessage(),
                            this.getClass().getSimpleName()
                    );
                    listener.onFailure(exception);
                }
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
}

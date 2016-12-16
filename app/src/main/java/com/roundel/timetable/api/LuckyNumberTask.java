package com.roundel.timetable.api;

import android.content.Context;
import android.os.AsyncTask;

import com.roundel.timetable.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.roundel.timetable.api.APISupport.request;

/**
 * Created by Krzysiek on 2016-12-14.
 */

public class LuckyNumberTask extends AsyncTask<String, String, JSONObject>
{
    public static final String JSON_LUCKY_NUMBER = "LuckyNumber";
    public static final String JSON_LUCKY_NUMBER_ROOT = "LuckyNumber";
    public static final String JSON_LUCKY_NUMBER_DAY = "LuckyNumberDay";

    private LuckyNumberTask.GetLuckyNumberResponse mListener;
    private Context mContext;
    private final String TAG = getClass().getSimpleName();

    private APIException exception = null;

    public LuckyNumberTask(Context context, LuckyNumberTask.GetLuckyNumberResponse listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        mListener.onStart();
    }

    @Override
    protected JSONObject doInBackground(String... strings)
    {
        String token = strings[0] == null ? "": strings[0];
        String auth_type = strings[1] == null ? "": strings[1];
        JSONObject params = new JSONObject();
        JSONObject headers = new JSONObject();
        String url = mContext.getString(R.string.api_base_url)+mContext.getString(R.string.api_luck_numbers);

        try
        {
            headers.put("Authorization", auth_type+" " + token);
            headers.put("User-Agent", mContext.getString(R.string.api_user_agent));
            headers.put("Content-Type", mContext.getString(R.string.api_content_type_json));
            headers.put("Accept-Encoding", mContext.getString(R.string.api_accept_encoding));
            headers.put("Accept-Language", mContext.getString(R.string.api_accept_lang));
            headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
            headers.put("Connection", "keep-alive");
            headers.put("X-Librus-Client-Id", "35");
            headers.put("Cookie", mContext.getString(R.string.api_cookie));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        String response = request(url, "GET", params, headers);
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject = new JSONObject(response);
        }
        catch(JSONException e)
        {
            exception = new APIException(
                    APIException.PARSE_JSON,
                    APIException.PARSE_JSON_MESSAGE,
                    response,
                    this.getClass().getName()
            );
        }
        try
        {
            if(!jsonObject.has(JSON_LUCKY_NUMBER_ROOT) ||
                    !jsonObject.getJSONObject(JSON_LUCKY_NUMBER_ROOT).has(JSON_LUCKY_NUMBER) ||
                    !jsonObject.getJSONObject(JSON_LUCKY_NUMBER_ROOT).has(JSON_LUCKY_NUMBER_DAY))
            {
                exception = new APIException(
                        APIException.UNKNOWN_RESPONSE,
                        APIException.UNKNOWN_RESPONSE_MESSAGE,
                        response,
                        this.getClass().getName()
                );
            }
        }
        catch(JSONException e)
        {
            exception = new APIException(
                    APIException.UNKNOWN_RESPONSE,
                    APIException.UNKNOWN_RESPONSE_MESSAGE,
                    response,
                    this.getClass().getName()
            );
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject s)
    {
        if(exception == null)
            mListener.onSuccess(s);
        else
            mListener.onFailure(exception);
    }

    public interface GetLuckyNumberResponse
    {
        void onStart();

        void onSuccess(JSONObject result);

        void onFailure(APIException exception);
    }
}

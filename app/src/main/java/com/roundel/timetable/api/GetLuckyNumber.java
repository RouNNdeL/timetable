package com.roundel.timetable.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.roundel.timetable.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.roundel.timetable.api.APISupport.request;

/**
 * Created by Krzysiek on 2016-12-14.
 */

public class GetLuckyNumber extends AsyncTask<String, String, String>
{
    private GetLuckyNumber.GetLuckyNumberResponse mListener;
    private Context mContext;
    public final String TAG = getClass().getSimpleName();

    public GetLuckyNumber(Context context, GetLuckyNumber.GetLuckyNumberResponse listener)
    {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        mListener.onTaskStart();
    }

    @Override
    protected String doInBackground(String... strings)
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
        Log.d(TAG, url);
        String response = request(url, "GET", params, headers);
        Log.d(TAG, response);
        return response;
    }

    @Override
    protected void onPostExecute(String s)
    {
        mListener.onTaskEnd(s);
    }

    public interface GetLuckyNumberResponse
    {
        void onTaskStart();

        void onTaskEnd(String result);
    }
}

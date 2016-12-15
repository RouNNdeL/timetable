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

public class GetOAuthToken extends AsyncTask<String, String, String>
{
    private GetOAuthTokenResponse mListener;
    private Context mContext;
    public final String TAG = getClass().getSimpleName();

    public GetOAuthToken(Context context, GetOAuthTokenResponse listener)
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
        String username = strings[0] == null ? "": strings[0];
        String password = strings[1] == null ? "": strings[1];
        JSONObject params = new JSONObject();
        JSONObject headers = new JSONObject();
        String url = mContext.getString(R.string.api_base_url)+mContext.getString(R.string.api_oauth_token);

        try
        {
            params.put("grant_type", "password");
            params.put("username", username);
            params.put("password", password);
            params.put("librus_long_term_token", "1");

            headers.put("Authorization", "Basic " + mContext.getString(R.string.api_token));
            headers.put("User-Agent", mContext.getString(R.string.api_user_agent));
            headers.put("Content-Type", mContext.getString(R.string.api_content_type_urlencoded));
            headers.put("Accept-Encoding", mContext.getString(R.string.api_accept_encoding));
            headers.put("Accept-Language", mContext.getString(R.string.api_accept_lang));
            headers.put("Accept", "*/*");
            headers.put("Origin", "file://");
            headers.put("Connection", "keep-alive");
            headers.put("Cookie", mContext.getString(R.string.api_cookie));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, url);
        String response = request(url, "POST", params, headers);
        Log.d(TAG, response);
        return response;
    }

    @Override
    protected void onPostExecute(String s)
    {
        mListener.onTaskEnd(s);
    }

    public interface GetOAuthTokenResponse
    {
        void onTaskStart();

        void onTaskEnd(String result);
    }
}

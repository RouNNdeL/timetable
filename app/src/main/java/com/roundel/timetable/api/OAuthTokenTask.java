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

public class OAuthTokenTask extends AsyncTask<String, String, JSONObject>
{
    public static final String JSON_TOKEN = "access_token";
    public static final String JSON_TOKEN_TYPE = "token_type";

    private GetOAuthTokenResponse mListener;
    private Context mContext;
    public final String TAG = getClass().getSimpleName();

    private APIException exception = null;

    public OAuthTokenTask(Context context, GetOAuthTokenResponse listener)
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
        if(!jsonObject.has(JSON_TOKEN) || !jsonObject.has(JSON_TOKEN_TYPE))
        {
            exception = new APIException(
                    APIException.INVALID_PASSWORD,
                    APIException.INVALID_PASSWORD_MESSAGE,
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

    public interface GetOAuthTokenResponse
    {
        void onStart();

        void onSuccess(JSONObject result);

        void onFailure(APIException e);
    }
}

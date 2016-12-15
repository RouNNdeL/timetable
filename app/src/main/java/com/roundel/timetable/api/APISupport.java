package com.roundel.timetable.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by Krzysiek on 2016-12-14.
 */

public class APISupport
{
    public final static String TAG = "APISupport";

    public static String request(String url, String method, JSONObject params, JSONObject headers)
    {
        String result = "";
        try
        {
            URL urlEncoded = new URL("https://" + url);

            HttpURLConnection urlConnection = (HttpURLConnection) urlEncoded.openConnection();

            urlConnection.setRequestMethod(method);

            Iterator<String> keys = headers.keys();
            while(keys.hasNext())
            {
                String key = (String) keys.next();
                final Object value = headers.get(key);
                if(value instanceof String)
                {
                    urlConnection.addRequestProperty(key, (String) value);
                }
            }

            // Send POST data.
            if(Objects.equals(method.toUpperCase(), "POST"))
            {
                if(params.length() > 0 && Objects.equals(headers.get("Content-Type"), "application/json"))
                {
                    DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream());
                    printout.writeBytes(params.toString());
                    printout.flush();
                    printout.close();
                }
                else if(params.length() > 0 && Objects.equals(headers.get("Content-Type"), "application/x-www-form-urlencoded"))
                {
                    DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream());
                    printout.writeBytes(generateParams(params));
                    printout.flush();
                    printout.close();
                }
            }

            int HttpResponseCode = urlConnection.getResponseCode();
            switch(HttpResponseCode)
            {
                case HttpURLConnection.HTTP_OK:
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    result = br1.readLine();
                    break;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                    result = br2.readLine();
                    break;
                default:
                    Log.d(TAG, String.valueOf(HttpResponseCode));
                    break;
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static String generateUrlWithParams(String url, JSONObject params) throws JSONException, UnsupportedEncodingException
    {
        if(params.length() <= 0)
            return url;
        url += "?";
        url += generateParams(params);
        return url;
    }

    public static String generateParams(JSONObject params) throws JSONException, UnsupportedEncodingException
    {
        String result = "";
        Iterator<String> keys = params.keys();
        while(keys.hasNext())
        {
            String key = (String) keys.next();
            final Object value = params.get(key);

            if(value instanceof String)
                result += key + "=" + URLEncoder.encode((String) value, "UTF-8");

            if(keys.hasNext())
                result += "&";
        }
        return result;
    }
}

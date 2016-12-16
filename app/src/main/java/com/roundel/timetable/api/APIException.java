package com.roundel.timetable.api;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import java.util.Locale;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class APIException extends Exception
{
    public static final int PARSE_JSON = 10;
    public static final int UNKNOWN_RESPONSE = 11;
    public static final int INVALID_PASSWORD = 12;

    public static final String UNKNOWN_RESPONSE_MESSAGE = "ERROR_UNKNOWN_RESPONSE";
    public static final String PARSE_JSON_MESSAGE = "ERROR_PARSE_JSON";
    public static final String INVALID_PASSWORD_MESSAGE = "ERROR_INVALID_PASSWORD";
    public static final String UNKNOWN_ERROR_MESSAGE = "UNNOWN_ERROR";

    private int code;
    private String message;
    private String content;
    private String context;

    public APIException(int code, String message, String content, String context)
    {
        this.code = code;
        this.message = message;
        this.content = content;
        this.context = context;
    }
    public APIException(int code, String message, String context)
    {
        this(code, message, "", context);
    }
    public APIException(int code, String context)
    {
        this(code, UNKNOWN_ERROR_MESSAGE, context);
    }

    public static void displayErrorToUser(APIException e, final Context context)
    {
        final int code =  e.getCode();
        final String message = e.getMessage();
        final String content = e.getContent();
        final String err_context = e.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("An error has occurred");
        builder.setMessage("Please send an email to the developer, so that the issue can be fixed as fast as possible.");
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String body = String.format(Locale.ENGLISH,
                        "Error code: %d\n\nError message: %s\n\nError content: %s\n\nError err_context: %s", code, message, content, err_context);
                Intent sendIntent = new Intent();
                sendIntent.setData(Uri.parse("mailto:rounndel@gmail.com"));
                sendIntent.setAction(Intent.ACTION_SENDTO);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Timetable error");
                sendIntent.putExtra(Intent.EXTRA_TEXT, body);
                context.startActivity(Intent.createChooser(sendIntent, "Send email"));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContext()
    {
        return context;
    }

    public void setContext(String context)
    {
        this.context = context;
    }
}

package com.roundel.timetable.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.roundel.timetable.R;
import com.roundel.timetable.api.GetOAuthToken;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends Activity
{

    private final String TAG = getClass().getSimpleName();
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private GetOAuthToken mAuthTask = null;

    // UI references.
    private TextInputEditText mEmailView;
    private TextInputLayout mEmailContainer;
    private TextInputEditText mPasswordView;
    private TextInputLayout mPasswordContainer;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (TextInputEditText) findViewById(R.id.email);
        mEmailContainer = (TextInputLayout) findViewById(R.id.email_container);

        mPasswordView = (TextInputEditText) findViewById(R.id.password);
        mPasswordContainer = (TextInputLayout) findViewById(R.id.password_container);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if(id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {
        if(mAuthTask != null)
        {
            return;
        }

        // Reset errors.
        mEmailContainer.setErrorEnabled(false);
        mPasswordContainer.setErrorEnabled(false);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(!TextUtils.isEmpty(password) && !isPasswordValid(password))
        {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if(TextUtils.isEmpty(email))
        {
            mEmailContainer.setErrorEnabled(true);
            mEmailContainer.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if(TextUtils.isEmpty(password))
        {
            mPasswordContainer.setErrorEnabled(true);
            mPasswordContainer.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new GetOAuthToken(this, new GetOAuthToken.GetOAuthTokenResponse()
            {
                @Override
                public void onTaskStart()
                {
                    Log.d(TAG, "Task started");
                }

                @Override
                public void onTaskEnd(String result)
                {
                    showProgress(false);
                    final Context applicationContext = getApplicationContext();
                    try
                    {
                        JSONObject json = new JSONObject(result);
                        if(json.has("access_token") && json.has("token_type"))
                        {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(applicationContext.getString(R.string.preference_token), (String) json.get("access_token"));
                            editor.putString(applicationContext.getString(R.string.preference_auth_type), (String) json.get("token_type"));
                            editor.putBoolean(applicationContext.getString(R.string.preference_logged_in), true);
                            editor.apply();
                            Intent intent = new Intent(applicationContext, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            mPasswordContainer.setErrorEnabled(true);
                            mPasswordContainer.setError(getString(R.string.error_incorrect_password));
                        }
                    }
                    catch(JSONException | ClassCastException e)
                    {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "Task finished: "+result);
                }
            });
            mAuthTask.execute(email, password);
            mAuthTask = null;
        }
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        return password.length() > 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}


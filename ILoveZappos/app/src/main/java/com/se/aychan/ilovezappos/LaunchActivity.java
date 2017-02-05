package com.se.aychan.ilovezappos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/*
    @author Anthony Chan
    LaunchActivity provides splash screen for when users activate application
    Shows a logo & redirects to necessary Activity
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // programatically remove Activity Bar for this Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);
        // Execute LogoTask()
        new LogoTask().execute();
    }

    /**
     * Async Task redirects to new activity based on
     * shared preferences of user.
     */
    class LogoTask extends AsyncTask<String, String, String> {
        private Long sleepDuration = 1500L; //length of pause duration
        LogoTask()
        {
            //create instance of LogoTask()
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... result)
        {
            try {
                Thread.sleep(sleepDuration); // will pause screen on the logo for sleepDuration seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            //Determine which activity to go to based on shared preferences. for now just one option.
            Intent intent = new Intent(LaunchActivity.this, TestActivity.class);
            startActivity(intent);
            finish(); //remove this activity from activity stack, destroy
        }
    }

}

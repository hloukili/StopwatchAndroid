package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {

    // Création des variables pour calculer les secondes, et le boolean
    // pour vérifier l'état de l'application.
    private int seconds = 0;
    private boolean running;
    //Record the stopwatch running before the onStop();
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

       //Restore the activity's state by getting values from the bundle.
        if(savedInstanceState !=null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            //Getting value from running before the onStop();
            wasRunning =  savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

    }

    /*
    @Override
    protected void onStart()
    {
        super.onStart();

        // Running again if wasRunning value is true;
        if(wasRunning)
        {
            running = true;

        }
    } */

    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning)
        {
            running = true;
        }
    }

    // Save the state of the variables in this method.
    @Override
    protected  void onSaveInstanceState(Bundle savedInstanceState)
    {   super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        //Save the state of running before the onStop();
        savedInstanceState.putBoolean("wasRunning",wasRunning);

    }

    // Stop the activity but it still run in background.
    /*
    @Override
    protected  void onStop()
    {
        super.onStop();

        //Mettre les informations de running dans une nouvelle variable.
        wasRunning = running;
        running = false;
    }
    */

    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // Start the stopwatch running when the Start button is clicked.
    public void onClickStart (View view)
    {
        running = true;
    }

    // Stop the stopwatch running when the Stop Button is clicked.
    public void onClickStop(View view)
    {
        running = false;
    }

    // Reset the stopwatch running when the Reset button is clicked.
    public void onClickReset(View view)
    {
        running=false;
        seconds=0;
    }


    private void runTimer()
    {
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        // Creation du Handler
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours,minutes,secs);
                timeView.setText(time);

                if (running)
                {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });






    }

}

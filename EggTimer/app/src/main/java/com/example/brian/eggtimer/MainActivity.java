package com.example.brian.eggtimer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    SeekBar TimerBar;
    TextView textView;
    Boolean countdownIsActive = false;
    Button startButton;
    CountDownTimer countDownTimer;


    public void controlTimer(View view){

        if(countdownIsActive == false){

            countdownIsActive = true;
            TimerBar.setEnabled(false);
            startButton.setText("Stop");

        // Test Button
        // Log.i("Button Pressed", "Pressed");
            countDownTimer = new CountDownTimer(TimerBar.getProgress(),1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft((int) millisUntilFinished/1000);
                Log.i("Second ",String.valueOf(millisUntilFinished/1000));
                textView.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                Log.i("finished", "timer done");
                textView.setText("0:0");
                mediaPlayer.start();
            }
        }.start();
    }else{
            //Reset the Timer
            textView.setText("00:30");
            TimerBar.setEnabled(true);
            TimerBar.setProgress(60000);
            countDownTimer.cancel();
            startButton.setText("Go");
            countdownIsActive = false;
        }


    }

    public void timeLeft(int secondLeft){
        textView.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(secondLeft),
                TimeUnit.MILLISECONDS.toSeconds(secondLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(secondLeft))
        ));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.rooster);

        //Set Timer display on EditText
        textView = (TextView) findViewById(R.id.textView);

        //Set up Button
        startButton = (Button) findViewById(R.id.startButton);

        //Set up Timer SeekBar
        TimerBar = (SeekBar) findViewById(R.id.seekBar);

        //Set maximum of the TimerBar (10min)
        TimerBar.setMax(600000);

        //Set initial of the TimerBar (1min)
        TimerBar.setProgress(60000);

        //Check the user selected on TimerBar
        TimerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("SeekBar value: ", String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(progress),
                        TimeUnit.MILLISECONDS.toSeconds(progress) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progress))
                ));


                /*
                textView.setText(String.format("%d:%d",

                        TimeUnit.MILLISECONDS.toMinutes(progress),
                        TimeUnit.MILLISECONDS.toSeconds(progress) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progress))));
                */
                //pass the second to timeleft method
                timeLeft(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}

package com.example.brian.braintraining;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Layout GridLayout;
    TextView timetextView;
    TextView pointtextView;
    TextView sumtextView;
    TextView resulttextView;
    TextView finaltextView;
    CountDownTimer countDownTimer;
    Boolean countdownIsActive = false;
    Button startButton;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    int locationOfCorrectAnswer;
    int score = 0;
    int totalQuestion = 0;

    //Answer arraylist
    ArrayList<Integer> answerArray = new ArrayList<Integer>();

    //Generate Question
    public void generateQuestion(){
        //Random number generator
        Random rand = new Random();
        // two numbers between 0-20
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumtextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        //0-3 randomly generate the correct answer location
        locationOfCorrectAnswer = rand.nextInt(4);

        //clear the previous answer list
        answerArray.clear();

        int incorrectAnswer;

        for(int i = 0; i<4; i++){
            //add the correct answer(number) to the location
            if(i == locationOfCorrectAnswer){
                answerArray.add(a + b);
            }else{
                incorrectAnswer = rand.nextInt(41);

                //if the location is wrong but the correct answer(number) has load in, generate another number and compare it
                while (incorrectAnswer == a+b){
                    incorrectAnswer = rand.nextInt(41);
                }

                answerArray.add(incorrectAnswer);
            }
        }

        button1.setText(Integer.toString(answerArray.get(0)));
        button2.setText(Integer.toString(answerArray.get(1)));
        button3.setText(Integer.toString(answerArray.get(2)));
        button4.setText(Integer.toString(answerArray.get(3)));
    }

    //User interact the answer button
    public void chooseAnswer(View view){
        //Log.i("Tag",view.getTag().toString());
        //If answer correct
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Log.i("Answer","correct");
            //Add 1 to score
            score++;
            Log.i((Integer.toString(score)),"/");
            Log.i((Integer.toString(totalQuestion)),"total");
            resulttextView.setText("Correct");

        }else {
            Log.i("Answer", "Incorrect");

            resulttextView.setText("Incorrect");
        }

        totalQuestion++;
        pointtextView.setText(score + "/"+ totalQuestion);
        generateQuestion();
    }


    public void gameStart(View view){
        timetextView.setVisibility(View.VISIBLE);
        finaltextView.setVisibility(View.INVISIBLE);
        pointtextView.setVisibility(View.VISIBLE);
        sumtextView.setVisibility(View.VISIBLE);
        resulttextView.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        startButton.setEnabled(false);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
    }

    public void gameFinish(View view){
        timetextView.setVisibility(View.INVISIBLE);
        pointtextView.setVisibility(View.INVISIBLE);
        sumtextView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        finaltextView.setVisibility(View.VISIBLE);
        resulttextView.setText("Game Over");
        startButton.setText("Try Again");
        startButton.setEnabled(true);
        finaltextView.setText("You score: " + (Integer.toString(score)) + "/" +(Integer.toString(totalQuestion)));
        score = 0;
        totalQuestion = 0;
    }

    public void controlTimer(final View view){


        if(countdownIsActive == false){

            gameStart(view);
            countdownIsActive = true;

            // Test Button
            // Log.i("Button Pressed", "Pressed");
            countDownTimer = new CountDownTimer(30000,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i("Second ",String.valueOf(millisUntilFinished/1000));
                    timetextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
                }

                @Override
                public void onFinish() {
                    Log.i("finished", "timer done");
                    timetextView.setText("0");
                    gameFinish(view);
                    countdownIsActive = false;
                }
            }.start();
        }else{
            //Reset the Timer
            gameStart(view);
            timetextView.setText("30s");
            countDownTimer.cancel();
            countdownIsActive = false;

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Timer display on TextView
        timetextView = (TextView) findViewById(R.id.timetextView);
        //Set Point display on TextView
        pointtextView = (TextView) findViewById(R.id.pointtextView);
        //Set Sum display on TextView
        sumtextView = (TextView) findViewById(R.id.sumtextView);
        //Set Result display on TextView
        resulttextView = (TextView) findViewById(R.id.resulttextView);

        finaltextView = (TextView) findViewById(R.id.finaltextView);

        //Set Start button
        startButton = (Button) findViewById(R.id.StartButton);
        //Set answer button
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        //Set Gradlayout


        generateQuestion();

    }
}

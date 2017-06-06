package com.example.brian.gameconnect3;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 =  kangaroo, 1 = kiwi
    int activePlayer = 0;

    //Games active
    boolean gameActive = true;

    // 2 = inactive
    // logic
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // Win game logic
    //0 1 2
    //3 4 5
    //6 7 8
    int[][] winGamePositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        int tapCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tapCounter] == 2 && gameActive) {

            gameState[tapCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.kangaroo);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.kiwi);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).setDuration(3);

            for (int[] towinGame : winGamePositions) {

                System.out.println(gameState.toString());
                //System.out.println(gameState[towinGame[1]]);
                //System.out.println(gameState[towinGame[2]]);

                if (gameState[towinGame[0]] == gameState[towinGame[1]] &&
                        gameState[towinGame[1]] == gameState[towinGame[2]] &&
                        gameState[towinGame[0]] != 2) {
                    System.out.println(gameState[towinGame[0]]);

                    gameActive = false;

                    String winner = "kiwi";
                    if (gameState[towinGame[0]] == 0) {
                        winner = "kangaroo";
                    }


                    //Winning message
                    TextView textView = (TextView) findViewById(R.id.winnerMessage);
                    textView.setText(winner + " has win the game");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                    layout.setVisibility(View.VISIBLE);


                }
                else{
                    boolean gameOver = true;

                    for (int countState: gameState){
                        if (countState == 2)
                            gameOver = false;
                    }

                    if (gameOver){
                        //Winning message
                        TextView textView = (TextView) findViewById(R.id.winnerMessage);
                        textView.setText("Draw");

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {

        gameActive = true;

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);



        // 0 =  kangaroo, 1 = kiwi
        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++){

            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.board);

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

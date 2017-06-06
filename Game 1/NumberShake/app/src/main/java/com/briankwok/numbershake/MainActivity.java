package com.briankwok.numbershake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String message = "";

    public void testNumber(View view) {


        EditText userNumber = (EditText) findViewById(R.id.userNumber);

        //Log.i("userNumber", userNumber.getText().toString());
        if (userNumber.getText().toString().isEmpty()){
            message = "Please enter a number";
        }else {
            Number newNum = new Number();
            newNum.number = Integer.parseInt(userNumber.getText().toString());
            //System.out.println(newNum.numberSquare());

            String message = "";

            if (newNum.numberSquare()) {
                if (newNum.numberTriangle()) {
                    message = newNum.number + " is both triangle and square";
                } else {
                    message = newNum.number + " is square but not triangle";
                }
            } else {
                if (newNum.numberTriangle()) {
                    message = newNum.number + " is triangle but not square";
                } else {
                    message = newNum.number + " is neither square or triangle";
                }
            }

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    class Number {

        int number;

        public boolean numberSquare() {

            double squareNum = Math.sqrt(number);

            if (squareNum == Math.floor(squareNum)) {

                return true;
            } else {
                return false;
            }
        }

        public boolean numberTriangle() {

            int i = 1;

            int triangleNumber = 1;

            while (triangleNumber < number) {
                i++;
                triangleNumber = triangleNumber + i;
            }

            if (triangleNumber == number) {
                return true;
            } else {
                return false;
            }
        }
    }
}

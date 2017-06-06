package com.example.brian.whatstheweather;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;


public class MainActivity extends Activity {

    EditText cityName;
    Button findCity;
    TextView resultTextView;
    TextView tempTextView;

    public void findWeather(View view){
        Log.i("CityName: ", String.valueOf(cityName.getText()));

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(),0);

        try {
            String encoderCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
            DownloadTask downloadTask = new DownloadTask();
            //downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q=London&APPID=f0f8b7e05f6b6c96fa2cd66be1fcad28");

            //User enter country name
            downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q="+cityName.getText().toString()+"&APPID=f0f8b7e05f6b6c96fa2cd66be1fcad28");

        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityNameEditText);
        findCity = (Button) findViewById(R.id.findWeatherButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        tempTextView = (TextView) findViewById(R.id.tempTextView);

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;
                    result += current;

                    data = reader.read();
                }

                return result;
            } catch (MalformedURLException e) {
                //e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                //e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_SHORT).show();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //If the JSON object have a problem
            try {

                String message="";
                String message1="";

                JSONObject jsonObject = new JSONObject(result);

                String weatherInfo = jsonObject.getString("weather");
                String countryInfo = jsonObject.getString("name");
                String temperatureInfo = jsonObject.getJSONObject("main").getString("temp");
                double kelvin = Double.parseDouble ( temperatureInfo);
                double celsius = kelvin - 273.0;
                String currentTemp = String.valueOf(Math.ceil(celsius));


                Log.i("temperatureInfo: ",temperatureInfo);

                //Log.i("weather content", weatherInfo);
                //Log.i("countryInfo", countryInfo);

                //if multi weather content display
                JSONArray arr = new JSONArray(weatherInfo);
                //JSONArray arr1 = new JSONArray(temperatureInfo);
                //JSONObject object = new JSONObject(temperatureInfo);
                //JSONArray Jarray  = object.getJSONArray("temp");

                //arr
                for (int i = 0; i< arr.length(); i++){
                    //conver each part to a json object
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main ="";
                    String description="";

                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");

                    Log.i("main",main);
                    Log.i("description",description);


                    if (main!= "" && description !=""){

                        message += main + ": " + description + "\r\n";
                    }

                    //Log.i("main is", jsonPart.getString("main"));
                    //Log.i("description is ", jsonPart.getString("description"));
                    //Log.i("description is ", jsonPart.getString("name"));
                    //weatherResult.setText(jsonPart.getString("name")+ " " + jsonPart.getString("main"));
                }



                if (message != ""){
                    resultTextView.setText(message);
                    tempTextView.setText(currentTemp);
                    Log.i("result1", message);
                }else{
                    resultTextView.setText(message);
                    Log.i("result2", message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

           Log.i("City weather", result);
        }
    }
}

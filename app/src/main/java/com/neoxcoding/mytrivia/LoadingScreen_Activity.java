package com.neoxcoding.mytrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserName;

public class LoadingScreen_Activity extends AppCompatActivity {


    Handler handler = new Handler();
    Context context;
    ProgressBar pb;
    int loadingCount = 0;
    TextView quot_view, loading_view;

    //Quote API URL
    final public String apiUrl = "http://swquotesapi.digitaljedi.dk/api/SWQuote/RandomStarWarsQuote";

    //STRING ARRAY
    private String[] loadVerse = {
            "Preparing the HyperDrive",
            "Setting Coordinates",
            "Charging Ion Cannons",
            "Checking Deflector Shields"
    };


    final Runnable loadingText = new Runnable() {
        @Override
        public void run() {

            loading_view = findViewById(R.id.loading_text);
            loading_view.setText(loadVerse[loadingCount]);
            loadingCount += 1;
            handler.postDelayed(this, 2_000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen_);
        setPointer();






    }


    private void setPointer() {

        this.context = this;
        quot_view = findViewById(R.id.quot_text_view);
        pb = findViewById(R.id.progress_bar);


        getDataJson();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //GO TO MAIN MENU
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                //STOP loadingText FROM RUNNING
                handler.removeCallbacks(loadingText);
                //FINISH LOADING SCREEN ACTIVITY
                finish();
            }
        };



        handler.postDelayed(runnable, 8_000);
        handler.post(loadingText);
    }






    //For getting the data with AsyncTask
    @SuppressLint("StaticFieldLeak")
    public void getDataJson(){

        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {

                String jsonString="";

                //we need to open http apiUrl connection to our desired apiUrl
                HttpURLConnection connection = null;

                try {
                    //open a connection to http
                    connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                    //create new input stream reader
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    //open a bufferdreader for getting all the data
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    //create an empty line to read
                    String line;
                    while ((line=bufferedReader.readLine()) != null){
                        jsonString += line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
                Log.e("data", "doInBackground: \n"+jsonString );

                return jsonString;
            }

            @Override
            protected void onPostExecute(String myString) {
                try {
                    //Create a json object using String content
                    JSONObject jsonObject = new JSONObject(myString);

               //If API response has more than 1 jsonObjects, USE: JSONObject currently = jsonObject.getJSONObject("nameOfJsonObject")

                    //But we have only 1 Json object in the response so find your desired String, passing it's name:
                    String quote = jsonObject.getString("starWarsQuote");

                    //SET QUOTE INTO THE TEXT VIEW
                    quot_view.setText(quote);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }



}

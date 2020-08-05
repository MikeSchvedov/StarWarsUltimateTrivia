package com.neoxcoding.mytrivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.util.Log;

import android.view.View;


import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import static com.neoxcoding.mytrivia.PrefrenceData.getCurrentUserName;
import static com.neoxcoding.mytrivia.PrefrenceData.getEffectsSettigns;
import static com.neoxcoding.mytrivia.PrefrenceData.getMusicSettings;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserName;
import static com.neoxcoding.mytrivia.PrefrenceData.setEffectsSettigns;
import static com.neoxcoding.mytrivia.PrefrenceData.setMusicSettings;

import java.io.IOException;


public class MainMenu extends AppCompatActivity {


    MySQLiteManager mySQLiteManager;
    MediaPlayer MainMenuMusic_MP, ButtonClick_MP;
    TextView titleView;

    SharedPreferences firstTimePreferences = null;

    Context context;
    private final static int MAX_VOLUME = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        firstTimePreferences = getSharedPreferences("com.neoxcoding.mytrivia", MODE_PRIVATE);

        MainMenuMusic_MP = MediaPlayer.create(this, R.raw.mainmusic);

        ButtonClick_MP = MediaPlayer.create(this, R.raw.button_sound);
        titleView = findViewById(R.id.textview_title);


        setPointer();
    }



    @Override
    protected void onResume() {
        super.onResume();
        //CHECK IF ITS THE FIRST TIME RUNNING THE APP
        if (firstTimePreferences.getBoolean("firstrun", true)) {
            Toast.makeText(context, "this is the first run", Toast.LENGTH_LONG).show();
            setMusicSettings(context, "on");
            setEffectsSettigns(context, "on");
            setCurrentUserCountry(context, "Unknown");
            setCurrentUserName(context, "GUEST");
            //Now it will always be false
            firstTimePreferences.edit().putBoolean("firstrun", false).commit();
        }

        //Check if settings are disabling music
        if(getMusicSettings(context).equals("on")) {
            MainMenuMusic_MP.start();
        }

    }


    private void setPointer() {
        this.context = this;
        mySQLiteManager = new MySQLiteManager(this);





        findViewById(R.id.btn_myProgress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHANGE THE WEIGHT OF BUTTON ON CLICK,  1.0f -> 2.0f
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        2.0f
                );
                findViewById(R.id.btn_myProgress).setLayoutParams(param);


                //STOP MAIN MENU MUSIC
                stopMainMenuMusic();
                //START PROGRESS PAGE
                Intent intent = new Intent(MainMenu.this, ProgressPage.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //FINISH THE MAIN MENU ACTIVITY
                finish();


            }
        });


        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHANGE THE WEIGHT OF BUTTON ON CLICK,  1.0f -> 2.0f
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        2.0f
                );
                findViewById(R.id.btn_start).setLayoutParams(param);


                //MAKING THE BUTTON CLICK LESS NOISY - 50% VOLUME
                final float volume = (float) (1 - (Math.log(MAX_VOLUME - 50) / Math.log(MAX_VOLUME)));
                ButtonClick_MP.setVolume(volume, volume);

                //Check if settings are disabling music
                if (getEffectsSettigns(context).equals("on")) {
                    //MAKE BUTTON CLICK SOUND
                    ButtonClick_MP.start();
                }

                //STOP MAIN MENU MUSIC
                stopMainMenuMusic();

                //Check if its the first time playing to set user name and country
                if (getCurrentUserName(context).equals("GUEST")) {
                    //Go to Login Page
                    Intent intent = new Intent(MainMenu.this, FirstTimeLogin.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    //FINISH THE MAIN MENU ACTIVITY
                    finish();

                } else {

                    //START THE GAME
                    Intent intent = new Intent(MainMenu.this, GameActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    //FINISH THE MAIN MENU ACTIVITY
                    finish();

                }

            }
        });


        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHANGE THE WEIGHT OF BUTTON ON CLICK,  1.0f -> 2.0f
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        2.0f
                );
                findViewById(R.id.btn_myProgress).setLayoutParams(param);


                //STOP MAIN MENU MUSIC
                stopMainMenuMusic();
                //START PROGRESS PAGE
                Intent intent = new Intent(MainMenu.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //FINISH THE MAIN MENU ACTIVITY
                finish();


            }
        });







    }



    private void stopMainMenuMusic() {

        if (MainMenuMusic_MP != null) {
            MainMenuMusic_MP.stop();

            try {
                MainMenuMusic_MP.prepare();
            } catch (IOException e) {
                Log.e(null, "IOException during prepare after stop! mMediaPlayer value: " + MainMenuMusic_MP);
            }
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.AlertDialog)
                //   .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit From App")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainMenuMusic_MP.stop();
                        MainMenuMusic_MP.release();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

}

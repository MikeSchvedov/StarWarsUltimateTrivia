package com.neoxcoding.mytrivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static com.neoxcoding.mytrivia.PrefrenceData.getEffectsSettigns;
import static com.neoxcoding.mytrivia.PrefrenceData.getMusicSettings;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserName;
import static com.neoxcoding.mytrivia.PrefrenceData.setEffectsSettigns;
import static com.neoxcoding.mytrivia.PrefrenceData.setMusicSettings;

public class SettingsActivity extends AppCompatActivity {

    Context context;
    Button confirmSwitch;
    EditText userName, userCountry;
    TextView changeUserTitle;
    MySQLiteManager mySQLiteManager;
    MediaPlayer Settings_MP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.context = this;

        mySQLiteManager = new MySQLiteManager(this);

        userCountry = findViewById(R.id.edittext_switch_country);
        userName = findViewById(R.id.edittext_switch_user);
        changeUserTitle = findViewById(R.id.changeuser_title);

        Settings_MP = MediaPlayer.create(this, R.raw.settingmusic);


        //Check if settings are disabling music
        if(getMusicSettings(context).equals("on")) {
            Settings_MP.start();
        }




        final CheckBox checkBox_MusicDisable = (CheckBox) findViewById(R.id.disable_music_checkbox);

        //Checking music_setting status
        if (getMusicSettings(context).equals("on")) {
            checkBox_MusicDisable.setChecked(false);
        } else {
            checkBox_MusicDisable.setChecked(true);

        }
        checkBox_MusicDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //If the box is checked change settings
                if (checkBox_MusicDisable.isChecked()) {
                    setMusicSettings(context, "off");
                    stopGamePlayMusic(Settings_MP);
                } else {
                    setMusicSettings(context, "on");
                    if(getMusicSettings(context).equals("on")) {
                        Settings_MP.start();
                    }
                }
            }
        });



        final CheckBox checkBox_EffectDisable = (CheckBox) findViewById(R.id.disable_FX_checkbox);

        //Checking music_setting status
        if (getEffectsSettigns(context).equals("on")) {
            checkBox_EffectDisable.setChecked(false);
        } else {
            checkBox_EffectDisable.setChecked(true);

        }
        checkBox_EffectDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //If the box is checked change settings
                if (checkBox_EffectDisable.isChecked()) {
                    setEffectsSettigns(context, "off");
                } else {
                    setEffectsSettigns(context, "on");
                }
            }
        });

findViewById(R.id.btn_save_user_data).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        new AlertDialog.Builder(context, R.style.AlertDialog)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert!")
                .setMessage("Existing High Score will be deleted! Continue?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String newUserName = userName.getText().toString().toUpperCase();
                        String newUserCountry = userCountry.getText().toString().toUpperCase();

                        setCurrentUserName(context, newUserName);
                        setCurrentUserCountry(context, newUserCountry);

                        changeUserTitle.setText("user Changed Successfully!");
                        changeUserTitle.setTextColor(Color.GREEN);

                        userName.setText("");
                        userCountry.setText("");

                        //DELETE HIGHSCORE IN SQLITE
                        mySQLiteManager.deleteOldData();

                    }

                })
                .setNegativeButton("No", null)
                .show();



    }
});








    }



    private void stopGamePlayMusic(MediaPlayer stopMP) {

        if (stopMP != null) {
            stopMP.stop();

            try {
                stopMP.prepare();
            } catch (IOException e) {
                Log.e(null, "IOException during prepare after stop! mMediaPlayer value: " + stopMP);
            }
        }
    }



    @Override
    public void onBackPressed() {
      stopGamePlayMusic(Settings_MP);
        finish();
        //Change title back to default
        if(changeUserTitle.toString().equals("user Changed Successfully!")) {
            changeUserTitle.setText("Change user");
            changeUserTitle.setTextColor(Color.parseColor("#E7CE049"));
        }
    }

    @Override
    public void finish() {

        super.finish();

        Intent intent = new Intent(SettingsActivity.this, MainMenu.class);
      //  stopGamePlayMusic(ProgressMusic_MP);

        //Change title back to default
        if(changeUserTitle.toString().equals("user Changed Successfully!")) {
            changeUserTitle.setText("Change user");
            changeUserTitle.setTextColor(Color.parseColor("#E7CE04"));
        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


}

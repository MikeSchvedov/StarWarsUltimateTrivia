package com.neoxcoding.mytrivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserName;

public class FirstTimeLogin extends AppCompatActivity {


    Context context;
    Button confirmSwitch;
    EditText userName, userCountry;
    TextView changeUserTitle;
    MySQLiteManager mySQLiteManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_login);

        this.context = this;

        mySQLiteManager = new MySQLiteManager(this);

        userCountry = findViewById(R.id.edittext_switch_country_firsttime);
        userName = findViewById(R.id.edittext_switch_user_firsttime);


        findViewById(R.id.btn_save_user_data_firsttime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUserName = userName.getText().toString().toUpperCase();
                String newUserCountry = userCountry.getText().toString().toUpperCase();

                setCurrentUserName(context, newUserName);
                setCurrentUserCountry(context, newUserCountry);



                //DELETE HIGHSCORE IN SQLITE
                mySQLiteManager.deleteOldData();
                //GOING TO MAIN MENU
                Intent intent = new Intent(FirstTimeLogin.this, GameActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //FINISH THE GAME ACTIVITY
                finish();

            }


        });
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit to Main Menu")
                .setMessage("Are you sure you want to quit?\nYou need a first-time login.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //GOING TO MAIN MENU
                        Intent intent = new Intent(FirstTimeLogin.this, MainMenu.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        //FINISH THE GAME ACTIVITY
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }



}

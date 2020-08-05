package com.neoxcoding.mytrivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.Persistence;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.neoxcoding.mytrivia.PrefrenceData.getCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.getCurrentUserName;
import static com.neoxcoding.mytrivia.PrefrenceData.getMusicSettings;

public class GameOverActivity extends AppCompatActivity {
    MySQLiteManager mySQLiteManager;
    TextView scoreCounterDisplay, newHighScoreAlert;
    Context context;
    MediaPlayer gameOverNoHighScore_MP,HighScore_MP;
    boolean userExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        this.context = this;

        scoreCounterDisplay = findViewById(R.id.textview_scoreCounter);
        newHighScoreAlert = findViewById(R.id.textview_newHighScore);

        mySQLiteManager = new MySQLiteManager(this);

        gameOverNoHighScore_MP = MediaPlayer.create(this, R.raw.game_over_loosing_rmusic);
        HighScore_MP = MediaPlayer.create(this, R.raw.highscore_music);

        //Getting the extra "MyScore"  from the intent.

        Bundle extras = getIntent().getExtras();
        String stringVariableName = null;
        if (extras != null) {
            stringVariableName = extras.getString("MyScore");
        }
        int tempGameOverScore = 0;
        if (stringVariableName != null) {
            tempGameOverScore = Integer.parseInt(stringVariableName);
        }

        scoreCounterDisplay.setText(tempGameOverScore + "");

        final int highscore = mySQLiteManager.getHighScore();

        if (tempGameOverScore > highscore) {

            if(getMusicSettings(context).equals("on")) {
                HighScore_MP.start();
            }



            newHighScoreAlert.setVisibility(View.VISIBLE);
            scoreCounterDisplay.setTextColor(Color.parseColor("#18B4C9"));

            //DELETING OLD HIGHSCORE FROM DATABASE
            mySQLiteManager.deleteOldData();

            //SETTING NEW HIGHSCORE IN DATABASE
            mySQLiteManager.setHighScore(tempGameOverScore);




            //SAVE NEW USER IF NOT EXISTS
             saveNewUserData(getCurrentUserName(context), tempGameOverScore, getCurrentUserCountry(context));

             //UPDATE SCORE OF USER
             updateData(tempGameOverScore);



        }else{

            if(getMusicSettings(context).equals("on")) {
                gameOverNoHighScore_MP.start();
            }

        }


        findViewById(R.id.btn_backToMainMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitToMainActivity();

            }


        });


    }

    private void updateData(int score ) {


        Map<String, Object> changes = new HashMap<>();
        changes.put( "score", score );
        Backendless.Data.of( "LeaderBoards" ).update( "name = '"+getCurrentUserName(context)+"'", changes, new AsyncCallback<Integer>()
        {
            @Override
            public void handleResponse( Integer objectsUpdated )
            {
                Log.i( "MYAPP", "Server has updated " + objectsUpdated + " objects" );
                Toast.makeText(context, "Leaderboards Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                Log.e( "MYAPP", "Server reported an error - " + fault );
            }
        } );
    }


    private void saveNewUserData(String myName, int score, String country) {

        LeaderBoards leaderBoardsObject = new LeaderBoards();

        leaderBoardsObject.setName(myName);
        leaderBoardsObject.setScore(score);
        leaderBoardsObject.setCountry(country);

        Backendless.Data.of(LeaderBoards.class).save(leaderBoardsObject, new AsyncCallback<LeaderBoards>() {
            @Override
            public void handleResponse(LeaderBoards response) {
                Toast.makeText(context, "Saved on Server", Toast.LENGTH_SHORT).show();




            }

            @Override
            public void handleFault(BackendlessFault fault) {
            //    Toast.makeText(context, "User Already Exists Error", Toast.LENGTH_SHORT).show();



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

        exitToMainActivity();

    }


    private void exitToMainActivity() {
        stopGamePlayMusic(gameOverNoHighScore_MP);
        stopGamePlayMusic(HighScore_MP);

        Intent intent = new Intent(GameOverActivity.this, MainMenu.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }


}

package com.neoxcoding.mytrivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import static com.neoxcoding.mytrivia.PrefrenceData.getCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.getCurrentUserName;
import static com.neoxcoding.mytrivia.PrefrenceData.getMusicSettings;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserCountry;
import static com.neoxcoding.mytrivia.PrefrenceData.setCurrentUserName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgressPage extends AppCompatActivity {

    TextView showHighScore, showUser;
    MySQLiteManager mySQLiteManager;
    Context context;
    MediaPlayer ProgressMusic_MP;
    Button globalBTN, countryBTN;
    private final static int MAX_VOLUME = 100;

    //Backendless Stuff
    private static final String API_HOST = "https://api.backendless.com";
    private static final String APP_ID = "62B41496-2D93-1FA8-FFA7-814FAD19A800";
    private static final String APP_API_KEY = "E781E592-D48E-44BF-8F2F-EE49C4432313";
    private static final String TAG = "RTDatabase";
    private IDataStore DataStore = Backendless.Data.of(LeaderBoards.class);
    //RecycleView Stuff
    private RecyclerView recyclerView;
    private LeaderBoardsAdapter adapter;
    private ArrayList leaderboardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_page);

        this.context = this;

        ProgressMusic_MP = MediaPlayer.create(this, R.raw.cantina);

        //MAKING THE BUTTON CLICK LESS NOISY - 50% VOLUME
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 50) / Math.log(MAX_VOLUME)));
        ProgressMusic_MP.setVolume(volume, volume);
        //Check if settings are disabling music
        if(getMusicSettings(context).equals("on")) {
            ProgressMusic_MP.start();
        }

        globalBTN = findViewById(R.id.global_button);
        countryBTN = findViewById(R.id.country_button);



        showHighScore = findViewById(R.id.textview_showHighScore);
        showUser = findViewById(R.id.textview_showuser);

        mySQLiteManager = new MySQLiteManager(this);

        int hs = mySQLiteManager.getHighScore();

        String highScore = hs + "";

        showHighScore.setText("Your HighScore is: " + highScore);
        showUser.setText(getCurrentUserName(context)+" from "+getCurrentUserCountry(context));


        initBackendless();
        initUI();
        getGlobalLeaderBoardsData();


        countryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MAKE CURRENT BUTTON YELLOW
                countryBTN.setBackgroundResource(R.drawable.my_button_bg_menu);
                countryBTN.setTextColor(Color.parseColor("#E7CE04"));
                //MAKE OTHER GREY
                globalBTN.setBackgroundResource(R.drawable.my_button_bg_menu_unselected);
                globalBTN.setTextColor(Color.parseColor("#5A5955"));

                getCountryLeaderBoardsData();
            }
        });

        globalBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MAKE CURRENT BUTTON YELLOW
                globalBTN.setBackgroundResource(R.drawable.my_button_bg_menu);
                globalBTN.setTextColor(Color.parseColor("#E7CE04"));
                //MAKE OTHER GREY
                countryBTN.setBackgroundResource(R.drawable.my_button_bg_menu_unselected);
                countryBTN.setTextColor(Color.parseColor("#5A5955"));

                getGlobalLeaderBoardsData();
            }
        });


    }


    //INITIALIZE BACKENDLESS
    private void initBackendless() {
        Backendless.setUrl(API_HOST);
        Backendless.initApp(this, APP_ID, APP_API_KEY);
    }

    //INITIALIZE UI
    private void initUI() {
        recyclerView = findViewById(R.id.admin_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }



    private void getGlobalLeaderBoardsData() {
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(100);
        queryBuilder.setSortBy("score DESC", "name");
        DataStore.find(queryBuilder, new AsyncCallback<List>() {
            @Override
            public void handleResponse(List response) {

                LeaderBoardsAdapter adapter = new LeaderBoardsAdapter(response);
                leaderboardsList = new ArrayList<List>(response);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ProgressPage.this,
                        "Error occurred: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getCountryLeaderBoardsData() {
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(100);
        queryBuilder.setSortBy("score DESC", "name");
        //SETTING CONDITION TO GET LIST BY CURRENT COUNTRY
        String whereClause = "country = '"+getCurrentUserCountry(context)+"'";
        queryBuilder.setWhereClause( whereClause );
        DataStore.find(queryBuilder, new AsyncCallback<List>() {
            @Override
            public void handleResponse(List response) {

                LeaderBoardsAdapter adapter = new LeaderBoardsAdapter(response);
                leaderboardsList = new ArrayList<List>(response);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ProgressPage.this,
                        "Error occurred: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
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
        stopGamePlayMusic(ProgressMusic_MP);
        finish();

    }

    @Override
    public void finish() {

        super.finish();

        Intent intent = new Intent(ProgressPage.this, MainMenu.class);
        stopGamePlayMusic(ProgressMusic_MP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}

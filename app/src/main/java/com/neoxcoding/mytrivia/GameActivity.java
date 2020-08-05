package com.neoxcoding.mytrivia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.neoxcoding.mytrivia.PrefrenceData.getEffectsSettigns;
import static com.neoxcoding.mytrivia.PrefrenceData.getMusicSettings;

public class GameActivity extends AppCompatActivity {

    MediaPlayer GamePlay_MP, Correct_MP, Wrong_MP;

    TextView questionView, scoreView, lifeView, timer_view;
    Button answer1btn, answer2btn, answer3btn, answer4btn;
    Context context;
    ImageView life_1, life_2, life_3;

    ArrayList<Question> allQuestionsArray = new ArrayList<>();

    ArrayList<Button> answerBTNArray = new ArrayList<Button>();

    CountDownTimer mCountDown;
    static int QCounter = 0;
    static int ScoreCount = 0;
    static int LifeCounter = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        this.context = this;




        GamePlay_MP = MediaPlayer.create(this, R.raw.gameplaymusic);
        //Check if settings are disabling music
        if(getMusicSettings(context).equals("on")) {
            GamePlay_MP.start();
        }

        QuestionBuilder(allQuestionsArray);

        setPointer();
    }

    //This is the question builder - EXPAND TO SEE CONTENT
    private void QuestionBuilder(ArrayList<Question> allQuestionsArray) {

        String[] q0_wrongAnswerArray = {"Mos Vargas", "Mos Davos", "Mos Def", "Filler"};
        BuildNewQuestion(allQuestionsArray, 0, "Where do Luke and Obi-Wan meet Han and Chewie?", "Mos Eisley", q0_wrongAnswerArray, context, "MEDIUM");

        String[] q1_wrongAnswerArray = {"Farlax", "Tatooine", "Jedha", "Filler"};
        BuildNewQuestion(allQuestionsArray, 1, "On which planet do we first meet Rey in The Force Awakens?", "Jakku", q1_wrongAnswerArray, context, "MEDIUM");

        String[] q2_wrongAnswerArray = {"Hoth", "Naboo", "Yavin", "Filler"};
        BuildNewQuestion(allQuestionsArray, 2, "Where was Darth Vader born?", "Tatooine", q2_wrongAnswerArray, context, "EASY");


        String[] q3_wrongAnswerArray = {"Kamino", "Dagobah", "Mustafar", "Filler"};
        BuildNewQuestion(allQuestionsArray, 3, "Where do Wookiees come from?", "Kashyyyk", q3_wrongAnswerArray, context, "MEDIUM");

        String[] q4_wrongAnswerArray = {"Yoda", "Luke", "Obi-Wan", "Filler"};
        BuildNewQuestion(allQuestionsArray, 4, "Who built C-3PO?", "Anakin", q4_wrongAnswerArray, context, "EASY");

        String[] q5_wrongAnswerArray = {"DD-10", "CC-9", "AA-7", "Filler"};
        BuildNewQuestion(allQuestionsArray, 5, "What is the name of Poe Dameron's astromech droid?", "BB-8", q5_wrongAnswerArray, context, "EASY");

        String[] q6_wrongAnswerArray = {"Mace Windu", "Luke Skywalker", "Obi-Wan", "Filler"};
        BuildNewQuestion(allQuestionsArray, 6, "Who said: 'Im just a simple man trying to make my way in the universe.'", "Jango Fett", q6_wrongAnswerArray, context, "MEDIUM");

        String[] q7_wrongAnswerArray = {"Obi-Wan", "Darth Vader", "Yoda", "Filler"};
        BuildNewQuestion(allQuestionsArray, 7, "Who said: 'There’s always a bigger fish.'", " Qui-Gon Jinn", q7_wrongAnswerArray, context, "MEDIUM");

        String[] q8_wrongAnswerArray = {"Jyn Erso", "Finn", "C-3PO", "Filler"};
        BuildNewQuestion(allQuestionsArray, 8, "Who said: 'This is Where the Fun Begins'", "Anakin", q8_wrongAnswerArray, context, "MEDIUM");

        String[] q9_wrongAnswerArray = {"Darth Sidious", "Darth Malgus", "Darth Vader", "Filler"};
        BuildNewQuestion(allQuestionsArray, 9, "Who created the 'Rule of Two'?", "Darth Bane", q9_wrongAnswerArray, context, "HARD");

        String[] q10_wrongAnswerArray = {"Hutts", "Jawas", "Wookiees", "Filler"};
        BuildNewQuestion(allQuestionsArray, 10, "Which furry species lives on the forest moon of Endor?", "Ewoks", q10_wrongAnswerArray, context, "EASY");

        String[] q11_wrongAnswerArray = {"Wampa", "Rancor", "Dianoga", "Filler"};
        BuildNewQuestion(allQuestionsArray, 11, "Which monster can be found in Tatooine's 'Great Pit of Carkoon'?", "Sarlacc", q11_wrongAnswerArray, context, "MEDIUM");

        String[] q12_wrongAnswerArray = {"Nexu", "Rancor", "Sarlacc", "Filler"};
        BuildNewQuestion(allQuestionsArray, 12, "Which monster can be found in the Death Star’s trash compactor?", "Dianoga", q12_wrongAnswerArray, context, "MEDIUM");

        String[] q13_wrongAnswerArray = {"Nexu", "Acklay", "Reek", "Filler"};
        BuildNewQuestion(allQuestionsArray, 13, "In episode 2, which monster was NOT one of three beasts set loose in the Genosian arena?", "Rancor", q13_wrongAnswerArray, context, "MEDIUM");

        String[] q14_wrongAnswerArray = {"Mustafar", "Tatooine", "Death Star 2", "Filler"};
        BuildNewQuestion(allQuestionsArray, 14, "Where did Obi-Wan and Darth Vader had their last battle?", "Death Star 1", q14_wrongAnswerArray, context, "MEDIUM");

        String[] q15_wrongAnswerArray = {"Naboo", "Mandalore", "Dathomir", "Filler"};
        BuildNewQuestion(allQuestionsArray, 15, "Where did Obi-Wan and Darth Maul had their last battle?", "Tatooine", q15_wrongAnswerArray, context, "MEDIUM");

        String[] q16_wrongAnswerArray = {"Jyn Erso", " Cassian Andor", "Chirrut Imwe", "Filler"};
        BuildNewQuestion(allQuestionsArray, 16, "Who did not die on the planet of Scarif?", "Saw Gererra", q16_wrongAnswerArray, context, "MEDIUM");

        String[] q17_wrongAnswerArray = {"Naboo", "Genosis", "Mustafar", "Filler"};
        BuildNewQuestion(allQuestionsArray, 17, "Where did General Grievous  died?", "Utapau", q17_wrongAnswerArray, context, "MEDIUM");

        String[] q18_wrongAnswerArray = {"Blue and Green", "Blue and Red", "Both Red", "Filler"};
        BuildNewQuestion(allQuestionsArray, 18, "Darth Raven had two lightsabers. What was their colors?", "Red and Purple", q18_wrongAnswerArray, context, "MEDIUM");

        String[] q19_wrongAnswerArray = {"Kyber", "Plastoid", "Famous Bounty Hunter", "Filler"};
        BuildNewQuestion(allQuestionsArray, 19, "What is also known as 'Mandelorian Steel'?", "Beskar", q19_wrongAnswerArray, context, "MEDIUM");

        String[] q20_wrongAnswerArray = {"The Republic", "The Empire", "The Banking Clan", "Filler"};
        BuildNewQuestion(allQuestionsArray, 20, "What is the name of the organization that created a blockade around Naboo?", "The Trade Federation", q20_wrongAnswerArray, context, "MEDIUM");

        String[] q21_wrongAnswerArray = {"10 Years", "2 Years", "1 Years", "Filler"};
        BuildNewQuestion(allQuestionsArray, 21, "How many years separate the end of Attack of the Clones and Revenge of the Sith?", "3 Years", q21_wrongAnswerArray, context, "MEDIUM");

        String[] q22_wrongAnswerArray = {"Yavin 4", "Moon of Alderan", "Tatooine", "Filler"};
        BuildNewQuestion(allQuestionsArray, 22, "What planet is the second Death Star orbiting in Return of the Jedi?", "Moon of Endor", q22_wrongAnswerArray, context, "MEDIUM");

        String[] q23_wrongAnswerArray = {"Lea Organa", "Padme Amidala", "Rey", "Filler"};
        BuildNewQuestion(allQuestionsArray, 23, "Who is the cantina owner on Takodana who has a lightsaber in her possession?", "Maz Kanata", q23_wrongAnswerArray, context, "MEDIUM");






        String[] q24_wrongAnswerArray = {"Alderan", "Naboo", "Jakku", "Filler"};
        BuildNewQuestion(allQuestionsArray, 24, "What planet is Han Solo an orphan on in Solo: A Star Wars Story?", "Corellia", q24_wrongAnswerArray, context, "MEDIUM");

        String[] q25_wrongAnswerArray = {"C-3PO", "Rey", "Finn", "Filler"};
        BuildNewQuestion(allQuestionsArray, 25, "Which Character is captured by the First Order on Pasaana?", "Chewbacca", q25_wrongAnswerArray, context, "MEDIUM");




        String[] q26_wrongAnswerArray = {"He's Vader's nephew", "He's Vader's brother", "He's Vader's son", "Filler"};
        BuildNewQuestion(allQuestionsArray, 26, "Kylo Ren idolizes Darth Vader. How is her related to Vader?", "He's Vader's grandson", q26_wrongAnswerArray, context, "MEDIUM");

        String[] q27_wrongAnswerArray = {"Ahch-To", "Tatooine", "Endor", "Filler"};
        BuildNewQuestion(allQuestionsArray, 27, "Finn, Rose and BB-8 go to which location to find a hacker?", "Canto Bight", q27_wrongAnswerArray, context, "MEDIUM");




        String[] q28_wrongAnswerArray = {"Red", "Green", "Yellow", "Filler"};
        BuildNewQuestion(allQuestionsArray, 28, "What color was Captain Rex's uniform?", "Blue", q28_wrongAnswerArray, context, "MEDIUM");




        String[] q29_wrongAnswerArray = {"Unknown Regions", "Hexagon", "Courascant", "Filler"};
        BuildNewQuestion(allQuestionsArray, 29, "What is located at the center of the galaxy?", "A black hole", q29_wrongAnswerArray, context, "MEDIUM");

        String[] q30_wrongAnswerArray = {"Darth Vader", "Yoda", "Han Solo", "Filler"};
        BuildNewQuestion(allQuestionsArray, 30, "Who died last?", "Leah Skywalker", q30_wrongAnswerArray, context, "MEDIUM");




        Collections.shuffle(allQuestionsArray);
    }


    private void BuildNewQuestion(ArrayList<Question> questionArray, int position, String mainQuestion, String correctAnswer, String[] wrongArray, Context context, String difficulty) {
        questionArray.add(new Question(context));
        questionArray.get(position).setMainQuestion(mainQuestion);
        questionArray.get(position).setCorrectAnswer(correctAnswer);
        questionArray.get(position).setWrongAnswers(wrongArray);
        questionArray.get(position).setDifficulty(difficulty);

    }


    private void setPointer() {

        //Set initial life
        LifeCounter = 3;

        scoreView = findViewById(R.id.textview_score);
        questionView = findViewById(R.id.textview_question);
        timer_view = findViewById(R.id.textview_timer);

        //Set life icons
        life_1 = findViewById(R.id.image_life1);
        life_2 = findViewById(R.id.image_life2);
        life_3 = findViewById(R.id.image_life3);

        //Set answer buttons
        answer1btn = findViewById(R.id.btn_answer1);
        answer2btn = findViewById(R.id.btn_answer2);
        answer3btn = findViewById(R.id.btn_answer3);
        answer4btn = findViewById(R.id.btn_answer4);

        //Add the buttons into the answerBTNArray
        answerBTNArray.add(answer1btn);
        answerBTNArray.add(answer2btn);
        answerBTNArray.add(answer3btn);
        answerBTNArray.add(answer4btn);

        //Shuffle the answerBTNArray to randomize the order
        Collections.shuffle(answerBTNArray);

        //Start new question
        startNewQuestion(answerBTNArray, LifeCounter);

    }

    private void startNewQuestion(final ArrayList<Button> buttonsArray, int LifeCounter) {
        setNewTimer();

        //Updating Life Icons visibility
        updateLifeVisibility(LifeCounter);


        //Shuffle so buttons location will be different each round
        Collections.shuffle(buttonsArray);

        scoreView.setText("Score: " + ScoreCount);

        //loop trough all existing questions in questionArray
        // for (int QCounter = 0; QCounter < allQuestionsArray.size(); QCounter++) {
        final int CORRECT_ANSWER_COUNTER = QCounter;

        //SET MAIN QUESTION TO THE TEXT VIEW ACCORDING TO THE CURRENT QCounter
        questionView.setText(allQuestionsArray.get(QCounter).getMainQuestion());


        setWrongAnswers(buttonsArray);


        //Loop again trough the button array and check if one of the buttons is called "Filler" (text not changed)
        //if so, change it to the correct answer text
        for (int CCounter = 0; CCounter < buttonsArray.size(); CCounter++) {

            String temp = buttonsArray.get(CCounter).getText().toString();

            if (temp.equals("Filler")) {

                //IF THE NAME OF A BUTTON IS Filler CHANGE ITS TEXT TO THE CORRECT ANSWER ACCORDING TO THE CURRENT QCounter
                buttonsArray.get(CCounter).setText(allQuestionsArray.get(QCounter).getCorrectAnswer());
            }
        }


        answer1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1btn.getText().equals(allQuestionsArray.get(CORRECT_ANSWER_COUNTER).getCorrectAnswer())) {

                    correctAnswer(buttonsArray, answer1btn);

                } else {
                    wrongAnswer(answer1btn);
                }
            }

        });

        answer2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer2btn.getText().equals(allQuestionsArray.get(CORRECT_ANSWER_COUNTER).getCorrectAnswer())) {

                    correctAnswer(buttonsArray, answer2btn);

                } else {
                    wrongAnswer(answer2btn);
                }

            }
        });

        answer3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer3btn.getText().equals(allQuestionsArray.get(CORRECT_ANSWER_COUNTER).getCorrectAnswer())) {

                    correctAnswer(buttonsArray, answer3btn);

                } else {
                    wrongAnswer(answer3btn);
                }

            }
        });

        answer4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer4btn.getText().equals(allQuestionsArray.get(CORRECT_ANSWER_COUNTER).getCorrectAnswer())) {

                    correctAnswer(buttonsArray, answer4btn);
                } else {

                    wrongAnswer(answer4btn);
                }

            }
        });


    }

    private void updateLifeVisibility(int lifeCounter) {


        switch (lifeCounter) {

            case 1:
                life_1.setVisibility(View.VISIBLE);
                life_2.setVisibility(View.INVISIBLE);
                life_3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                life_1.setVisibility(View.VISIBLE);
                life_2.setVisibility(View.VISIBLE);
                life_3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                life_1.setVisibility(View.VISIBLE);
                life_2.setVisibility(View.VISIBLE);
                life_3.setVisibility(View.VISIBLE);
                break;
            case 0:
                life_1.setVisibility(View.INVISIBLE);
                life_2.setVisibility(View.INVISIBLE);
                life_3.setVisibility(View.INVISIBLE);
                break;

        }


    }


    private void setWrongAnswers(ArrayList<Button> buttonsArray) {

        buttonsArray.get(0).setText(allQuestionsArray.get(QCounter).getWrongAnswers()[0]);
        buttonsArray.get(1).setText(allQuestionsArray.get(QCounter).getWrongAnswers()[1]);
        buttonsArray.get(2).setText(allQuestionsArray.get(QCounter).getWrongAnswers()[2]);
        buttonsArray.get(3).setText(allQuestionsArray.get(QCounter).getWrongAnswers()[3]);

    }

    private void onGameOver() {


        Intent gameOverScreen = new Intent(GameActivity.this, GameOverActivity.class);

        //SET EXTRAS
        Bundle extras = new Bundle();
        String endGameScore = ScoreCount + "";
        ScoreCount = 0;
        extras.putString("MyScore", endGameScore);
        gameOverScreen.putExtras(extras);

        //STOP GAME PLAY MUSIC
        stopGamePlayMusic(GamePlay_MP);
        //CANCEL OLD TIMER
        cancelTimer();
        //GO TO GAME OVER SCREEN
        startActivity(gameOverScreen);
        //FINISH GAME ACTIVITY
        finish();


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


    public void setNewTimer() {

        mCountDown = new CountDownTimer(11000, 1000) {


            public void onTick(long millisUntilFinished) {
                timer_view.setText("" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                onGameOver();
            }
        }.start();
    }


    public void cancelTimer() {
        mCountDown.cancel();
    }


    private void wrongAnswer(Button button) {
        Wrong_MP = MediaPlayer.create(this, R.raw.wrong_answer_music);


        //Check if settings are disabling music
        if(getEffectsSettigns(context).equals("on")) {
            //PLAY SOUND
            Wrong_MP.start();
        }


        //CHANGE BUTTON TO RED
        button.setBackgroundResource(R.drawable.my_button_bg_wrong);

        //SUBTRACT 1 LIFE
        LifeCounter--;
        //UPDATE LIFE IMAGES
        updateLifeVisibility(LifeCounter);

        //CHECK IF LIFE IS NOT ZERO
        if (LifeCounter <= 0) {

            onGameOver();

        }

    }


    private void correctAnswer(ArrayList<Button> buttonsArray, Button myButton) {
        Correct_MP = MediaPlayer.create(this, R.raw.correct_answer_music);


        //Check if settings are disabling music
        if(getEffectsSettigns(context).equals("on")) {
            //PLAY SOUND and STOP IT
            Correct_MP.start();
        }


        setCustomToast("Correct", Color.parseColor("#327129"));


        //SET THE BACKGROUND OF THE BUTTON TO GREEN
        myButton.setBackgroundResource(R.drawable.my_button_bg_correct);
        //ADD 1 TO QCounter
        QCounter++;

        //LOOP TROUGH ALL BUTTONS AND SET THEIR BACKGROUND BACK TO ORIGINAL
        for (int i = 0; i < buttonsArray.size(); i++) {
            buttonsArray.get(i).setBackgroundResource(R.drawable.my_button_bg_menu);
        }
        //ADD 100 POINTS TO THE GENERAL SCORE
        ScoreCount += 100;
        //UPDATE THE SCORE VIEW
        scoreView.setText("Score: " + ScoreCount);
        //CANCEL OLD TIMER
        cancelTimer();
        //START NEXT QUESTION
        startNewQuestion(answerBTNArray, LifeCounter);

    }

    private void setCustomToast(String MYTEXT, int MYCOLOR) {

        Toast toast = Toast.makeText(context, MYTEXT, Toast.LENGTH_SHORT);
        View view = toast.getView();


//Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(MYCOLOR, PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.CENTER, 0, -600);
        toast.show();

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit to Main Menu")
                .setMessage("Are you sure you want to quit?\nThe score will be lost.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelTimer();
                        ScoreCount = 0;
                        LifeCounter = 3;
                        updateLifeVisibility(LifeCounter);

                        //STOP GAME PLAY MUSIC
                        stopGamePlayMusic(GamePlay_MP);

                        //GOING TO MAIN MENU
                        Intent intent = new Intent(GameActivity.this, MainMenu.class);
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


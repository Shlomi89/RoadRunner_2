package com.example.hw224a10357.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

import com.example.hw224a10357.Controllers.GameManager;
import com.example.hw224a10357.R;
import com.example.hw224a10357.Utility.SoundManager;
import com.example.hw224a10357.Utility.StepDetector;
import com.example.hw224a10357.interfaces.StepCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private AppCompatTextView main_LBL_score;
    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private ShapeableImageView[][] main_IMG_Obstacles;
    private ShapeableImageView[][] main_IMG_Coins;
    private ShapeableImageView[] main_IMG_Player;
    private ShapeableImageView[] main_IMG_hearts;
    private GameManager gameManager;

    private ShapeableImageView main_IMG_background;

    public static final String KEY_DELAY = "DELAY";
    public static final String KEY_MODE = "MODE";

    private StepDetector stepDetector;

    public static int DELAY;


    private static final int RIGHT = 1;
    private static final int LEFT = -1;

    private SoundManager soundManager;


    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY);
            updateObs();
        }
    };

    private void updateObs() {
        gameManager.obsStepFroward();
        refreshUI();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent prevScreen = getIntent();
        soundManager = new SoundManager(this,R.raw.crashing);
        DELAY = prevScreen.getIntExtra(KEY_DELAY, 1000);
        String mode = prevScreen.getStringExtra(KEY_MODE);
        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
//        refreshUI();
        gameManager.initMat();
        Glide.with(this).load(R.drawable.background).centerCrop().placeholder(R.drawable.ic_launcher_background).into(main_IMG_background);
        if (mode.equalsIgnoreCase("SENSORS")) {
            main_BTN_right.setVisibility(View.INVISIBLE);
            main_BTN_left.setVisibility(View.INVISIBLE);
            initSensors();
        } else {
            main_BTN_right.setOnClickListener(view -> directionClicked(RIGHT));
            main_BTN_left.setOnClickListener(view -> directionClicked(LEFT));
        }
        handler.postDelayed(runnable, 0);
    }

    private void initSensors() {
        this.stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepLeft() {
                directionClicked(LEFT);
            }

            @Override
            public void stepRight() {
                directionClicked(RIGHT);
            }

            @Override
            public void changeSpeed(int delay) {
                DELAY = delay;
            }


        });
        stepDetector.start();
    }

    private void directionClicked(int Direction) {
        gameManager.changeDirection(Direction);
        refreshUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);

    }


    private void refreshUI() {
        // lost:
        if (gameManager.isGameLost()) {
            changeActivity(gameManager.getPoints());
            if (stepDetector != null)
                stepDetector.stop();
        } else {
//            main_IMG_flag.setImageResource(gameManager.getCurrentCountry().getFlagImage());
            if (gameManager.checkIfCoinHit())
                main_LBL_score.setText(gameManager.getPoints() + " ");
            main_LBL_score.setText(gameManager.getPoints() + " ");
            if (gameManager.checkIfCrashed()) {
                toastAndVibrate("BOOM");
                soundManager.playSound();
                if (gameManager.getHits() != 0 && gameManager.getHits() <= 3)
                    main_IMG_hearts[main_IMG_hearts.length - gameManager.getHits()].setVisibility(View.INVISIBLE);
            }
            refreshObsPosition();
            refreshPlayerPosition();
            refreshCoinsPosition();
        }
    }

    private void changeActivity(int points) {
        Intent scoreIntent = new Intent(this, ScoreActivity.class);
        scoreIntent.putExtra(ScoreActivity.KEY_SCORE, points);
        startActivity(scoreIntent);
        handler.removeCallbacks(runnable);
        finish();
    }

    private void refreshPlayerPosition() {
        for (int i = 0; i < gameManager.getCols(); i++) {
            if (gameManager.getPlayerPos() == i)
                main_IMG_Player[i].setVisibility(View.VISIBLE);
            else
                main_IMG_Player[i].setVisibility(View.INVISIBLE);
        }
    }


    private void refreshObsPosition() {
        for (int i = 0; i < gameManager.getRows(); i++) {
            for (int j = 0; j < gameManager.getCols(); j++) {
                main_IMG_Obstacles[i][j].setVisibility(gameManager.getObsPos()[i][j]);
            }
        }
    }

    private void refreshCoinsPosition() {
        for (int i = 0; i < gameManager.getRows(); i++) {
            for (int j = 0; j < gameManager.getCols(); j++) {
                main_IMG_Coins[i][j].setVisibility(gameManager.getCoinPos()[i][j]);
            }
        }
    }


    private void toastAndVibrate(String st) {
        toast(st);
        vibrate();
    }


    private void toast(String st) {
        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
    }


    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_Left);
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_BTN_right = findViewById(R.id.main_BTN_Right);
        main_IMG_background = findViewById(R.id.main_IMG_background);
        main_IMG_Obstacles = new ShapeableImageView[][]{
                //ROW 1
                {findViewById(R.id.imageView_Obs_0_0),
                        findViewById(R.id.imageView_Obs_0_1),
                        findViewById(R.id.imageView_Obs_0_2),
                        findViewById(R.id.imageView_Obs_0_3),
                        findViewById(R.id.imageView_Obs_0_4)
                }
                ,
                //ROW 2
                {findViewById(R.id.imageView_Obs_1_0),
                        findViewById(R.id.imageView_Obs_1_1),
                        findViewById(R.id.imageView_Obs_1_2),
                        findViewById(R.id.imageView_Obs_1_3),
                        findViewById(R.id.imageView_Obs_1_4)}
                ,
                //ROW 3
                {findViewById(R.id.imageView_Obs_2_0),
                        findViewById(R.id.imageView_Obs_2_1),
                        findViewById(R.id.imageView_Obs_2_2),
                        findViewById(R.id.imageView_Obs_2_3),
                        findViewById(R.id.imageView_Obs_2_4)}

                ,
                //ROW 4
                {findViewById(R.id.imageView_Obs_3_0),
                        findViewById(R.id.imageView_Obs_3_1),
                        findViewById(R.id.imageView_Obs_3_2),
                        findViewById(R.id.imageView_Obs_3_3),
                        findViewById(R.id.imageView_Obs_3_4)}

                ,
                {findViewById(R.id.imageView_Obs_4_0),
                        findViewById(R.id.imageView_Obs_4_1),
                        findViewById(R.id.imageView_Obs_4_2),
                        findViewById(R.id.imageView_Obs_4_3),
                        findViewById(R.id.imageView_Obs_4_4)}

                ,
                {findViewById(R.id.imageView_Obs_5_0),
                        findViewById(R.id.imageView_Obs_5_1),
                        findViewById(R.id.imageView_Obs_5_2),
                        findViewById(R.id.imageView_Obs_5_3),
                        findViewById(R.id.imageView_Obs_5_4)}

                ,
                //Player Row
                {findViewById(R.id.imageView_Obs_Last_0),
                        findViewById(R.id.imageView_Obs_Last_1),
                        findViewById(R.id.imageView_Obs_Last_2),
                        findViewById(R.id.imageView_Obs_Last_3),
                        findViewById(R.id.imageView_Obs_Last_4),}
        };

        main_IMG_Coins = new ShapeableImageView[][]{
                //ROW 1
                {findViewById(R.id.imageView_Coin_0_0),
                        findViewById(R.id.imageView_Coin_0_1),
                        findViewById(R.id.imageView_Coin_0_2),
                        findViewById(R.id.imageView_Coin_0_3),
                        findViewById(R.id.imageView_Coin_0_4)
                }
                ,
                //ROW 2
                {findViewById(R.id.imageView_Coin_1_0),
                        findViewById(R.id.imageView_Coin_1_1),
                        findViewById(R.id.imageView_Coin_1_2),
                        findViewById(R.id.imageView_Coin_1_3),
                        findViewById(R.id.imageView_Coin_1_4)}
                ,
                //ROW 3
                {findViewById(R.id.imageView_Coin_2_0),
                        findViewById(R.id.imageView_Coin_2_1),
                        findViewById(R.id.imageView_Coin_2_2),
                        findViewById(R.id.imageView_Coin_2_3),
                        findViewById(R.id.imageView_Coin_2_4)}

                ,
                //ROW 4
                {findViewById(R.id.imageView_Coin_3_0),
                        findViewById(R.id.imageView_Coin_3_1),
                        findViewById(R.id.imageView_Coin_3_2),
                        findViewById(R.id.imageView_Coin_3_3),
                        findViewById(R.id.imageView_Coin_3_4)}

                ,
                {findViewById(R.id.imageView_Coin_4_0),
                        findViewById(R.id.imageView_Coin_4_1),
                        findViewById(R.id.imageView_Coin_4_2),
                        findViewById(R.id.imageView_Coin_4_3),
                        findViewById(R.id.imageView_Coin_4_4)}

                ,
                {findViewById(R.id.imageView_Coin_5_0),
                        findViewById(R.id.imageView_Coin_5_1),
                        findViewById(R.id.imageView_Coin_5_2),
                        findViewById(R.id.imageView_Coin_5_3),
                        findViewById(R.id.imageView_Coin_5_4)}

                ,
                //Player Row
                {findViewById(R.id.imageView_Coin_Last_0),
                        findViewById(R.id.imageView_Coin_Last_1),
                        findViewById(R.id.imageView_Coin_Last_2),
                        findViewById(R.id.imageView_Coin_Last_3),
                        findViewById(R.id.imageView_Coin_Last_4),}
        };


        main_IMG_Player = new ShapeableImageView[]{
                findViewById(R.id.imageView_Player_0),
                findViewById(R.id.imageView_Player_1),
                findViewById(R.id.imageView_Player_2),
                findViewById(R.id.imageView_Player_3),
                findViewById(R.id.imageView_Player_4),};


        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
    }
}
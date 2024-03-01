package com.example.hw224a10357.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.hw224a10357.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MenuActivity extends AppCompatActivity {


    private MaterialButton menu_BTN_Start;
    private MaterialButton menu_BTN_LeaderBoard;

    private LinearLayout menu_LAY_Speed;
    private MaterialButton menu_BTN_Slow;
    private MaterialButton menu_BTN_Fast;


    private MaterialButton menu_BTN_Button;
    private MaterialButton menu_BTN_Sensors;

    private boolean slowOrFast=false;
    private boolean buttonOrSensor=false;

    private int delay = 1000;

    private String mode = "Button";
    private ShapeableImageView main_IMG_background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        Glide.with(this).load(R.drawable.background).centerCrop().placeholder(R.drawable.ic_launcher_background).into(main_IMG_background);
        menu_BTN_Start.setOnClickListener(view -> changePlayActivity(delay,mode));
        menu_BTN_LeaderBoard.setOnClickListener(view -> changeLeaderboardActivity());
        menu_BTN_Slow.setOnClickListener(view -> changeSpeed());
        menu_BTN_Fast.setOnClickListener(view ->changeSpeed());
        menu_BTN_Button.setOnClickListener(view -> changeMode());
        menu_BTN_Sensors.setOnClickListener(view -> changeMode());
    }

    private void changeMode() {
        buttonOrSensor = !buttonOrSensor;
        if (buttonOrSensor) // Sensor mode chosen
        {
            menu_BTN_Button.setEnabled(true);
            menu_BTN_Sensors.setEnabled(false);
            menu_LAY_Speed.setVisibility(View.INVISIBLE);
            mode = "SENSORS";
        }
        else // Button mode chosen
        {
            menu_BTN_Sensors.setEnabled(true);
            menu_BTN_Button.setEnabled(false);
            mode = "Button";
            menu_LAY_Speed.setVisibility(View.VISIBLE);
        }
    }

    private void changeSpeed() {
        slowOrFast = !slowOrFast;
        if (slowOrFast) {
            delay = 500;
            menu_BTN_Slow.setEnabled(true);
            menu_BTN_Fast.setEnabled(false);
        } else {
            delay = 1000;
            menu_BTN_Fast.setEnabled(true);
            menu_BTN_Slow.setEnabled(false);
        }
    }

    private void findViews() {
        menu_BTN_Start = findViewById(R.id.menu_BTN_Start);
        main_IMG_background = findViewById(R.id.main_IMG_background);
        menu_BTN_Slow = findViewById(R.id.menu_BTN_Slow);
        menu_BTN_Fast = findViewById(R.id.menu_BTN_Fast);
        menu_BTN_Button = findViewById(R.id.menu_BTN_Button);
        menu_BTN_Sensors = findViewById(R.id.menu_BTN_Sensors);
        menu_BTN_LeaderBoard = findViewById(R.id.menu_BTN_Leaderboard);
        menu_LAY_Speed = findViewById(R.id.menu_LAY_Speed);
    }


    private void changePlayActivity(int delay ,String mode) {
        ;
        Intent GameIntent = new Intent(this, MainActivity.class);
        GameIntent.putExtra(MainActivity.KEY_DELAY, delay);
        GameIntent.putExtra(MainActivity.KEY_MODE, mode);
        startActivity(GameIntent);
        finish();
    }

    private void changeLeaderboardActivity() {
        Intent ScoreIntent = new Intent(this, LeaderboardActivity.class);
        startActivity(ScoreIntent);
    }


}
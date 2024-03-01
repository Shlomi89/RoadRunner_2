package com.example.hw224a10357.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.hw224a10357.Model.LeaderBoard;
import com.example.hw224a10357.Model.Player;
import com.example.hw224a10357.R;
import com.example.hw224a10357.Utility.DataManager;
import com.example.hw224a10357.Utility.SharedPreferencesManager;
import com.example.hw224a10357.Utility.SignalManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;


public class ScoreActivity extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String LEADERBOARD = "LEADERBOARD";


    private ShapeableImageView main_IMG_background;
    private LeaderBoard leaderBoard = DataManager.getLeaderboard();
    private Player player;
    private MaterialTextView score_LBL_score;
    private MaterialTextView score_LBL_NewHighScore;
    private TextInputEditText score_TXT_Name;
    private MaterialButton score_BTN_save;
    private MaterialButton score_BTN_menu;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        findViews();
        Intent previousScreen = getIntent();
        int score = previousScreen.getIntExtra(KEY_SCORE, 0);
        Glide.with(this).load(R.drawable.background).centerCrop().placeholder(R.drawable.ic_launcher_background).into(main_IMG_background);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();
        refreshUI(score);
        score_BTN_menu.setOnClickListener(v -> changeActivity());
    }

    private void Save(String name, int points) {
        if (name.isEmpty()) {
            SignalManager.getInstance().toast("Please Fill YOu Name");
        } else {
            getCurrentLocation();
            player = new Player(name, points, currentLocation.getLatitude(), currentLocation.getLongitude());
            Log.d("LEADERBOARD BEFORE SAVE", leaderBoard.toString());
            leaderBoard.addNewPlayer(player);
            Log.d("LEADERBOARD BEFORE SAVE", leaderBoard.toString());
            SharedPreferencesManager.getInstance().putString(LEADERBOARD, new Gson().toJson(leaderBoard));
            SignalManager.getInstance().toast("Score Saved");
            changeActivity();
        }
    }

    private void changeActivity() {
        Intent MenuIntent = new Intent(this, MenuActivity.class);
        startActivity(MenuIntent);
        finish();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    currentLocation =location;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getCurrentLocation();
            } else
                finish();
        }
    }


    private void refreshUI(int score) {
        if (CheckIfHighScore(score)) {
            score_LBL_NewHighScore.setVisibility(View.VISIBLE);
            score_TXT_Name.setVisibility(View.VISIBLE);
            score_BTN_save.setVisibility(View.VISIBLE);
            score_BTN_save.setOnClickListener(view -> Save(score_TXT_Name.getText().toString(), score));
        }
        score_LBL_score.setText("Score" + "\n" + score);
    }

    private boolean CheckIfHighScore(int points) {
        return DataManager.getLeaderboard().CanEnterToLeaderBoard(points);
    }


    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        score_LBL_score = findViewById(R.id.score_LBL_score);
        score_LBL_NewHighScore = findViewById(R.id.score_LBL_NewHighScore);
        score_TXT_Name = findViewById(R.id.score_TXT_Name);
        score_BTN_save = findViewById(R.id.score_BTN_save);
        score_BTN_menu = findViewById(R.id.score_BTN_menu);
    }
}
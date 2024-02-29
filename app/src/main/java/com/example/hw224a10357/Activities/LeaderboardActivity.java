package com.example.hw224a10357.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.hw224a10357.Activities.Fragments.ListFragment;
import com.example.hw224a10357.Activities.Fragments.MapFragment;
import com.example.hw224a10357.Model.Player;
import com.example.hw224a10357.R;
import com.example.hw224a10357.interfaces.Callback_highScoreClicked;

public class LeaderboardActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private FrameLayout leader_FRAME_list;
    private FrameLayout  leader_FRAME_map;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        findViews();

        listFragment= new ListFragment();
        listFragment.setCallbackHighScoreClicked(new Callback_highScoreClicked() {
            @Override
            public void highScoreClicked(Player player,int position) {
                mapFragment.showOnMap(player);
            }
        });
        mapFragment= new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.leader_FRAME_list,listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.leader_FRAME_map,mapFragment).commit();
    }

    private void findViews() {
        leader_FRAME_list =findViewById(R.id.leader_FRAME_list);
        leader_FRAME_map = findViewById(R.id.leader_FRAME_map);
    }


}
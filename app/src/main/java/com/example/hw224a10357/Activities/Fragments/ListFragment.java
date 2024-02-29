package com.example.hw224a10357.Activities.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw224a10357.Adapters.PlayerAdapter;
import com.example.hw224a10357.Model.LeaderBoard;
import com.example.hw224a10357.Model.Player;
import com.example.hw224a10357.R;
import com.example.hw224a10357.Utility.DataManager;
import com.example.hw224a10357.Utility.SignalManager;
import com.example.hw224a10357.interfaces.Callback_highScoreClicked;

import java.util.Arrays;
import java.util.Objects;

public class ListFragment extends Fragment {

    private RecyclerView recyclerview_list_players;
    private Callback_highScoreClicked callbackHighScoreClicked;

    public void setCallbackHighScoreClicked(Callback_highScoreClicked callbackHighScoreClicked) {
        this.callbackHighScoreClicked = callbackHighScoreClicked;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        return view;
    }


    private void initViews() {
        Player[] players = DataManager.getLeaderboard().getHighScores();
        Log.d(" Players", Arrays.toString(players));
        PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), DataManager.getLeaderboard().getHighScores());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        if (callbackHighScoreClicked != null)
            playerAdapter.setCallbackHighScoreClicked(callbackHighScoreClicked);
        recyclerview_list_players.setLayoutManager(linearLayoutManager);
        recyclerview_list_players.setAdapter(playerAdapter);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

//    private void ItemClicked(Player player){
//        if (callbackHighScoreClicked!=null)
//            callbackHighScoreClicked.highScoreClicked(player);
//    }


    private void findViews(View view) {
        recyclerview_list_players = view.findViewById(R.id.recyclerview_list_players);

    }
}
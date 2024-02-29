package com.example.hw224a10357.Utility;

import com.example.hw224a10357.Model.LeaderBoard;
import com.google.gson.Gson;

public class DataManager {

    public static LeaderBoard getLeaderboard(){
        LeaderBoard lead= (LeaderBoard) new Gson().fromJson(SharedPreferencesManager.getInstance().getString("LEADERBOARD", ""), LeaderBoard.class);
        if (lead.getHighScores()[0] == null) {
             lead= new LeaderBoard();
        }
        return lead;
    }
}

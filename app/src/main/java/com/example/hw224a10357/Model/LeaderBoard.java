package com.example.hw224a10357.Model;

import java.util.Arrays;

public class LeaderBoard {

    private Player[] highPlayers = new Player[10];

    public LeaderBoard() {
        for (int i = 0; i < highPlayers.length; i++) {
            highPlayers[i] = new Player();
        }
    }



    public Player[] getHighScores() {
        return highPlayers;
    }


    /**
     * Add New Score To LeaderBoard
     *
     * @param player= new score to be added
     */
    public void addNewPlayer(Player player) {
        for (int i = 0; i < highPlayers.length; i++) {
            if (highPlayers[i].getPoints() < player.getPoints()) {
                addPos(i, player);
                return;
            }
        }
    }


    private void addPos( int pos, Player value) {
        // initially set to value parameter so the first iteration, the value is replaced by it
        Player prevValue = value;
        Player tmp;
        // Shift all elements to the right, starting at pos
        for (int i = pos; i < highPlayers.length; i++) {
            tmp = prevValue;
            prevValue = highPlayers[i];
            highPlayers[i] = tmp;
        }
    }


    public boolean CanEnterToLeaderBoard(int points) {
        return highPlayers[9].getPoints() < points;
    }


    @Override
    public String toString() {
        return "LeaderBoard{" +
                "leaderBoard=" + Arrays.toString(highPlayers) +
                '}';
    }
}

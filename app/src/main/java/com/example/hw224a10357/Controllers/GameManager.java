package com.example.hw224a10357.Controllers;


import android.view.View;

import com.example.hw224a10357.Utility.SoundManager;

import java.util.Arrays;

public class GameManager {


    private int points = 0;
    private int hits = 0;
    private int life;

    private final int rows = 7;
    private final int cols = 5;

    private int playerPos = 2;

    private boolean isBlank = false;

    private static final int COYOTE = View.VISIBLE;
    private static final int COIN = View.VISIBLE;
    private static final int BLANK = View.INVISIBLE;
    private static final int MAXCOINS = 2;

    private int[][] obsPos = new int[rows][cols];
    private int[][] coinPos = new int[rows][cols];


    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public GameManager(int life) {
        this.life = life;
    }


    public int getPoints() {
        return points;
    }


    public int getHits() {
        return hits;
    }

    public int getLife() {
        return life;
    }

    public int[][] getObsPos() {
        return obsPos;
    }

    public int[][] getCoinPos() {
        return coinPos;
    }



    public boolean isGameLost() {
        return getLife() == getHits();
    }

    public int getPlayerPos() {
        return playerPos;
    }

    public void setPlayerNewPos(int playerPos) {
        this.playerPos = this.playerPos + playerPos;
    }

    public void changeDirection(int direction) {
        if (getPlayerPos() + direction < cols && getPlayerPos() + direction >= 0)
            setPlayerNewPos(direction);
    }

    public boolean checkIfCrashed() {
        if (obsPos[rows - 1][playerPos] == COYOTE) {
            hits++;
            return true;
        }
        return false;
    }

    public boolean checkIfCoinHit() {
        if (coinPos[rows - 1][playerPos] == COIN) {
            points += 100;
            removeCoin();
            return true;
        }
        return false;
    }

    private void removeCoin(){
        coinPos[rows-1][playerPos] = BLANK;
    }

    public void initMat() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(obsPos[i], View.INVISIBLE);
            Arrays.fill(coinPos[i], View.INVISIBLE);
        }
    }

    private int[] createNewObsLine() {

        int[] line = new int[cols];
        for (int i = 0; i < cols; i++) {
            if (getRandomBollean())
                line[i] = COYOTE;
            else
                line[i] = BLANK;
        }
        if (Arrays.stream(line).sum() == COYOTE)
            return createNewObsLine();
        return line;
    }

    private int[] createNewBlankLine() {
        int[] line = new int[cols];
        for (int i = 0; i < cols; i++) {
            line[i] = View.INVISIBLE;
        }
        return line;
    }


    public void obsStepFroward() {
        for (int i = rows - 2; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                obsPos[i + 1][j] = obsPos[i][j];
                coinPos[i + 1][j] = coinPos[i][j];
            }
        }
        if (isBlank) {
            obsPos[0] = createNewBlankLine();
            triggerisBlank();
        } else {
            obsPos[0] = createNewObsLine();
            triggerisBlank();
        }
        createNewCoinLine();
        points +=10;
    }

    private void createNewCoinLine() {
        int numCoins = 0;
        for (int i = 0; i < cols; i++) {
            if (obsPos[0][i] == BLANK) {
                if (getRandomCoinBollean() && numCoins < MAXCOINS) {
                    coinPos[0][i] = COIN;
                    numCoins++;
                }
                else
                    coinPos[0][i] = BLANK;
            } else
                coinPos[0][i] = BLANK;
        }
    }

    private void triggerisBlank() {
        if (isBlank)
            isBlank = false;
        else
            isBlank = true;
    }


    public boolean getRandomBollean() {
        return Math.random() < 0.5;
    }


    public boolean getRandomCoinBollean() {
        return Math.random() < 0.3;
    }

}


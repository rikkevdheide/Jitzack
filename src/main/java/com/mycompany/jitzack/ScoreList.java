package com.mycompany.jitzack;

import java.util.ArrayList;
import java.util.Objects;

public class ScoreList {

    private final String playerName;
    private final ArrayList<Integer> scoreList;
    private final int scoreToWin;

    public ScoreList(String playerName, int scoreToWin) {
        this.playerName = playerName;
        this.scoreToWin = scoreToWin;
        this.scoreList = new ArrayList<>();
    }

    public void addPoints(int points) {
        this.scoreList.add(points);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (int points : this.scoreList) {
            totalScore += points;
        }
        return totalScore;
    }

    public int getPlayedRounds() {
        return this.scoreList.size();
    }

    public boolean reachedScoreToWin() {
        return (this.getTotalScore() >= this.scoreToWin);
    }
    
   

    @Override
    public String toString() {
        if (this.getPlayedRounds() == 1) {
            return this.playerName + " heeft " + this.getTotalScore() + " strafpunten uit " + this.getPlayedRounds() + " ronde";
        }
        return this.playerName + " heeft " + this.getTotalScore() + " strafpunten uit " + this.getPlayedRounds() + " ronden";
    }

}

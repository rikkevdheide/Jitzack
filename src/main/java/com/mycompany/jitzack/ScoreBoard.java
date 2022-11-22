package com.mycompany.jitzack;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoard {

    private final ArrayList<ScoreList> playerList;
    private ArrayList<ScoreList> rankedPlayerList;
    private final int scoreToLose;

    //Constructor to create ScoreBoard with score to lose
    public ScoreBoard(int scoreToLose) {
        this.scoreToLose = scoreToLose;
        this.playerList = new ArrayList<>();
        this.rankedPlayerList = new ArrayList<>();
    }

    //Return playerList
    public ArrayList<ScoreList> getPlayerList() {
        return playerList;
    }

    //Return ranked playerList
    public ArrayList<ScoreList> getRankedPlayerList() {
        return rankedPlayerList;
    }

    //Add new player to score board
    public String addPlayer(String playerName) {
        //Check that playerName isn't empty
        if (playerName.equals("")) {
            return "Spelernaam is niet gevuld.";
        }

        //Check that player doesn't exists
        int playerIndex = this.findPlayerIndexByName(playerName);
        if (playerIndex >= 0) {
            return "Speler " + playerName + " bestaat al.";
        }

        //Check that game isn't started yet
        if (this.maxRoundsActivePlayers() > 0) {
            return playerName + " moet even wachten, dit spel is al gestart.";
        }

        //Add new player
        ScoreList newPlayer = new ScoreList(playerName, this.scoreToLose);
        this.playerList.add(newPlayer);
        //this.ranking.add(newPlayer);
        return "Speler " + playerName + " is tegevoegd.";
    }

    //Add points to player
    public String addPoints(String playerName, int points) {

        //Check that provided player name exists
        int playerIndex = this.findPlayerIndexByName(playerName);
        if (playerIndex < 0) {
            return playerName + " speelt niet mee.";
        }

        //Check that players are in the same round
        ScoreList player = this.playerList.get(playerIndex);
        if ((player.getPlayedRounds() > this.minRoundsActivePlayers())
                || (this.maxRoundsActivePlayers() - this.minRoundsActivePlayers()) > 1) {
            return playerName + " wacht tot iedereen aan de beurt is geweest.";
        }

        //Check that score to win isn't reached yet
        if (player.reachedScoreToWin()) {
            return playerName + " heeft al de " + this.scoreToLose + " strafpunten gehaald.";
        }

        //Add points to player
        player.addPoints(points);

        return points + " strafpunten toegevoegd voor " + playerName;
    }

    //Creates the rankedPlayerList based on the playerlists player
    //Ranking is based on played rounds and total score
    public void rankPlayers() {
        rankedPlayerList = (ArrayList<ScoreList>) playerList.clone();
        this.rankedPlayerList.sort(Comparator
                .comparing(ScoreList::getPlayedRounds).reversed()
                .thenComparing(ScoreList::getTotalScore).reversed());
    }

    //Return player index based on player name
    private int findPlayerIndexByName(String playerName) {
        for (ScoreList player : this.playerList) {
            if (player.getPlayerName().equals(playerName)) {
                return playerList.indexOf(player);
            }
        }
        return -1;
    }

    //Return most rounds played by active players
    public int maxRoundsActivePlayers() {

        if (this.playerList.isEmpty()) {
            return -1;
        }

        int maxRounds = playerList.get(0).getPlayedRounds();
        for (ScoreList player : playerList) {
            if (maxRounds < player.getPlayedRounds() && !player.reachedScoreToWin()) {
                maxRounds = player.getPlayedRounds();
            }
        }
        return maxRounds;
    }

    //Return least rounds played by active players
    public int minRoundsActivePlayers() {

        if (this.playerList.isEmpty()) {
            return -1;
        }

        int minRounds = this.maxRoundsActivePlayers();
        for (ScoreList player : playerList) {
            if (minRounds > player.getPlayedRounds() && !player.reachedScoreToWin()) {
                minRounds = player.getPlayedRounds();
            }
        }
        return minRounds;
    }

    //Return the number of finished players
    public int finishedPlayers() {
        int finishedPlayers = 0;
        for (ScoreList player : playerList) {
            if (player.reachedScoreToWin()) {
                finishedPlayers++;
            }
        }
        return finishedPlayers;
    }

    //Returns the ranked player list as string
    //Additional string will be placed after player list line when player is finished
    public String toString(String additionalText) {
        String result = "";
        int i = 0;
        while (rankedPlayerList.size() > i) {

            if (rankedPlayerList.get(i).reachedScoreToWin()) {
                result += (i + 1) + ": " + rankedPlayerList.get(i) + additionalText + "\n";
            } else {
                result += (i + 1) + ": " + rankedPlayerList.get(i) + "\n";
            }
            i++;
        }
        return result;
    }
    
    //Returns the ranked player list as string
    @Override
    public String toString() {
        String result = "";
        int i = 0;
        while (rankedPlayerList.size() > i) {
            result += (i + 1) + ": " + rankedPlayerList.get(i) + "\n";
            i++;
        }
        return result;
    }
}

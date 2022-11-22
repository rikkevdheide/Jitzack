package com.mycompany.jitzack;

import java.util.Scanner;

public class UserInterface {

    Scanner reader;
    ScoreBoard scoreBoard;

    public UserInterface() {
        this.reader = new Scanner(System.in);
    }

    public void Start() {
        this.gameSetUp();
        this.addPlayers();
        this.playRounds();
        this.endGame();

    }

    private void gameSetUp() {
        System.out.println("== WELKOM BIJ JITZACK! ==");
        Integer scoreToLose = requestValidInteger("Tot hoeveel strafpunten spelen we?");
        this.scoreBoard = new ScoreBoard(scoreToLose);
        System.out.println("De laatste speler die " + scoreToLose + " strafpunten haalt wint het spel.\n");
    }

    private void addPlayers() {
        Boolean addMorePlayers = true;
        while (addMorePlayers) {
            System.out.println("Voer de naam in van speler " + (scoreBoard.getPlayerList().size() + 1) + ":");
            String playerName = reader.nextLine();
            System.out.println(this.scoreBoard.addPlayer(playerName));

            if (scoreBoard.getPlayerList().size() <= 1) {
                System.out.println("");
                continue;
            }

            addMorePlayers = yesIsTrue("Wil je nog meer spelers toevoegen? (J / N)");
            System.out.println("");
        }
        System.out.println("\nLet's deal the cards!");
        reader.nextLine();
    }

    //Method to play rounds
    private void playRounds() {
        Boolean continuePlaying = true;
        int finishedPlayers = 0;

        while (continuePlaying) {
            System.out.println("== PUNTENTELLING RONDE " + (scoreBoard.maxRoundsActivePlayers() + 1) + " == ");

            for (ScoreList player : scoreBoard.getPlayerList()) {
                if (!player.reachedScoreToWin()) {
                    Integer points = requestValidInteger("Voer behaalde strafpunten in voor " + player.getPlayerName() + ":");
                    scoreBoard.addPoints(player.getPlayerName(), points);
                    System.out.println("");
                }
            }

            if (scoreBoard.finishedPlayers() == scoreBoard.getPlayerList().size()) {
                break;
            }

            scoreBoard.rankPlayers();
            System.out.println("== STAND NA RONDE " + scoreBoard.maxRoundsActivePlayers() + " ==\n" + scoreBoard.toString(" == SPEELT NIET MEER MEE == "));
            System.out.println("");

            if (scoreBoard.finishedPlayers() > finishedPlayers) {
                continuePlaying = yesIsTrue("Doorspelen? (J / N)");
                finishedPlayers = scoreBoard.finishedPlayers();
            }

        }
    }

    private void endGame() {
        System.out.println("Het spel is afgelopen");
        System.out.println("== EINDSTAND ==\n" + scoreBoard);
    }

    private boolean yesIsTrue(String question) {
        System.out.println(question);
        while (true) {
            String response = reader.nextLine();

            //N returns false
            if (response.equalsIgnoreCase("N")) {
                return false;
            }

            //Check for invalid response
            if (!"J".equalsIgnoreCase(response)) {
                System.out.println("Ongeldige invoer. " + question);
                continue;
            }

            //Y returns true
            return true;
        }
    }

    private Integer requestValidInteger(String question) {
        System.out.println(question);
        while (true) {
            String scoreToLoseString = reader.nextLine();
            try {
                return Integer.valueOf(scoreToLoseString);
            } catch (NumberFormatException e) {
                System.out.println("Ongeldige invoer. " + question);
                continue;
            }
        }
    }
}

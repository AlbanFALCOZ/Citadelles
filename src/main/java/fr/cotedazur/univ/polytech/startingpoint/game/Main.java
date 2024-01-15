package fr.cotedazur.univ.polytech.startingpoint.game;


import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * cette classe permet de jouer une partie de Citadelle et de déterminer le gagnant
 */
public class Main {


    public static void main (String[] args) {
        //testBots();
        showGame();
    }

    public static void showGame() {
        GameEngine Game = new GameEngine();
        Game.gameTurns();
        Winner winner = new Winner(Game.getBots());
        System.out.println(winner.printScore());
        System.out.println(winner.showWinners());
    }

    public static void testBots() {
        long start = System.currentTimeMillis();
        int[] listWinners = new int[4];
        int numberOfGames = 10000;
        for (int i = 0; i < numberOfGames; i++) {

            GameEngine Game = new GameEngine(false);
            Game.gameTurns();
            Winner winner = new Winner(Game.getBots());
            for (String winners : winner.getWinners()) {
                if (winners.equals("Alban")) {
                    listWinners[0]++;
                }
                if (winners.equals("Sara")) {
                    listWinners[1]++;
                }
                if (winners.equals("Stacy")) {
                    listWinners[2]++;
                }
                if (winners.equals("Nora")) {
                    listWinners[3]++;
                }
            }
            //System.out.println(winner.calculateScores());
            //System.out.println(winner.showWinners());
        }

        for (int i = 0; i < listWinners.length; i++) {
            System.out.println("Winrate N°" + i + " : " + (float)listWinners[i]/numberOfGames*100 + "%");
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed/1000 + "sec");
    }

}

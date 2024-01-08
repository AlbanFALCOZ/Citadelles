package fr.cotedazur.univ.polytech.startingpoint.game;


import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {

        long start = System.currentTimeMillis();
        int[] listWinners = new int[4];
// ...
        for (int i = 0; i < 1000; i++) {
            GameEngine Game = new GameEngine(false);
            Game.gameTurns();
            Winner winner = new Winner(Game.getBots());
            for (String winners : winner.getWinners()) {
                if (winners.equals("Nora")) {
                    listWinners[0]++;
                }
                if (winners.equals("Stacy")) {
                    listWinners[1]++;
                }
                if (winners.equals("Alban")) {
                    listWinners[2]++;
                }
                if (winners.equals("Sara")) {
                    listWinners[3]++;
                }
            }
            //System.out.println(winner.calculateScores());
            //System.out.println(winner.showWinners());
        }

        for (int i = 0; i < listWinners.length; i++) {
            System.out.println("Winner N°" + i + " : " + listWinners[i]);
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed/1000 + "sec");

    }



}

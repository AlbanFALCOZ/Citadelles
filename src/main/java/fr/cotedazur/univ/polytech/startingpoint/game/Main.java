package fr.cotedazur.univ.polytech.startingpoint.game;


import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.List;

/**
 * cette classe permet de jouer une partie de Citadelle et de déterminer le gagnant
 */
public class Main {

    /**
     * @param args les arguments
     * cette méthode permet de jouer une partie de Citadelle et de déterminer le gagnant
     */
    public static void main (String[] args) {

        GameEngine Game = new GameEngine();
        Game.gameTurns();
        Winner winner = new Winner(Game.getBots());
        System.out.println(winner.calculateScores());
        System.out.println(winner.showWinners());


    }



}

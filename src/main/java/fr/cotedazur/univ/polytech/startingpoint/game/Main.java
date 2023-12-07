package fr.cotedazur.univ.polytech.startingpoint.game;


import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.List;

public class Main {

    public static void main (String[] args) {


        GameEngine Game = new GameEngine();
        Game.assignRandomCharacterToRobots();
        Game.specialCard();
        Game.playTurns();
        Game.calculateScores();
        List<String> winner = Game.getWinners();
        System.out.println("Le gagnant de la partie est " + winner);


    }



}

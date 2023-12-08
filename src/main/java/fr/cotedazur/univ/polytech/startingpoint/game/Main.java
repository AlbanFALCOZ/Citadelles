package fr.cotedazur.univ.polytech.startingpoint.game;


import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.List;

public class Main {

    public static void main (String[] args) {


        GameEngine Game = new GameEngine();
        Game.gameTurns();
        Game.calculateScores();
        Game.showWinners();


    }



}

package fr.cotedazur.univ.polytech.startingpoint.game;


import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

public class Main {

    public static void main (String[] args) {


        GameEngine Game = new GameEngine();
        Game.assignRandomCharacterToRobots();
        Game.specialCard();
        Game.playTurns();
        Game.calculateScores();
        String winner = Game.getWinner();
        System.out.println("Le gagnant de la partie est " + winner);


    }



}

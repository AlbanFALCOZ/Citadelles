package fr.cotedazur.univ.polytech.startingpoint.game;


import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotRandom;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


class WinnerTest {
    private GameEngine gameEngine;
    private Winner winner;

    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine();
        winner = new Winner(gameEngine.getBots());
    }

    @Test
    void testCalculateScores() {
        gameEngine.robotsPickCharacters();
        gameEngine.assignCrown();
        gameEngine.robotsPickCharacters();
        Round round = new Round(gameEngine.getBots());
        round.playTurns();
        winner.printScore();
        int maxScore = 0;
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() > maxScore) {
                maxScore = bot.calculateScore();
            }
        }
        List<String> winners = winner.getWinners();
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() == maxScore) {
                assertTrue(winners.contains(bot.getName()));
            }
        }
    }

    /*
    @Test
    void testGetWinner() {
        gameEngine.robotsPickCharacters();
        gameEngine.assignCrown();
        gameEngine.robotsPickCharacters();
        Round round = new Round(gameEngine.getBots());
        round.playTurns();
        winner.calculateScores();
        int maxScore = 0;
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() > maxScore) {
                maxScore = bot.calculateScore();
            }
        }
        List<String> winners = winner.getWinners();
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() == maxScore) {
                assertTrue(winners.contains(bot.getName()));
            }
        }
    }
    */


    @Test
    void testShowWinners() {
        Robot robot1 = new RobotRandom("Robot1");
        Robot robot2 = new RobotRandom("Robot2");
        robot1.setScore(10);
        robot2.setScore(10);
        Winner winner = new Winner(List.of(robot1, robot2));
        assertEquals("There is an equality! The winners are: Robot1, Robot2", winner.showWinners());

    }


}




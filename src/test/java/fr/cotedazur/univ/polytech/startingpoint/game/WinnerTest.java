package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
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
        gameEngine.assignRandomCharacterToRobots();
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

    @Test
    void testGetWinner() {
        gameEngine.assignRandomCharacterToRobots();
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


}




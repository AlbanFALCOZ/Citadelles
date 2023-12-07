package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    private GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine();
    }


    void testInitializeBots() {
        for (Robot bot : gameEngine.getBots()) {
            assertEquals(4, bot.getNumberOfDistrictInHand(), "Chaque robot doit avoir 4 districts uniques");
        }
    }

    @Test
    void assignRandomCharacterToRobots() {
        gameEngine.assignRandomCharacterToRobots();
        Set<CharactersType> characters = new HashSet<>();
        for (Robot bot : gameEngine.getBots()) {
            characters.add(bot.getCharacter());
        }
        assertEquals(4, characters.size());
    }

    @Test
    void testassignCrown() {
        gameEngine.assignRandomCharacterToRobots();
        gameEngine.assignCrown();
        int numberOfCrown = 0;
        for (Robot bot : gameEngine.getBots()) {
            if (bot.getHasCrown()) {
                numberOfCrown++;
            }
        }
        assertEquals(1, numberOfCrown);
    }

    @Test
    void testPlayTurns() {
        gameEngine.assignRandomCharacterToRobots();
        gameEngine.playTurns();

    }

    @Test
    void testCalculateScores() {
        gameEngine.assignRandomCharacterToRobots();
        gameEngine.playTurns();
        gameEngine.calculateScores();
        int maxScore = 0;
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() > maxScore) {
                maxScore = bot.calculateScore();
            }
        }
        String winner = gameEngine.getWinner();
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() == maxScore) {
                assertTrue(bot.getName().equals(winner));
            }
        }
    }

    @Test
    void testGetWinner() {
        gameEngine.assignRandomCharacterToRobots();
        gameEngine.playTurns();
        gameEngine.calculateScores();
        int maxScore = 0;
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() > maxScore) {
                maxScore = bot.calculateScore();
            }
        }
        String winner = gameEngine.getWinner();
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() == maxScore) {
                assertTrue(bot.getName().equals(winner));
            }
        }
    }


}

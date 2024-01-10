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

    @Test
    void testInitializeBots() {
        for (Robot bot : gameEngine.getBots()) {
            assertEquals(4, bot.getNumberOfDistrictInHand(), "Chaque robot doit avoir 4 districts uniques");
        }
    }

    @Test
    void testassignCrown() {
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
    void testRobotsPickCharacters() {
        gameEngine.assignCrown();
        gameEngine.robotsPickCharacters();
        Set<CharactersType> characters = new HashSet<>();
        for (Robot bot : gameEngine.getBots()) {
            characters.add(bot.getCharacter());
        }
        assertEquals(4, characters.size(), "Chaque robot doit avoir un personnage unique");
    }

    @Test
    void testClearBots() {
        gameEngine.clearBots();
        assertEquals(0, gameEngine.getBots().size(), "La liste des robots doit être vide");
    }

    @Test
    void testAddRobot() {
        gameEngine.clearBots();
        Robot robot = new Robot("Robot");
        gameEngine.addRobot(robot);
        assertEquals(1, gameEngine.getBots().size(), "La liste des robots doit contenir un robot");
    }

    @Test
    void testIsBuiltEigthDistrict() {
        gameEngine.assignCrown();
        gameEngine.robotsPickCharacters();
        Round round = new Round(gameEngine.getBots());
        gameEngine.gameTurns();
        assertTrue(gameEngine.isBuiltEigthDistrict(), "Le jeu doit être terminé");
    }

}
package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
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
    void assignRandomCharacterToRobots() {
        gameEngine.assignRandomCharacterToRobots();
        Set<CharactersType> characters = new HashSet<>();
        for (Robot bot : gameEngine.getBots()) {
            characters.add(bot.getCharacter());
        }
        assertEquals(4, characters.size());
    }


    /*
    @Test
    void testPlayTurns() {
        gameEngine.initializeBots();
        gameEngine.playTurns();
        for (Robot bot : gameEngine.getBots()) {
            assertEquals(4, bot.getGolds(), "Le robot doit avoir 4 golds après un tour.");
        }
    }*/


    @Test
    void testPlayTurns() {
        gameEngine.playTurns();

        //si les robots ont 4 golds après le tour.
        for (Robot bot : gameEngine.getBots()) {
            assertEquals(4, bot.getGolds(), "Le robot doit avoir 4 golds après un tour.");
        }
    }
}

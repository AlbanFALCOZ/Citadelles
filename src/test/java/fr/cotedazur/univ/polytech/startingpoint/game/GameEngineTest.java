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
    void testPlayTurns() {
        gameEngine.playTurns();

        //si les robots ont 4 golds après le tour.
        for (Robot bot : gameEngine.getBots()) {
            assertEquals(4, bot.getGolds(), "Le robot doit avoir 4 golds après un tour.");
        }
    }

    @Test
    public void testSortRobots() {
        GameEngine gameEngine = new GameEngine();
        gameEngine.initializeBots();
        gameEngine.assignRandomCharacterToRobots();
        List<Robot> originalOrder = new ArrayList<>(gameEngine.getBots());
        ArrayList<Robot> sortedRobots = gameEngine.sortRobots();
        assertEquals(originalOrder, gameEngine.getBots());
        for (int i = 1; i < sortedRobots.size(); i++) {
            assertTrue(sortedRobots.get(i - 1).getCharacter().getNumber() <= sortedRobots.get(i).getCharacter().getNumber());
        }
    }
}

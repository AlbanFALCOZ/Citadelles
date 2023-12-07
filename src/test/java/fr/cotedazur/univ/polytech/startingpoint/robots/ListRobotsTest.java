package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.game.GameEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListRobotsTest {

    @Test
    public void testSortRobots() {
        ListRobots listRobots = new ListRobots();
        GameEngine gameEngine = new GameEngine();
        gameEngine.initializeBots();
        gameEngine.assignRandomCharacterToRobots();

        listRobots.addAll(gameEngine.getBots());

        List<Robot> originalOrder = new ArrayList<>(listRobots);

        listRobots.sortRobots();

        assertEquals(originalOrder, new ArrayList<>(listRobots));

        for (int i = 1; i < listRobots.size(); i++) {
            assertTrue(listRobots.get(i - 1).getCharacter().getNumber() <= listRobots.get(i).getCharacter().getNumber());
        }

    }

}
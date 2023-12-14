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
        List<String> winners = gameEngine.getWinners();
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() == maxScore) {
                assertTrue(winners.contains(bot.getName()));
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
        List<String> winners = gameEngine.getWinners();
        for (Robot bot : gameEngine.getBots()) {
            if (bot.calculateScore() == maxScore) {
                assertTrue(winners.contains(bot.getName()));
            }
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



    @Test
    public void testSingleWinner() {
        GameEngine game = new GameEngine();
        game.clearBots();
        Robot robot1 = new Robot("Robot1"); // Score élevé
        robot1.addDistrict(DistrictsType.PALAIS);
        robot1.setGolds(6);
        robot1.tryBuild();
        game.addRobot(robot1);
        Robot robot2 = new Robot("Robot2"); // Score inférieur
        robot2.addDistrict(DistrictsType.MANOIR);
        robot2.setGolds(6);
        robot2.tryBuild();
        game.addRobot(robot2);
        List<String> winners = game.getWinners();
        //System.out.println(winners);
        assertEquals(1, winners.size());
        assertEquals("Robot1", winners.get(0));
    }


    @Test
    public void testMultipleWinners() {
        GameEngine game = new GameEngine();

        Robot robot1 = new Robot("Robot1");
        robot1.addDistrict(DistrictsType.MONASTERE);
        robot1.setGolds(6);
        robot1.tryBuild();
        game.addRobot(robot1);

        Robot robot2 = new Robot("Robot2");
        robot2.addDistrict(DistrictsType.MANOIR);
        robot2.setGolds(6);
        robot2.tryBuild();
        game.addRobot(robot2);

        List<String> winners = game.getWinners();
        assertTrue(winners.size() > 1);
    }

    @Test
    public void testSpecialCard() {
        GameEngine game = new GameEngine();
        game.clearBots();

        Robot robot1 = new Robot("Robot1");
        robot1.setCharacter(CharactersType.MARCHAND);
        robot1.addDistrict(DistrictsType.TAVERNE);
        robot1.setGolds(6);
        robot1.tryBuild();
        game.addRobot(robot1);

        game.specialCard();

        assertEquals(6, robot1.getGolds());
    }






}

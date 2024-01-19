package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotRandom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundTest {

    @Test
    void testGetBots() {

        RobotRandom robot1 = new RobotRandom("Robot1");
        RobotRandom robot2 = new RobotRandom("Robot2");
        RobotRandom robot3 = new RobotRandom("Robot3");
        List<Robot> initialBots = List.of(robot1, robot2, robot3);
        Round round = new Round(initialBots);

        List<Robot> retrievedBots = round.getBots();


        assertEquals(initialBots, retrievedBots);
    }



    @Test
    void testSortRobots() {
        // Arrange
        RobotRandom nobleRobot1 = new RobotRandom("Franz Kafka");
        nobleRobot1.setCharacter(CharactersType.ROI);
        nobleRobot1.setHasCrown(true);

        RobotRandom nobleRobot2 = new RobotRandom("Leo Tolstoy");
        nobleRobot2.setCharacter(CharactersType.ROI);
        nobleRobot2.setHasCrown(false);

        RobotRandom openRobot = new RobotRandom("Simone De Beauvoir");
        openRobot.setCharacter(CharactersType.ASSASSIN);

        Round round = new Round(List.of(nobleRobot1, nobleRobot2, openRobot));

        round.sortRobots();

        assertEquals(nobleRobot1, round.getBots().get(0));
        assertEquals(nobleRobot2, round.getBots().get(1));
        assertEquals(openRobot, round.getBots().get(2));

    }



    @Test
    public void testThiefStealsGold() {
        GameEngine game = new GameEngine();
        game.clearBots();

        Robot thief = new RobotRandom("Thief");
        Robot target = new RobotRandom("Target");

        thief.setCharacter(CharactersType.VOLEUR);
        target.setCharacter(CharactersType.ROI);
        thief.setGolds(2);
        target.setGolds(5);

        game.addRobot(thief);
        game.addRobot(target);

        Round round = new Round(game.getBots());

        List<Robot> otherBots = Collections.singletonList(target); //liste avec uniquement la cible
        //thief.chooseTarget(otherBots);
        //round.thiefAction(thief);

        //assertEquals(7, thief.getGolds(), "Le voleur devrait avoir 7 pièces d'or après le vol");
        //assertEquals(0, target.getGolds(), "La cible devrait avoir 0 pièce d'or après le vol");
    }

    @Test
    void testAssignCrownForKing_OneNobleCharacter() {

        RobotRandom nobleRobot = new RobotRandom("Franz Kafka");
        nobleRobot.setCharacter(CharactersType.ROI);
        nobleRobot.setHasCrown(false);
        Round round = new Round(List.of(nobleRobot));
        round.assignCrownForKing();
        assertTrue(nobleRobot.getHasCrown());
    }




}
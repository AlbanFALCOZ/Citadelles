package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotNora;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundTest {

    @Test
    void testGetBots() {

        RobotNora robot1 = new RobotNora("Robot1");
        RobotNora robot2 = new RobotNora("Robot2");
        RobotNora robot3 = new RobotNora("Robot3");
        List<RobotNora> initialBots = List.of(robot1, robot2, robot3);
        Round round = new Round(initialBots);

        List<RobotNora> retrievedBots = round.getBots();


        assertEquals(initialBots, retrievedBots);
    }



    @Test
    void testSortRobots() {
        // Arrange
        RobotNora nobleRobot1 = new RobotNora("Franz Kafka");
        nobleRobot1.setCharacter(CharactersType.ROI);
        nobleRobot1.setHasCrown(true);

        RobotNora nobleRobot2 = new RobotNora("Leo Tolstoy");
        nobleRobot2.setCharacter(CharactersType.ROI);
        nobleRobot2.setHasCrown(false);

        RobotNora openRobot = new RobotNora("Simone De Beauvoir");
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

        RobotNora thief = new RobotNora("Thief");
        RobotNora target = new RobotNora("Target");

        thief.setCharacter(CharactersType.VOLEUR);
        target.setCharacter(CharactersType.ROI);
        thief.setGolds(2);
        target.setGolds(5);

        game.addRobot(thief);
        game.addRobot(target);

        Round round = new Round(game.getBots());

        List<RobotNora> otherBots = Collections.singletonList(target); //liste avec uniquement la cible
        //thief.chooseTarget(otherBots);
        //round.thiefAction(thief);

        //assertEquals(7, thief.getGolds(), "Le voleur devrait avoir 7 pièces d'or après le vol");
        //assertEquals(0, target.getGolds(), "La cible devrait avoir 0 pièce d'or après le vol");
    }

    @Test
    void testAssignCrownForKing_OneNobleCharacter() {

        RobotNora nobleRobot = new RobotNora("Franz Kafka");
        nobleRobot.setCharacter(CharactersType.ROI);
        nobleRobot.setHasCrown(false);
        Round round = new Round(List.of(nobleRobot));
        round.assignCrownForKing();
        assertTrue(nobleRobot.getHasCrown());
    }




}
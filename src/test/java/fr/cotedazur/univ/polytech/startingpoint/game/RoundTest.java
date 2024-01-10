package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    public void testSortRobots() {
        GameEngine gameEngine = new GameEngine();
        Round round = new Round(gameEngine.getBots());
        gameEngine.initializeBots();
        gameEngine.assignRandomCharacterToRobots();
        List<Robot> originalOrder = new ArrayList<>(gameEngine.getBots());
        ArrayList<Robot> sortedRobots = round.sortRobots();
        assertEquals(originalOrder, gameEngine.getBots());
        for (int i = 1; i < sortedRobots.size(); i++) {
            assertTrue(sortedRobots.get(i - 1).getCharacter().getNumber() <= sortedRobots.get(i).getCharacter().getNumber());
        }
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
        Round round = new Round(game.getBots());
        round.specialCard();

        assertEquals(6, robot1.getGolds());
    }


    @Test
    public void testGoldStealing() {
        GameEngine game = new GameEngine();
        Robot thief = new Robot("Thief");
        Robot target = new Robot("Target");

        thief.setGolds(2);
        target.setGolds(5);

        thief.setCharacter(CharactersType.VOLEUR);
        target.setCharacter(CharactersType.ROI);

        game.addRobot(thief);
        game.addRobot(target);

        thief.chooseTarget(Collections.singletonList(target));

        game.round.playTurns();

        assertEquals(7, thief.getGolds(), "Le voleur devrait avoir 7 pièces d'or après vol");
        assertEquals(0, target.getGolds(), "La cible devrait avoir 0 pièce d'or restante après vol");
    }


}
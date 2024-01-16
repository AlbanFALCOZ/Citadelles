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

    void testSortRobots() {
        GameEngine gameEngine = new GameEngine();
        Round round = new Round(gameEngine.getBots());
        gameEngine.initializeBots();
        gameEngine.robotsPickCharacters();
        List<Robot> originalOrder = new ArrayList<>(gameEngine.getBots());
        List<Robot> sortedRobots = round.sortRobots();
        assertEquals(originalOrder, gameEngine.getBots());
        for (int i = 1; i < sortedRobots.size(); i++) {
            assertTrue(sortedRobots.get(i - 1).getCharacter().getNumber() <= sortedRobots.get(i).getCharacter().getNumber());
        }

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


}
package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        Round round = new Round(game.getBots());
        game.clearBots();

        Robot robot1 = new Robot("Robot1");
        robot1.setCharacter(CharactersType.MARCHAND);
        robot1.addDistrict(DistrictsType.TAVERNE);
        robot1.setGolds(6);
        robot1.tryBuild();
        game.addRobot(robot1);

        round.specialCard();

        assertEquals(6, robot1.getGolds());
    }


}
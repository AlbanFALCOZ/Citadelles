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


}
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
    public void testThiefStealsGold() {
        GameEngine game = new GameEngine();
        game.clearBots();

        Robot thief = new Robot("Thief");
        Robot target = new Robot("Target");

        thief.setCharacter(CharactersType.VOLEUR);
        target.setCharacter(CharactersType.ROI);
        thief.setGolds(2);
        target.setGolds(5);

        game.addRobot(thief);
        game.addRobot(target);

        Round round = new Round(game.getBots());

        List<Robot> otherBots = Collections.singletonList(target); //liste avec uniquement la cible
        thief.chooseTarget(otherBots);
        round.thiefAction(thief);

        assertEquals(7, thief.getGolds(), "Le voleur devrait avoir 7 pièces d'or après le vol");
        assertEquals(0, target.getGolds(), "La cible devrait avoir 0 pièce d'or après le vol");
    }


}
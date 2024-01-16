package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotTest {

    private Robot robot;

    @BeforeEach
    void setUp() {
        robot = new RobotRandom("TestRobot");
    }


    @Test
    void showStatus() {
        Robot robot = new RobotRandom("Bot avec 8 golds");
        robot.setCharacter(CharactersType.ASSASSIN);
        robot.addGold(6);
        System.out.println(robot.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 8 golds, hand {}, city {}]", robot.statusOfPlayer(false));
        DistrictsType district = DistrictsType.BIBLIOTHEQUE;
        robot.addDistrict(district);
        System.out.println(robot.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 8 golds, hand {(Bibliothèque,6)}, city {}]", robot.statusOfPlayer(false));
        robot.tryBuild();
        System.out.println(robot.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 2 golds, hand {}, city {(Bibliothèque,6)}]", robot.statusOfPlayer(false));
    }

    @Test
    void testTryBuild() {
        Robot robot = new RobotRandom("TestRobot");
        DeckDistrict deckDistrict = new DeckDistrict();

        // Assuming you have some districts in the deck for testing
        DistrictsType districtWithCost2 = DistrictsType.EGLISE;
        DistrictsType districtWithCost3 = DistrictsType.MANOIR;
        DistrictsType districtWithCost5 = DistrictsType.PALAIS;
        robot.addDistrict(districtWithCost2);
        robot.addDistrict(districtWithCost5);
        robot.addDistrict(districtWithCost3);
        assertEquals(2, robot.getGolds());
        String builtDistrictName1 = robot.tryBuild();
        assertEquals("a new " + districtWithCost2.getName(), builtDistrictName1);


    }


    @Test
    void calculateScore() {
        robot.setCharacter(CharactersType.ASSASSIN);
        DistrictsType districtWithCost2 = DistrictsType.EGLISE;
        DistrictsType districtWithCost3 = DistrictsType.MANOIR;
        DistrictsType districtWithCost5 = DistrictsType.PALAIS;
        robot.addDistrict(districtWithCost2);
        robot.addDistrict(districtWithCost5);
        robot.addDistrict(districtWithCost3);
        robot.tryBuild();
        robot.tryBuild();
        robot.tryBuild();
        assertEquals(2, robot.calculateScore());
        robot.setGolds(2);
        robot.tryBuild();
        System.out.println(robot.statusOfPlayer());
        assertEquals(2, robot.calculateScore());
    }

    @Test
    void testPickDistrictCard() {
        List<DistrictsType> listDistrict = new ArrayList<>();
        listDistrict.add(DistrictsType.TEMPLE);
        robot.pickDistrictCard(listDistrict, new DeckDistrict());
        listDistrict.add(DistrictsType.MONASTERE);
        robot.pickDistrictCard(listDistrict, new DeckDistrict());

    }

    @Test
    void testGetNumberOfDistrictInHand() {
        robot.addDistrict(DistrictsType.BIBLIOTHEQUE);
        robot.addDistrict(DistrictsType.BIBLIOTHEQUE);
        robot.addDistrict(DistrictsType.BIBLIOTHEQUE);
        robot.addDistrict(DistrictsType.BIBLIOTHEQUE);
        assertEquals(4, robot.getNumberOfDistrictInHand());
    }

    @Test
    void testGetGolds() {
        robot.setGolds(5);
        assertEquals(5, robot.getGolds());
    }

    @Test
    void testGetName() {
        assertEquals("TestRobot", robot.getName());
    }

    @Test
    void testGetCharacter() {
        robot.setCharacter(CharactersType.ASSASSIN);
        assertEquals(CharactersType.ASSASSIN, robot.getCharacter());
    }

    @Test
    void testGetCity() {
        robot.addDistrict(DistrictsType.TAVERNE);
        robot.tryBuild();
        assertEquals(1, robot.getCity().size());
    }

    @Test
    void testSetHasCrown() {
        robot.setHasCrown(true);
        assertTrue(robot.getHasCrown());
    }

    @Test
    void testAddGold() {
        robot.addGold(5);
        assertEquals(7, robot.getGolds());
    }


    @Test
    void testAddDistrict() {
        robot.addDistrict(DistrictsType.BIBLIOTHEQUE);
        assertEquals(1, robot.getNumberOfDistrictInHand());
    }

    @Test
    void testSetGolds() {
        robot.setGolds(5);
        assertEquals(5, robot.getGolds());
    }


    @Test
    public void testCountBuildingsByType() {
        Robot robot = new RobotRandom("TestRobot");
        robot.setCharacter(CharactersType.MARCHAND);

        robot.getCity().add(DistrictsType.CHATEAU); // noble
        robot.getCity().add(DistrictsType.CHATEAU); // noble
        robot.getCity().add(DistrictsType.CASERNE); // militaire
        robot.getCity().add(DistrictsType.TAVERNE); // marchand

        // ordre des compteurs : noble, religieux, marchand, militaire, default
        assertEquals(1, robot.countBuildingsByType());
    }


    @Test
    public void testCountBuildingsWithMagicSchool() {
        Robot robot = new RobotRandom("TestRobot");

        robot.setCharacter(CharactersType.ROI);

        robot.getCity().add(DistrictsType.CHATEAU); // noble
        robot.getCity().add(DistrictsType.CASERNE); // militaire
        robot.getCity().add(DistrictsType.ECOLE_DE_MAGIE); // l'école de magie, qui sera comptée comme "noble" (pcq ROI)

        ;

        // ordre des compteurs : noble, religieux, marchand, militaire, default
        assertEquals(2, robot.countBuildingsByType());
    }

    @Test
    public void testWinGoldsByTypeOfBuildings() {
        robot.setCharacter(CharactersType.ROI);

        robot.getCity().add(DistrictsType.CHATEAU); // noble
        robot.getCity().add(DistrictsType.CASERNE); // militaire
        robot.getCity().add(DistrictsType.ECOLE_DE_MAGIE); // l'école de magie, qui sera comptée comme "noble" (pcq ROI)

        robot.winGoldsByTypeOfBuildings();

        assertEquals(4, robot.getGolds());

        Robot robotEveque = new RobotRandom("TestRobot2");
        robotEveque.setCharacter(CharactersType.EVEQUE);
        robotEveque.getCity().add(DistrictsType.EGLISE); // religieux
        robotEveque.getCity().add(DistrictsType.CASERNE); // militaire
        robotEveque.getCity().add(DistrictsType.ECOLE_DE_MAGIE); // l'école de magie, qui sera comptée comme "religieux" (pcq EVEQUE)
        robotEveque.getCity().add(DistrictsType.TAVERNE); // marchand
        robotEveque.getCity().add(DistrictsType.TEMPLE);// religieux
        robotEveque.getCity().add(DistrictsType.CATHEDRALE);// religieux

        robotEveque.winGoldsByTypeOfBuildings();

        assertEquals(6, robotEveque.getGolds());

    }

    @Test
    public void testScoreAvecUniversiteEtDracoport() {
        robot.setGolds(100);
        robot.setCharacter(CharactersType.ASSASSIN);
        robot.addDistrict(DistrictsType.DRACOPORT);
        robot.addDistrict(DistrictsType.UNIVERSITE);
        robot.tryBuild();
        robot.tryBuild();
        assertEquals(16, robot.calculateScore());
    }

    @Test
    public void testNumberOfCardsDrawnWithObservatoire() {
        robot.setCharacter(CharactersType.ASSASSIN);
        robot.setGolds(1000);
        robot.addDistrict(DistrictsType.OBSERVATOIRE);
        assertEquals(2, robot.getNumberOfCardsDrawn());
        robot.tryBuild();
        assertEquals(3, robot.getNumberOfCardsDrawn());
    }

    @Test
    public void districtAlreadyInCity() {
        robot.setCharacter(CharactersType.ASSASSIN);
        robot.setGolds(1000);
        robot.addDistrict(DistrictsType.OBSERVATOIRE);
        robot.addDistrict(DistrictsType.OBSERVATOIRE);
        robot.tryBuild();
        robot.tryBuild();
        assertEquals(1, robot.getCity().size());
        robot.addDistrict(DistrictsType.TAVERNE);
        robot.tryBuild();
        assertEquals(2, robot.getCity().size());
    }

    @Test
    public void testTargetSelection() {
        Robot thief = new RobotRandom("Thief");
        Robot target1 = new RobotRandom("Target1");
        Robot target2 = new RobotRandom("Target2");

        List<Robot> otherBots = Arrays.asList(target1, target2);
        /*
        thief.chooseTarget(otherBots);

        assertTrue(otherBots.contains(thief.getTarget()), "La cible devrait être parmi les autres bots");
         */
    }

}

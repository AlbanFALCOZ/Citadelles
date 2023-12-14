package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    private Robot robot;

    @BeforeEach
    void setUp() {
         robot = new Robot("TestRobot");
    }


    @Test
    void showStatus() {
        Robot robot = new Robot("Bot avec 8 golds");
        robot.setCharacter(CharactersType.ASSASSIN);
        robot.addGold(6);
        System.out.println(robot.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 8 golds, hand {}, city {}]",robot.statusOfPlayer(false));
        DistrictsType district = DistrictsType.BIBLIOTHEQUE;
        robot.addDistrict(district);
        System.out.println(robot.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 8 golds, hand {(Bibliothèque,6)}, city {}]",robot.statusOfPlayer(false));
        robot.tryBuild();
        System.out.println(robot.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 2 golds, hand {}, city {(Bibliothèque,6)}]",robot.statusOfPlayer(false));
    }

    @Test
    void testTryBuild() {
        Robot robot = new Robot("TestRobot");
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
        assertEquals("a new " + districtWithCost2.name(), builtDistrictName1);



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
        robot.pickDistrictCard();
        robot.pickDistrictCard();

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



    void testCountBuildingsNoMagicSchool() {
        robot.addDistrict(DistrictsType.CHATEAU); // noble
        robot.addDistrict(DistrictsType.CASERNE); // militaire

        assertEquals(2, robot.countBuildingsByType());
    }

    @Test
    void testCountBuildingsWithMagicSchoolAsNoble() {
        robot.setCharacter(CharactersType.ROI); // Roi, donc l'école de magie compte comme noble
        robot.addDistrict(DistrictsType.ECOLE_DE_MAGIE); // ecole
        robot.addDistrict(DistrictsType.CATHEDRALE); // religieux

        assertEquals(1, robot.countBuildingsByType()); // que l'école de magie compte comme noble

    }





    @Test
    public void testCountBuildingsByType() {
        Robot robot = new Robot("TestRobot");
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
        Robot robot = new Robot("TestRobot");

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

        Robot robotEveque = new Robot("TestRobot2");
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

}


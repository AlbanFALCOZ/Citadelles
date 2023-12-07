package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(districtWithCost2.name(), builtDistrictName1);


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
}

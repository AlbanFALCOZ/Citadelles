package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {


    @Test
    void showStatus() {
        Robot robot = new Robot("Bot avec 8 golds");
        robot.addGold(6);
        assertEquals("[Status of Bot avec 8 golds : 8 golds, hand {}, city {}]",robot.statusOfPlayer());
        DistrictsType district = DistrictsType.BIBLIOTHEQUE;
        robot.addDistrict(district);
        assertEquals("[Status of Bot avec 8 golds : 8 golds, hand {(Bibliothèque,6)}, city {}]",robot.statusOfPlayer());
        robot.tryBuild();
        assertEquals("[Status of Bot avec 8 golds : 2 golds, hand {}, city {(Bibliothèque,6)}]",robot.statusOfPlayer());

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
        Robot robot = new Robot("TestRobot");
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
        robot.startTurn();
        robot.tryBuild();
        System.out.println(robot.statusOfPlayer());
        assertEquals(2, robot.calculateScore());
    }
}

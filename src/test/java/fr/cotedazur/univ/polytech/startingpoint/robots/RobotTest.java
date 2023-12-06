package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {


    @Test
    void showStatus() {
        Robot robot = new Robot("Bot avec 8 golds");
        robot.addGold(6);
        assertEquals("[Status of Bot avec 8 golds : 8 golds, hand {}]",robot.statusOfPlayer());
        DistrictsType district = DistrictsType.BIBLIOTHEQUE;
        robot.addDistrict(district);
        assertEquals("[Status of Bot avec 8 golds : 8 golds, hand {(Bibliothèque,1)}]",robot.statusOfPlayer());
    }

    @Test
    void testTryBuild() {
        Robot robot = new Robot("TestRobot");
        DeckDistrict deckDistrict = new DeckDistrict();

        // Assuming you have some districts in the deck for testing
        DistrictsType districtWithCost2 = DistrictsType.PALAIS;
        DistrictsType districtWithCost3 = DistrictsType.HOTEL_DE_VILLE;
        DistrictsType districtWithCost5 = DistrictsType.UNIVERSITE;
        robot.addDistrict(districtWithCost2);
        robot.addDistrict(districtWithCost5);
        robot.addDistrict(districtWithCost3);
        assertEquals(2, robot.getGolds());
        String builtDistrictName1 = robot.tryBuild();
        assertEquals(districtWithCost2.name(), builtDistrictName1);

    }
}

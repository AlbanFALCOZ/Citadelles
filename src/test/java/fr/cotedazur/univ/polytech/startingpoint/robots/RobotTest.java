package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

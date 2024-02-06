package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotAnalyzerTest {
    private RobotAnalyzer robotAnalyzer;
    private DeckDistrict mockDeckDistrict;

    @BeforeEach
    public void setUp() {
        robotAnalyzer = new RobotAnalyzer("TestBot");
        mockDeckDistrict = mock(DeckDistrict.class);
    }



    @Test
    public void testTryBuild() {
        // Set up the necessary state for the robot
        robotAnalyzer.setGolds(5);
        robotAnalyzer.getDistrictInHand().add(DistrictsType.PALAIS);
        robotAnalyzer.getCity().add(DistrictsType.EGLISE);

        // Act
        String result = robotAnalyzer.tryBuild();

        // Assert
        assertEquals("a new Palais", result);
    }


    @Test
    void pickDistrictCard() {
    }

    @Test
    void generateChoice() {
    }

    @Test
    void pickCharacter() {
    }
}
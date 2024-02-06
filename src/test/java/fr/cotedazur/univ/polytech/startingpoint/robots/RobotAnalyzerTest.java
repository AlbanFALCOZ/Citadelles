package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        robotAnalyzer.setGolds(5);
        robotAnalyzer.getDistrictInHand().add(DistrictsType.PALAIS);
        robotAnalyzer.getCity().add(DistrictsType.EGLISE);

        String result = robotAnalyzer.tryBuild();

        assertEquals("a new Palais", result);
    }


    @Test
    public void testPickDistrictCard() {
        DeckDistrict deckDistrict = new DeckDistrict();
        List<DistrictsType> listDistrict = Arrays.asList(DistrictsType.PALAIS, DistrictsType.EGLISE);
        listDistrict.forEach(deckDistrict::addDistrictToDeck);

        int initialDeckSize = deckDistrict.numberOfRemainingDistrictInDeck();

        List<DistrictsType> chosenDistricts = robotAnalyzer.pickDistrictCard(listDistrict, deckDistrict);

        assertTrue(chosenDistricts.contains(DistrictsType.PALAIS) || chosenDistricts.contains(DistrictsType.EGLISE));
        int finalDeckSize = deckDistrict.numberOfRemainingDistrictInDeck();

        assertEquals(initialDeckSize - chosenDistricts.size() + listDistrict.size(), finalDeckSize);
    }


    @Test
    public void testGenerateChoice() {
        RobotAnalyzer robotAnalyzer = mock(RobotAnalyzer.class);
        when(robotAnalyzer.getGolds()).thenReturn(3);

        List<DistrictsType> mockDistricts = new ArrayList<>();
        mockDistricts.add(DistrictsType.PALAIS);
        when(robotAnalyzer.getDistrictInHand()).thenReturn(mockDistricts);

        int choice = robotAnalyzer.generateChoice();

        assertEquals(0, choice);
    }

    @Test
    void pickCharacter() {
    }
}
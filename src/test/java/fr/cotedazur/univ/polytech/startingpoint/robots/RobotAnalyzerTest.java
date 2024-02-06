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
    void generateChoice() {
    }

    @Test
    void pickCharacter() {
    }
}
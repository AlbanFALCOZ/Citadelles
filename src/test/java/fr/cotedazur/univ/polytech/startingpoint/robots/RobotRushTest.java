package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.Colors;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotRush;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RobotRushTest {

    private RobotRush robotRush;
    private DeckDistrict mockDeckDistrict;
    private ActionOfBotDuringARound actionMock;


    @BeforeEach
    public void setUp() {
        robotRush = new RobotRush("TestBot");
        robotRush.setGolds(5);

        mockDeckDistrict = mock(DeckDistrict.class);
        actionMock = mock(ActionOfBotDuringARound.class);
        robotRush.setAction(actionMock);
    }

    @Test
    public void testPickCharacter() {
        List<CharactersType> availableCharacters = Arrays.asList(CharactersType.values());
        List<CharactersType> copyAvailableCharacters = new ArrayList<>(availableCharacters);
        robotRush.pickCharacter(copyAvailableCharacters, null);
        assertNotEquals(null, robotRush.getCharacter());
    }


    @Test
    public void testTryBuild() {
        List<DistrictsType> allDistricts = Arrays.asList(DistrictsType.values());

        Random rand = new Random();
        DistrictsType randomDistrict = allDistricts.get(rand.nextInt(allDistricts.size()));

        robotRush.getDistrictInHand().add(randomDistrict);

        // construire
        String buildResult = robotRush.tryBuild();
        assertNotEquals("nothing", buildResult);
        assertFalse(robotRush.getCity().isEmpty());
    }


    @Test
    public void testPickMarchandWhenLowOnGolds() {
        RobotRush robotRush = new RobotRush("rush");
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> charactersList = deckCharacters.getCharactersInHand();
        charactersList.remove(CharactersType.ARCHITECTE);
        robotRush.pickCharacter(charactersList,new ArrayList<>());
        assertEquals(robotRush.getCharacter(),CharactersType.MARCHAND);
    }

    @Test
    public void testPickKingWhen6District() {
        RobotRush robotRush = new RobotRush("rush");
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> charactersList = deckCharacters.getCharactersInHand();
        charactersList.remove(CharactersType.ARCHITECTE);
        robotRush.setGolds(100);
        robotRush.pickCharacter(charactersList,new ArrayList<>());
        assertEquals(robotRush.getCharacter(),CharactersType.ROI);
    }

    @Test
    public void testPickEvequeWhen6DistrictAndNoKing() {
        RobotRush robotRush = new RobotRush("rush");
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> charactersList = deckCharacters.getCharactersInHand();
        charactersList.remove(CharactersType.ARCHITECTE);
        charactersList.remove(CharactersType.ROI);
        robotRush.setGolds(100);
        robotRush.pickCharacter(charactersList,new ArrayList<>());
        assertEquals(robotRush.getCharacter(),CharactersType.EVEQUE);
    }

    @Test
    public void testPickAssassinWhenNothing() {
        RobotRush robotRush = new RobotRush("rush");
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> charactersList = deckCharacters.getCharactersInHand();
        charactersList.remove(CharactersType.ARCHITECTE);
        charactersList.remove(CharactersType.ROI);
        charactersList.remove(CharactersType.EVEQUE);
        robotRush.setGolds(100);
        System.out.println(charactersList.get(0));
        robotRush.pickCharacter(charactersList,new ArrayList<>());
        //Le marchand a la priorité sur les autres personnages
        assertEquals(robotRush.getCharacter(),CharactersType.MARCHAND);
    }


    @Test
    public void testGenerateChoice() {
        int choice = robotRush.generateChoice();
        assertTrue(choice == 0 || choice == 1);
    }

    @Test
    public void testTryBuildRobotRush() {
        RobotRush robotRush = new RobotRush("rush");

        DistrictsType districtCost2 = DistrictsType.MARCHE;
        DistrictsType districtCost3 = DistrictsType.CASERNE;
        DistrictsType districtCost5 = DistrictsType.LABORATOIRE;
        robotRush.addDistrict(districtCost2);
        robotRush.addDistrict(districtCost3);
        robotRush.addDistrict(districtCost5);
        String nameDistrict = robotRush.tryBuild();
        assertEquals("a new " + districtCost2.getName(), nameDistrict);
    }

    @Test
    public void testPickDistrictCardRobotRush() {
        RobotRush robotRush = new RobotRush("rush");
        List<DistrictsType> listDistrictDrawn = new ArrayList<>();
        listDistrictDrawn.add(DistrictsType.TAVERNE);
        listDistrictDrawn.add(DistrictsType.MANUFACTURE);
        DeckDistrict deckDistrict = new DeckDistrict();
        List<DistrictsType> pickedDistricts = robotRush.pickDistrictCard(listDistrictDrawn, deckDistrict);
        assertEquals(1, pickedDistricts.size());
        assertTrue(pickedDistricts.contains(DistrictsType.TAVERNE) || pickedDistricts.contains(DistrictsType.MANUFACTURE));
    }
}

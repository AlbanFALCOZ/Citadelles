package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.Colors;
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

/*
    @Test
<<<<<<< HEAD

=======
=======
    /*@Test
>>>>>>> bb08d7867440ed661ee741f86b2d6635b148d03f
>>>>>>> 1fa0bfd12dfc15491faafeff3c464cbab0204010
    public void testTryBuild() {
        List<DistrictsType> allDistricts = Arrays.asList(DistrictsType.values());

        Random rand = new Random();
        DistrictsType randomDistrict = allDistricts.get(rand.nextInt(allDistricts.size()));

        robotRush.getDistrictInHand().add(randomDistrict);

        // construire
        String buildResult = robotRush.tryBuild();
        assertNotEquals("nothing", buildResult);
        assertTrue(robotRush.getCity().size() > 0);
    }*/


    /*
    @Test
    public void testPickDistrictCard() {
        List<DistrictsType> allDistricts = Arrays.asList(DistrictsType.values());
        Random rand = new Random();
        List<DistrictsType> mockDistrictList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mockDistrictList.add(allDistricts.get(rand.nextInt(allDistricts.size())));
        }
        when(mockDeckDistrict.getDistrictsInDeck()).thenReturn(
                mockDistrictList.get(0),
                mockDistrictList.subList(1, mockDistrictList.size()).toArray(new DistrictsType[0])
        );

        List<DistrictsType> pickedCards = robotRush.pickDistrictCard(mockDistrictList, mockDeckDistrict);

        assertFalse(pickedCards.isEmpty(), "The picked cards list should not be empty.");

        verify(actionMock).printDistrictChoice(anyList(), anyList());
    }

     */

    @Test
    public void testGenerateChoice() {
        int choice = robotRush.generateChoice();
        assertTrue(choice == 0 || choice == 1);
    }

}

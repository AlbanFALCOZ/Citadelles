package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PowerTest {

    @Test
    void canDestroyDistrict() {
        RobotRandom destructor = new RobotRandom("destructor");
        RobotRandom victim = new RobotRandom("victim");
        Power power = new Power(destructor, new ActionOfBotDuringARound(destructor));
        destructor.setCharacter(CharactersType.CONDOTTIERE);
        victim.setCharacter(CharactersType.MAGICIEN);
        victim.addDistrict(DistrictsType.TAVERNE);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PALAIS);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PRISON);
        victim.tryBuild();

        assertTrue(power.canDestroyDistrict(victim, DistrictsType.TAVERNE));
        destructor.setGolds(1);
        assertFalse(power.canDestroyDistrict(victim, DistrictsType.PRISON));
        assertFalse(power.canDestroyDistrict(victim, DistrictsType.PALAIS));
        destructor.setGolds(5);
        assertFalse(power.canDestroyDistrict(victim, DistrictsType.MANOIR));


    }



//Sans mockito
    @Test
    void condottiere() {
        RobotRandom destructor = new RobotRandom("destructor");
        RobotRandom victim = new RobotRandom("victim");
        Power power = new Power(destructor, new ActionOfBotDuringARound(destructor));
        destructor.setCharacter(CharactersType.CONDOTTIERE);
        victim.setCharacter(CharactersType.MARCHAND);
        victim.setGolds(30);
        victim.addDistrict(DistrictsType.TAVERNE);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PALAIS);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PRISON);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.CIMETIERE);
        victim.tryBuild();

        destructor.setGolds(5);
        power.condottiere(victim);
        assertEquals(1, destructor.getGolds());
        victim.setCharacter(CharactersType.CONDOTTIERE);
        power.condottiere(victim);
        assertEquals(1, destructor.getGolds());
        power.condottiere(victim);
        assertEquals(1, destructor.getGolds());

        victim.setCharacter(CharactersType.EVEQUE);
        destructor.setGolds(5);
        power.condottiere(victim);
        assertEquals(5, destructor.getGolds());
    }



    /*
    @Test
    public void tryDestroyDonjon() {
        RobotRandom destructor = new RobotRandom("destructor");
        RobotRandom victim = new RobotRandom("victim");
        Power power = new Power(destructor, new ActionOfBotDuringARound(destructor));
        destructor.setCharacter(CharactersType.CONDOTTIERE);
        victim.setCharacter(CharactersType.MARCHAND);
        victim.setGolds(30);
        victim.addDistrict(DistrictsType.DONJON);
        victim.tryBuild();

        destructor.setGolds(5);
        power.condottiere(victim);
        assertEquals(5, destructor.getGolds());
        assertEquals(1, victim.getNumberOfDistrictInCity());
        victim.addDistrict(DistrictsType.CASERNE);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.CHATEAU);
        victim.tryBuild();
        power.condottiere(victim);
        assertEquals(2, destructor.getGolds());
        assertEquals(2, victim.getNumberOfDistrictInCity());
    }
<<<<<<< HEAD
*/


    @Test
    public void marchand() {
        RobotRandom marchand = new RobotRandom("marchand");
        Power power = new Power(marchand,new ActionOfBotDuringARound(marchand));
        marchand.setCharacter(CharactersType.MARCHAND);
        assertEquals(2,marchand.getGolds());
        power.marchand();
        assertEquals(3,marchand.getGolds());
        marchand.winGoldsByTypeOfBuildings();
        assertEquals(3,marchand.getGolds());
        marchand.addDistrict(DistrictsType.TAVERNE);
        marchand.addGold(1);
        marchand.addDistrict(DistrictsType.ECHOPPE);
        marchand.addGold(2);
        marchand.tryBuild();
        marchand.tryBuild();
        marchand.winGoldsByTypeOfBuildings();
        assertEquals(5,marchand.getGolds());
    }

    @Test
    void testAssassin() {
        RobotRandom assassin = new RobotRandom("Assassin");
        RobotRandom victim = new RobotRandom("Victim");
        Power power = new Power(assassin,new ActionOfBotDuringARound(assassin));
        assassin.setCharacter(CharactersType.ASSASSIN);
        victim.setCharacter(CharactersType.MARCHAND);
        assertFalse(victim.getIsAssassinated());
        power.assassin(victim);
        assertTrue(victim.getIsAssassinated());
    }

    @Test
    void testMagicien() {
        RobotRandom magicien = new RobotRandom("Magicien");
        magicien.setCharacter(CharactersType.MAGICIEN);
        DeckDistrict deck = new DeckDistrict();
        for (int i = 0; i < 5; i++) magicien.addDistrict(deck.getDistrictsInDeck());
        int numberOfCardInHand = magicien.getNumberOfDistrictInHand();
        assertEquals(5,numberOfCardInHand);
        //Power power = mock(Power.class);
        //power.magicien();
    }

    @Mock
    private Robot mockedBot;
    @Mock
    private ActionOfBotDuringARound mockedAction;
    @InjectMocks
    private Power power;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Test avec mockito
    @Test
    void testMarchand() {
        when(mockedBot.getGolds()).thenReturn(0);
        doNothing().when(mockedBot).addGold(1);
        doNothing().when(mockedAction).printActionOfSellerBotWhoGainedGold();
        power.marchand();
        verify(mockedBot).addGold(1);
        verify(mockedAction).printActionOfSellerBotWhoGainedGold();
    }
    

    @Mock
    private DeckDistrict mockedDeck;



/*
    @Test
    void testArchitecteOptionOne() {
        // Set up the mocked behavior for the first option (i == 0)
        when(mockedBot.getChoice()).thenReturn(0);
        when(mockedBot.pickListOfDistrict(mockedDeck)).thenReturn(Arrays.asList(
                DistrictsType.TAVERNE, DistrictsType.MARCHE));
        when(mockedBot.pickDistrictCard(anyList(), eq(mockedDeck))).thenReturn(Arrays.asList(
                DistrictsType.MANOIR, DistrictsType.PALAIS));
        doNothing().when(mockedAction).addListOfDistrict(anyList(), anyList());
        doNothing().when(mockedAction).printActionOfBotWhoHasBuilt();

        power.architecte(mockedBot, mockedDeck);


        verify(mockedBot).setChoice(7);
        verify(mockedBot, times(3)).pickListOfDistrict(mockedDeck);
        verify(mockedBot, times(2)).pickDistrictCard(anyList(), eq(mockedDeck));
        verify(mockedAction).addListOfDistrict(anyList(), anyList());
        verify(mockedAction).printActionOfBotWhoHasBuilt();
    }


 */

    /*
@Test
void testVoleur() {
    // Set up the mocked behavior
    when(mockedBot.getIsAssassinated()).thenReturn(false);
    when(mockedBot.getGolds()).thenReturn(3);
    doNothing().when(mockedBot).addGold(3); // mock adding gold
    doNothing().when(mockedAction).printThiefStill(mockedBot);
    doNothing().when(mockedAction).printCantAffectVictim(any(Robot.class));

    // Call the method under test
    power.voleur(mockedBot);

    // Verify that the expected interactions occurred
    verify(mockedBot).getIsAssassinated();
    verify(mockedBot).getGolds();
    verify(mockedBot).addGold(3);
    verify(mockedAction).printThiefStill(mockedBot);
    verify(mockedAction, never()).printCantAffectVictim(any(Robot.class));

    // Resetting the mocks for the next verification
    reset(mockedBot, mockedAction);

    // Test the scenario where the victim is assassinated
    when(mockedBot.getIsAssassinated()).thenReturn(true);
    power.voleur(mockedBot);

    // Verify that the expected interactions occurred
    verify(mockedBot).getIsAssassinated();
    verify(mockedBot, never()).getGolds();
    verify(mockedBot, never()).addGold(anyInt());
    verify(mockedAction, never()).printThiefStill(mockedBot);
    verify(mockedAction).printCantAffectVictim(mockedBot);
}


    @Test
    void voleur(){

}
*
     */





}
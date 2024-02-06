package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RobotRichardoTest {

    private RobotRichardo richardo;
    private DeckCharacters deckCharacters;
    private DeckDistrict deckDistrict;

    @BeforeEach
    void setUp() {
        richardo = new RobotRichardo("richardo");
        deckCharacters = new DeckCharacters();
        deckDistrict = new DeckDistrict();
    }

    @Test
    void testRichardPickKing() {
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();

        StrategyBatisseur batisseur = new StrategyBatisseur();

        batisseur.pickBatisseur(listCharacters, richardo);
        assertEquals(richardo.character,CharactersType.ROI);
        assertEquals(listCharacters.size(),7);

        richardo.character = null;
        batisseur.pickBatisseur(listCharacters, richardo);
        assertEquals(richardo.character,CharactersType.MARCHAND);

        richardo.character = null;
        richardo.setGolds(6);
        richardo.addDistrict(deckDistrict.getDistrictsInDeck());
        richardo.addDistrict(deckDistrict.getDistrictsInDeck());
        richardo.addDistrict(deckDistrict.getDistrictsInDeck());
        batisseur.pickBatisseur(listCharacters, richardo);
        assertEquals(richardo.character,CharactersType.ARCHITECTE);
    }


 /*   @Test

    void testRaichardoWhenKingPickNobleCard() {
        richardo.setCharacter(CharactersType.ROI);
        richardo.isBatisseur();
        List<DistrictsType> listOfNobleCards = new ArrayList<>();
        listOfNobleCards.add(DistrictsType.CHATEAU);
        listOfNobleCards.add(DistrictsType.TAVERNE);
        richardo.addDistrict(richardo.pickDistrictCard(listOfNobleCards,deckDistrict));
        assertTrue(richardo.getDistrictInHand().contains(DistrictsType.CHATEAU));

    @Test
    void testRichardPickKing() {
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();

        richardo.pickBatisseur(listCharacters);
        assertEquals(richardo.character,CharactersType.ROI);
        assertEquals(listCharacters.size(),7);

        richardo.character = null;
        richardo.pickBatisseur(listCharacters);
        assertEquals(richardo.character,CharactersType.MARCHAND);

        richardo.character = null;
        richardo.setGolds(6);
        richardo.addDistrict(deckDistrict.getDistrictsInDeck());
        richardo.addDistrict(deckDistrict.getDistrictsInDeck());
        richardo.addDistrict(deckDistrict.getDistrictsInDeck());
        richardo.pickBatisseur(listCharacters);
        assertEquals(richardo.character,CharactersType.ARCHITECTE);
    }


    @Test
    void testRaichardoWhenKingPickNobleCard() {
        richardo.setCharacter(CharactersType.ROI);
        richardo.isBatisseur();
        List<DistrictsType> listOfNobleCards = new ArrayList<>();
        listOfNobleCards.add(DistrictsType.CHATEAU);
        listOfNobleCards.add(DistrictsType.TAVERNE);
        richardo.addDistrict(richardo.pickDistrictCard(listOfNobleCards,deckDistrict));
        assertTrue(richardo.getDistrictInHand().contains(DistrictsType.CHATEAU));

        richardo.addDistrict(DistrictsType.TAVERNE);
        richardo.setGolds(100);
        richardo.tryBuild();
        assertTrue(richardo.getCity().contains(DistrictsType.CHATEAU));
    }*/


    @Test
    void testRaichardoWhenMarchandPickMarchandDistrict() {
        richardo.setCharacter(CharactersType.MARCHAND);
        richardo.isBatisseur();
        List<DistrictsType> listOfNobleCards = new ArrayList<>();
        listOfNobleCards.add(DistrictsType.CHATEAU);
        listOfNobleCards.add(DistrictsType.TAVERNE);
        richardo.addDistrict(richardo.pickDistrictCard(listOfNobleCards,deckDistrict));
        assertTrue(richardo.getDistrictInHand().contains(DistrictsType.TAVERNE));

        richardo.addDistrict(DistrictsType.CHATEAU);
        richardo.setGolds(100);
        richardo.tryBuild();
        assertTrue(richardo.getCity().contains(DistrictsType.TAVERNE));
    }



    @Test
    public void testThereIsA_CharacterExists_ReturnsTrue() {

        List<CharactersType> availableCharacters = new ArrayList<>();
        availableCharacters.add(CharactersType.VOLEUR);
        RobotRichardo robot = new RobotRichardo("TestRobot");
        robot.setHasCrown(true);
        boolean result = robot.thereIsA(CharactersType.VOLEUR, availableCharacters);
        assertTrue(result);

    }

    @Test
    public void testThereIsA_CharacterDoesNotExist_ReturnsFalse() {

        List<CharactersType> availableCharacters = new ArrayList<>();
        availableCharacters.add(CharactersType.VOLEUR);

        RobotRichardo robot = new RobotRichardo("TestRobot");
        robot.setHasCrown(true);

        boolean result = robot.thereIsA(CharactersType.ASSASSIN, availableCharacters);

        assertFalse(result);
    }

    @Test
    public void testThereIsA_NoCrown_ReturnsFalse() {
        // Arrange
        List<CharactersType> availableCharacters = new ArrayList<>();
        availableCharacters.add(CharactersType.VOLEUR);

        RobotRichardo robot = new RobotRichardo("TestRobot");
        robot.setHasCrown(false);

        // Act
        boolean result = robot.thereIsA(CharactersType.VOLEUR , availableCharacters);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testChooseVictimForAssassin_ThiefExists_ReturnsCorrectVictim() {
        List<CharactersType> availableCharacters = new ArrayList<>();
        availableCharacters.add(CharactersType.VOLEUR);
        RobotRichardo assassin = new RobotRichardo("Assassin");
        assassin.setHasCrown(true);
        assassin.setCharacter(CharactersType.ASSASSIN);
        List<Robot> bots = new ArrayList<>();
        RobotRichardo victim = new RobotRichardo("Victim");
        victim.setCharacter(CharactersType.VOLEUR);
        bots.add(victim);
        assassin.pickCharacter(availableCharacters , bots );
        Robot chosenVictim = assassin.getStrategyAgressif().chooseVictimForAssassin(bots , 0  , richardo) ;

        assertEquals(victim, chosenVictim);
    }

    @Test
    public void testPickCharacterCard_CharacterExists_CharacterPickedAndRemovedFromList() {

        List<CharactersType> availableCharacters = new ArrayList<>();
        availableCharacters.add(CharactersType.ROI);
        availableCharacters.add(CharactersType.MARCHAND);
        availableCharacters.add(CharactersType.EVEQUE);
        RobotRichardo robot = new RobotRichardo("Test Robot");

        robot.pickCharacterCard(availableCharacters, CharactersType.MARCHAND);

        assertEquals(CharactersType.MARCHAND, robot.getCharacter());

        assertFalse(availableCharacters.contains(CharactersType.MARCHAND));
    }






}
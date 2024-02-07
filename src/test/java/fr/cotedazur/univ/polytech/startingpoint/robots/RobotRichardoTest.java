package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.richardo.RobotRichardo;
import fr.cotedazur.univ.polytech.startingpoint.richardo.StrategyBatisseur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        assertEquals(richardo.character,CharactersType.MARCHAND);
        assertEquals(listCharacters.size(),7);

        richardo.character = null;
        batisseur.pickBatisseur(listCharacters, richardo);
        assertEquals(richardo.character,CharactersType.ROI);

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

    public void testChooseVictimForCondottiere_ChoosesCorrectVictim() {

        List<CharactersType> availableCharacters = new ArrayList<>();
        availableCharacters.add(CharactersType.CONDOTTIERE);

        List<Robot> bots = new ArrayList<>();
        Robot bot1 = Mockito.mock(Robot.class);
        when(bot1.getNumberOfDistrictInCity()).thenReturn(4);
        when(bot1.getCharacter()).thenReturn(CharactersType.ROI);
        bots.add(bot1);
        Robot bot2 = Mockito.mock(Robot.class);
        when(bot2.getNumberOfDistrictInCity()).thenReturn(5);
        when(bot2.getCharacter()).thenReturn(CharactersType.ASSASSIN);
        bots.add(bot2);
        RobotRichardo richardo = new RobotRichardo("Richard");
        richardo.setAvailableCharacters(availableCharacters);
        richardo.pickCharacter(availableCharacters , bots);
        Robot chosenVictim = richardo.chooseVictimForCondottiere(bots);
        assertEquals(bot1, chosenVictim);
    }


    @Test
    void testPickCharacterWithoutVoleurAndNoNeedForCondottiere() {
        List<Robot> bots = new ArrayList<>();
        List<CharactersType> charactersToPickFrom = new ArrayList<>() ;
        charactersToPickFrom.add(CharactersType.ROI) ;
        charactersToPickFrom.add(CharactersType.EVEQUE) ;
        charactersToPickFrom.add(CharactersType.CONDOTTIERE) ;
        charactersToPickFrom.add(CharactersType.ASSASSIN) ;
        Robot sarsor = new RobotSarsor("Sara");
        Robot gentil = new RobotDiscrete("Stacy");
        Robot choice = new RobotChoiceOfCharacter("Alban");
        RobotRichardo richardo = new RobotRichardo("Richardo");
        bots.add(sarsor);
        bots.add(gentil);
        bots.add(choice);
        bots.add(richardo);
        richardo.setAgressif(true);
        assertTrue( richardo.getAgressive());
        richardo.setHasCrown(true);
        richardo.pickCharacter(charactersToPickFrom , bots);

        assertEquals(CharactersType.ROI , richardo.getCharacter());

    }



/*

    @Test
    void testPickCharacterForCondottiere(){
        List<Robot> bots = new ArrayList<>();
        List<CharactersType> charactersToPickFrom = new ArrayList<>() ;
        charactersToPickFrom.add(CharactersType.ROI) ;
        charactersToPickFrom.add(CharactersType.EVEQUE) ;
        charactersToPickFrom.add(CharactersType.ASSASSIN) ;
        charactersToPickFrom.add(CharactersType.CONDOTTIERE) ;
        charactersToPickFrom.add(CharactersType.VOLEUR) ;
        richardo.setGolds(7);
        Robot sarsor = new RobotSarsor("Sara");
        Robot gentil = new RobotDiscrete("Stacy");
        Robot choice = new RobotChoiceOfCharacter("Alban");
        Robot richardo = new RobotRichardo("Richardo");
        bots.add(sarsor);
        bots.add(gentil);
        bots.add(choice);
        bots.add(richardo);
        richardo.setHasCrown(true);
        for ( int i = 0  ; i <7 ; i++) {
            sarsor.getCity().add(DistrictsType.MANOIR);
        }
        for (int i = 0 ; i < 3 ; i++){
            richardo.getCity().add(DistrictsType.MARCHE);
        }
        richardo.pickCharacter(charactersToPickFrom , bots);
        assertEquals(CharactersType.CONDOTTIERE , richardo.getCharacter());

    }



 */

    
    }








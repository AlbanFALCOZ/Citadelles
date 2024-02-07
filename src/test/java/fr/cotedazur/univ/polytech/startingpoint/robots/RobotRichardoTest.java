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

    @Test
    void testRichardoWhenKingPickNobleCard() {
        richardo.setCharacter(CharactersType.ROI);
        StrategyBatisseur batisseur = new StrategyBatisseur();
        batisseur.isBatisseur(richardo);
        List<DistrictsType> listOfNobleCards = new ArrayList<>();
        listOfNobleCards.add(DistrictsType.CHATEAU);
        listOfNobleCards.add(DistrictsType.TAVERNE);
        richardo.addDistrict(richardo.pickDistrictCard(listOfNobleCards,deckDistrict));
        assertTrue(richardo.getDistrictInHand().contains(DistrictsType.CHATEAU));
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


    @Test
    void testScenarioArchitecte() {
        RobotRichardo botNearFinishing = new RobotRichardo("richardo1");
        RobotRichardo richardo2 = new RobotRichardo("richardo2");
        RobotRichardo richardo3 = new RobotRichardo("richardo3");
        RobotRichardo richardo4 = new RobotRichardo("richardo4");

        List<CharactersType> listCharacter = deckCharacters.getCharactersInHand();

        List<Robot> listBots = new ArrayList<>();
        listBots.add(botNearFinishing);
        listBots.add(richardo2);
        listBots.add(richardo3);
        listBots.add(richardo4);

        botNearFinishing.setGolds(1000);
        while (botNearFinishing.getNumberOfDistrictInCity() < 5 || botNearFinishing.getNumberOfDistrictInHand() < 1) {
            botNearFinishing.addDistrict(deckDistrict.getDistrictsInDeck());
            botNearFinishing.tryBuild();
        }

        assertTrue(richardo2.scenarioArchitecte(listBots));


        botNearFinishing.setCharacter(CharactersType.CONDOTTIERE);
        richardo2.setCharacter(CharactersType.EVEQUE);

        listCharacter.remove(CharactersType.EVEQUE);
        listCharacter.remove(CharactersType.CONDOTTIERE);
        listCharacter.remove(CharactersType.ROI);

        richardo2.pickCharacter(listCharacter,listBots);
        assertEquals(richardo2.getCharacter(),CharactersType.ASSASSIN);

        listCharacter.add(CharactersType.ROI);
        richardo2.pickCharacter(listCharacter,listBots);
        assertEquals(richardo2.getCharacter(),CharactersType.ARCHITECTE);

    }
}










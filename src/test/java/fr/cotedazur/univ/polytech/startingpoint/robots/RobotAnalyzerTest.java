package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.Round;
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
    private List<Robot> mockAllPlayers;


    @BeforeEach
    public void setUp() {
        mockAllPlayers = new ArrayList<>();
        robotAnalyzer = new RobotAnalyzer("TestBot",mockAllPlayers);
        mockDeckDistrict = mock(DeckDistrict.class);
        robotAnalyzer.getCity().clear();
    }

    /*@Test
    public void testTryBuild() {
        //construction district spécial quand c'est possible
        robotAnalyzer.setGolds(6);
        robotAnalyzer.setDistrictInHand(new ArrayList<>(Arrays.asList(DistrictsType.MANOIR, DistrictsType.CATHEDRALE, DistrictsType.BIBLIOTHEQUE)));
        String result = robotAnalyzer.tryBuild();
        assertEquals("a new Bibliotheque", result, "Le robot devrait construire 'Bibliothèque' car c'est un district spécial qu'il peut se permettre.");

        //construction district pour bloquer adversaires
        robotAnalyzer.setGolds(3);
        // Assurez-vous que le mock ou la logique de test simule correctement le contexte où 'Manoir' serait choisi pour bloquer les adversaires
        robotAnalyzer.setDistrictInHand(new ArrayList<>(Arrays.asList(DistrictsType.MANOIR, DistrictsType.TAVERNE)));
        result = robotAnalyzer.tryBuild();
        assertEquals("a new Manoir", result, "Le robot devrait construire 'Manoir', basé sur la quantité d'or disponible et la stratégie de blocage des adversaires.");

        //cas où robot ne peut construire aucun district
        robotAnalyzer.setGolds(0);
        robotAnalyzer.setDistrictInHand(new ArrayList<>(Arrays.asList(DistrictsType.TAVERNE)));
        result = robotAnalyzer.tryBuild();
        assertEquals("nothing", result, "Le robot ne devrait construire aucun district car il n'a pas assez d'or.");
    }*/





    /*@Test
    public void testPickDistrictCard() {
        List<DistrictsType> availableDistricts = Arrays.asList(DistrictsType.TAVERNE, DistrictsType.PALAIS, DistrictsType.MANOIR, DistrictsType.CATHEDRALE);

        robotAnalyzer.getCity().addAll(Arrays.asList(DistrictsType.CHATEAU));
        robotAnalyzer.setGolds(8);

        List<DistrictsType> chosenDistricts = robotAnalyzer.pickDistrictCard(availableDistricts, mockDeckDistrict);
        assertTrue(chosenDistricts.size() <= robotAnalyzer.getNumberOfCardsChosen(), "Le robot devrait choisir un nombre limité de districts basé sur le nombre de cartes qu'il peut choisir.");

        assertTrue(chosenDistricts.contains(DistrictsType.PALAIS) || chosenDistricts.contains(DistrictsType.CATHEDRALE), "Le robot devrait prioriser les districts avec des scores élevés ou des coûts accessibles.");

        int totalCost = chosenDistricts.stream().mapToInt(DistrictsType::getCost).sum();
        assertTrue(totalCost <= robotAnalyzer.getGolds(), "Le coût total des districts choisis ne devrait pas dépasser l'or disponible du robot.");
    }*/




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
    public void testPickCharacter() {
        List<CharactersType> availableCharacters = Arrays.asList(CharactersType.ASSASSIN, CharactersType.VOLEUR, CharactersType.ROI);
        List<CharactersType> copyAvailableCharacters = new ArrayList<>(availableCharacters);

        Robot mockBot1 = mock(Robot.class);
        when(mockBot1.getName()).thenReturn("Bot1");
        when(mockBot1.getGolds()).thenReturn(4);
        when(mockBot1.getDistrictInHand()).thenReturn(Arrays.asList(DistrictsType.PALAIS, DistrictsType.EGLISE));

        Robot mockBot2 = mock(Robot.class);
        when(mockBot2.getName()).thenReturn("Bot2");
        when(mockBot2.getGolds()).thenReturn(3);
        when(mockBot2.getDistrictInHand()).thenReturn(Arrays.asList(DistrictsType.FORTRESSE, DistrictsType.PRISON));

        List<Robot> bots = Arrays.asList(robotAnalyzer, mockBot1, mockBot2);


        robotAnalyzer.pickCharacter(copyAvailableCharacters, bots);

        CharactersType chosenCharacter = robotAnalyzer.getCharacter();
        assertNotNull(chosenCharacter, "Le personnage choisi ne devrait pas être null");
        assertTrue(availableCharacters.contains(chosenCharacter), "Le personnage choisi doit être parmi les personnages dispo");

        assertEquals(CharactersType.ASSASSIN, chosenCharacter, "Le personnage choisi devrait être l'Assassin pour contrer le Roi prédit pour plusieurs adversaires");
    }

}
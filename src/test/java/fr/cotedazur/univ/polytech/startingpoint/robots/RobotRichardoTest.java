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
<<<<<<< HEAD
        StrategyBatisseur batisseur = new StrategyBatisseur();
=======

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
>>>>>>> 1fa0bfd12dfc15491faafeff3c464cbab0204010

        batisseur.pickBatisseur(listCharacters, richardo);
        assertEquals(richardo.character,CharactersType.ROI);
        assertEquals(listCharacters.size(),7);

<<<<<<< HEAD
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
    void testRichardoWhenKingPickNobleCard() {
=======
    @Test
    void testRaichardoWhenKingPickNobleCard() {
>>>>>>> 1fa0bfd12dfc15491faafeff3c464cbab0204010
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
<<<<<<< HEAD
    }*/
=======
    }
>>>>>>> 1fa0bfd12dfc15491faafeff3c464cbab0204010

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
}
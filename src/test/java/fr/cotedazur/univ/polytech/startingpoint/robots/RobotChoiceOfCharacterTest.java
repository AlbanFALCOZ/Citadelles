package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RobotChoiceOfCharacterTest {

    @Test
    void testPickMarchand() {
        RobotChoiceOfCharacter robotChoiceOfCharacter = new RobotChoiceOfCharacter("Robocop");
        robotChoiceOfCharacter.addGold(1000);
        robotChoiceOfCharacter.addDistrict(DistrictsType.TAVERNE);
        robotChoiceOfCharacter.addDistrict(DistrictsType.ECHOPPE);
        robotChoiceOfCharacter.tryBuild();robotChoiceOfCharacter.tryBuild();
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();
        robotChoiceOfCharacter.pickCharacterBasedOfNumberOfDistrict(listCharacters);
        assertEquals(CharactersType.MARCHAND,robotChoiceOfCharacter.getCharacter());
    }

    @Test
    void testPickEveque() {
        RobotChoiceOfCharacter robotChoiceOfCharacter = new RobotChoiceOfCharacter("Robocop");
        robotChoiceOfCharacter.addGold(1000);
        robotChoiceOfCharacter.addDistrict(DistrictsType.EGLISE);
        robotChoiceOfCharacter.addDistrict(DistrictsType.TEMPLE);
        robotChoiceOfCharacter.tryBuild();robotChoiceOfCharacter.tryBuild();
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();
        robotChoiceOfCharacter.pickCharacterBasedOfNumberOfDistrict(listCharacters);
        assertEquals(CharactersType.EVEQUE,robotChoiceOfCharacter.getCharacter());
    }

    @Test
    void testPickRoi() {
        RobotChoiceOfCharacter robotChoiceOfCharacter = new RobotChoiceOfCharacter("Robocop");
        robotChoiceOfCharacter.addGold(1000);
        robotChoiceOfCharacter.addDistrict(DistrictsType.CHATEAU);
        robotChoiceOfCharacter.addDistrict(DistrictsType.MANOIR);
        robotChoiceOfCharacter.tryBuild();robotChoiceOfCharacter.tryBuild();
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();
        robotChoiceOfCharacter.pickCharacterBasedOfNumberOfDistrict(listCharacters);
        assertEquals(CharactersType.ROI,robotChoiceOfCharacter.getCharacter());
    }

    @Test
    void testPickCondottiere() {
        RobotChoiceOfCharacter robotChoiceOfCharacter = new RobotChoiceOfCharacter("Robocop");
        robotChoiceOfCharacter.addGold(1000);
        robotChoiceOfCharacter.addDistrict(DistrictsType.CASERNE);
        robotChoiceOfCharacter.addDistrict(DistrictsType.PRISON);
        robotChoiceOfCharacter.tryBuild();robotChoiceOfCharacter.tryBuild();
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();
        robotChoiceOfCharacter.pickCharacterBasedOfNumberOfDistrict(listCharacters);
        assertEquals(CharactersType.CONDOTTIERE,robotChoiceOfCharacter.getCharacter());
    }

    @Test
    void testPickAssassin() {
        RobotChoiceOfCharacter robotChoiceOfCharacter = new RobotChoiceOfCharacter("Robocop");
        robotChoiceOfCharacter.addGold(1000);
        robotChoiceOfCharacter.addDistrict(DistrictsType.CHATEAU);
        robotChoiceOfCharacter.addDistrict(DistrictsType.EGLISE);
        robotChoiceOfCharacter.addDistrict(DistrictsType.CASERNE);
        robotChoiceOfCharacter.addDistrict(DistrictsType.TAVERNE);
        robotChoiceOfCharacter.tryBuild();robotChoiceOfCharacter.tryBuild();
        DeckCharacters deckCharacters = new DeckCharacters();
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();
        robotChoiceOfCharacter.pickCharacter(listCharacters,new ArrayList<>());
        assertEquals(CharactersType.MAGICIEN,robotChoiceOfCharacter.getCharacter());
    }

    @Test
    void test() {
        RobotChoiceOfCharacter random = new RobotChoiceOfCharacter("random");
        random.addDistrict(DistrictsType.EGLISE);
        random.addDistrict(DistrictsType.TAVERNE);
        random.addDistrict(DistrictsType.FORTRESSE);
        random.addDistrict(DistrictsType.LABORATOIRE);
        random.addDistrict(DistrictsType.BIBLIOTHEQUE);
        random.addDistrict(DistrictsType.CIMETIERE);
        random.addGold(1000);
        for (int i = 0; i < 6; i++) {
            random.tryBuild();
        }
        assertFalse(random.canFinishNextTurn());
        random.addDistrict(DistrictsType.CHATEAU);
        random.addDistrict(DistrictsType.PALAIS);
        assertTrue(random.canFinishNextTurn());
    }
}

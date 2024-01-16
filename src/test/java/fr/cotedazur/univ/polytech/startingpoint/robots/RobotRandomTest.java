package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RobotRandomTest {
    private RobotRandom robotRandom;


    @BeforeEach
    void setUp() {
        robotRandom = new RobotRandom("testRobot");
    }

    @Test
    void getDistrictInHand() {
        assertTrue(robotRandom.getDistrictInHand().isEmpty());
    }

    @Test
    void getScore() {
        assertEquals(0, robotRandom.getScore());
    }

    @Test
    void getRESET() {
        assertEquals("\u001B[0m", robotRandom.getRESET());
    }

    @Test
    void getName() {
        assertEquals("testRobot", robotRandom.getName());
    }


    @Test
    void getNumberOfCardsDrawn() {
        assertEquals(2, robotRandom.getNumberOfCardsDrawn());
    }

    @Test
    void setNumberOfCardsDrawn() {
        robotRandom.setNumberOfCardsDrawn(3);
        assertEquals(3, robotRandom.getNumberOfCardsDrawn());
    }

    @Test
    void getNumberOfCardsChosen() {
        assertEquals(1, robotRandom.getNumberOfCardsChosen());
    }

    @Test
    void setNumberOfCardsChosen() {
        robotRandom.setNumberOfCardsChosen(2);
        assertEquals(2, robotRandom.getNumberOfCardsChosen());
    }

    @Test
    void setScore() {
        robotRandom.setScore(10);
        assertEquals(10, robotRandom.getScore());
    }

    @Test
    void addGold() {
        robotRandom.addGold(3);
        assertEquals(5, robotRandom.getGolds());
    }

    @Test
    void setCharacter() {
        robotRandom.setCharacter(CharactersType.ROI);
        assertEquals(CharactersType.ROI, robotRandom.getCharacter());
    }

    @Test
    void getCharacter() {
        robotRandom.setCharacter(CharactersType.ROI);
        assertEquals(CharactersType.ROI, robotRandom.getCharacter());
    }

    @Test
    void addDistrict() {
        robotRandom.addDistrict(DistrictsType.CHATEAU);
        assertEquals(1, robotRandom.getNumberOfDistrictInHand());
    }

    @Test
    void getNumberOfDistrictInHand() {
        robotRandom.addDistrict(DistrictsType.CHATEAU);
        robotRandom.addDistrict(DistrictsType.BIBLIOTHEQUE);
        assertEquals(2, robotRandom.getNumberOfDistrictInHand());
    }

    @Test
    void getNumberOfDistrictInCity() {
        robotRandom.addDistrict(DistrictsType.MANOIR);
        robotRandom.addGold(4);
        robotRandom.tryBuild();
        assertEquals(1, robotRandom.getNumberOfDistrictInCity());
    }

    @Test
    void pickDistrictCard() {
        List<DistrictsType> listDistrict = new ArrayList<>();
        listDistrict.add(DistrictsType.CHATEAU);
        listDistrict.add(DistrictsType.MANOIR);
        List<DistrictsType> pickedDistricts = robotRandom.pickDistrictCard(listDistrict, new DeckDistrict());
        assertEquals(1, pickedDistricts.size());
        assertTrue(pickedDistricts.contains(DistrictsType.CHATEAU) || pickedDistricts.contains(DistrictsType.MANOIR));
    }

    @Test
    void pickListOfDistrict() {
        List<DistrictsType> pickedDistricts = robotRandom.pickListOfDistrict(new DeckDistrict());
        assertEquals(2, pickedDistricts.size());
    }

    @Test
    void getHasCrown() {
        robotRandom.setHasCrown(true);
        assertTrue(robotRandom.getHasCrown());
    }

    @Test
    void countBuildingsByType() {
        robotRandom.setCharacter(CharactersType.MARCHAND);
        robotRandom.getCity().add(DistrictsType.MANOIR); // marchand
        robotRandom.getCity().add(DistrictsType.TAVERNE); // marchand
        robotRandom.getCity().add(DistrictsType.CASERNE); // militaire
        assertEquals(1, robotRandom.countBuildingsByType());
    }

    @Test
    void winGoldsByTypeOfBuildings() {
        robotRandom.setCharacter(CharactersType.MARCHAND);
        robotRandom.getCity().add(DistrictsType.MARCHE); // marchand
        robotRandom.getCity().add(DistrictsType.TAVERNE); // marchand
        robotRandom.getCity().add(DistrictsType.CASERNE); // militaire
        robotRandom.winGoldsByTypeOfBuildings();
        assertEquals(4, robotRandom.getGolds());
    }

    @Test
    void getChoice() {
        int choice = robotRandom.getChoice();
        assertTrue(choice == 0 || choice == 1);
    }

    @Test
    void showStatus() {
        RobotRandom robotRandom = new RobotRandom("Bot avec 8 golds");
        robotRandom.setCharacter(CharactersType.ASSASSIN);
        robotRandom.addGold(6);
        System.out.println(robotRandom.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 8 golds, hand {}, city {}]", robotRandom.statusOfPlayer(false));
        DistrictsType district = DistrictsType.BIBLIOTHEQUE;
        robotRandom.addDistrict(district);
        System.out.println(robotRandom.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 8 golds, hand {(Bibliothèque,6)}, city {}]", robotRandom.statusOfPlayer(false));
        robotRandom.tryBuild();
        System.out.println(robotRandom.statusOfPlayer());
        assertEquals("[Status of Bot avec 8 golds : role (Assassin), 2 golds, hand {}, city {(Bibliothèque,6)}]", robotRandom.statusOfPlayer(false));
    }

    @Test
    void testTryBuild() {
        RobotRandom robotRandom = new RobotRandom("TestrobotRandom");
        DeckDistrict deckDistrict = new DeckDistrict();

        // Assuming you have some districts in the deck for testing
        DistrictsType districtWithCost2 = DistrictsType.EGLISE;
        DistrictsType districtWithCost3 = DistrictsType.MANOIR;
        DistrictsType districtWithCost5 = DistrictsType.PALAIS;
        robotRandom.addDistrict(districtWithCost2);
        robotRandom.addDistrict(districtWithCost5);
        robotRandom.addDistrict(districtWithCost3);
        assertEquals(2, robotRandom.getGolds());
        String builtDistrictName1 = robotRandom.tryBuild();
        assertEquals("a new " + districtWithCost2.getName(), builtDistrictName1);


    }


    @Test
    void calculateScore() {
        robotRandom.setCharacter(CharactersType.ASSASSIN);
        DistrictsType districtWithCost2 = DistrictsType.EGLISE;
        DistrictsType districtWithCost3 = DistrictsType.MANOIR;
        DistrictsType districtWithCost5 = DistrictsType.PALAIS;
        robotRandom.addDistrict(districtWithCost2);
        robotRandom.addDistrict(districtWithCost5);
        robotRandom.addDistrict(districtWithCost3);
        robotRandom.tryBuild();
        robotRandom.tryBuild();
        robotRandom.tryBuild();
        assertEquals(2, robotRandom.calculateScore());
        robotRandom.setGolds(2);
        robotRandom.tryBuild();
        System.out.println(robotRandom.statusOfPlayer());
        assertEquals(2, robotRandom.calculateScore());
    }


    @Test
    void testGetGolds() {
        robotRandom.setGolds(5);
        assertEquals(5, robotRandom.getGolds());
    }

    @Test
    void testGetName() {
        assertEquals("testRobot", robotRandom.getName());
    }

    @Test
    void testGetCharacter() {
        robotRandom.setCharacter(CharactersType.ASSASSIN);
        assertEquals(CharactersType.ASSASSIN, robotRandom.getCharacter());
    }

    @Test
    void testGetCity() {
        robotRandom.addDistrict(DistrictsType.TAVERNE);
        robotRandom.tryBuild();
        assertEquals(1, robotRandom.getCity().size());
    }

    @Test
    void testSetHasCrown() {
        robotRandom.setHasCrown(true);
        assertTrue(robotRandom.getHasCrown());
    }

    @Test
    void testAddGold() {
        robotRandom.addGold(5);
        assertEquals(7, robotRandom.getGolds());
    }


    @Test
    void testAddDistrict() {
        robotRandom.addDistrict(DistrictsType.BIBLIOTHEQUE);
        assertEquals(1, robotRandom.getNumberOfDistrictInHand());
    }

    @Test
    void testSetGolds() {
        robotRandom.setGolds(5);
        assertEquals(5, robotRandom.getGolds());
    }


    @Test
    public void testCountBuildingsByType() {
        RobotRandom robotRandom = new RobotRandom("TestrobotRandom");
        robotRandom.setCharacter(CharactersType.MARCHAND);

        robotRandom.getCity().add(DistrictsType.CHATEAU); // noble
        robotRandom.getCity().add(DistrictsType.CHATEAU); // noble
        robotRandom.getCity().add(DistrictsType.CASERNE); // militaire
        robotRandom.getCity().add(DistrictsType.TAVERNE); // marchand

        // ordre des compteurs : noble, religieux, marchand, militaire, default
        assertEquals(1, robotRandom.countBuildingsByType());
    }


    @Test
    public void testCountBuildingsWithMagicSchool() {
        RobotRandom robotRandom = new RobotRandom("TestrobotRandom");

        robotRandom.setCharacter(CharactersType.ROI);

        robotRandom.getCity().add(DistrictsType.CHATEAU); // noble
        robotRandom.getCity().add(DistrictsType.CASERNE); // militaire
        robotRandom.getCity().add(DistrictsType.ECOLE_DE_MAGIE); // l'école de magie, qui sera comptée comme "noble" (pcq ROI)

        ;

        // ordre des compteurs : noble, religieux, marchand, militaire, default
        assertEquals(2, robotRandom.countBuildingsByType());
    }

    @Test
    public void testWinGoldsByTypeOfBuildings() {
        robotRandom.setCharacter(CharactersType.ROI);

        robotRandom.getCity().add(DistrictsType.CHATEAU); // noble
        robotRandom.getCity().add(DistrictsType.CASERNE); // militaire
        robotRandom.getCity().add(DistrictsType.ECOLE_DE_MAGIE); // l'école de magie, qui sera comptée comme "noble" (pcq ROI)

        robotRandom.winGoldsByTypeOfBuildings();

        assertEquals(4, robotRandom.getGolds());

        RobotRandom robotRandomEveque = new RobotRandom("TestrobotRandom2");
        robotRandomEveque.setCharacter(CharactersType.EVEQUE);
        robotRandomEveque.getCity().add(DistrictsType.EGLISE); // religieux
        robotRandomEveque.getCity().add(DistrictsType.CASERNE); // militaire
        robotRandomEveque.getCity().add(DistrictsType.ECOLE_DE_MAGIE); // l'école de magie, qui sera comptée comme "religieux" (pcq EVEQUE)
        robotRandomEveque.getCity().add(DistrictsType.TAVERNE); // marchand
        robotRandomEveque.getCity().add(DistrictsType.TEMPLE);// religieux
        robotRandomEveque.getCity().add(DistrictsType.CATHEDRALE);// religieux

        robotRandomEveque.winGoldsByTypeOfBuildings();

        assertEquals(6, robotRandomEveque.getGolds());

    }

    @Test
    void testScoreAvecUniversiteEtDracoport() {
        robotRandom.setGolds(100);
        robotRandom.setCharacter(CharactersType.ASSASSIN);
        robotRandom.addDistrict(DistrictsType.DRACOPORT);
        robotRandom.addDistrict(DistrictsType.UNIVERSITE);
        robotRandom.tryBuild();
        robotRandom.tryBuild();
        assertEquals(16, robotRandom.calculateScore());
    }

    @Test
    void testNumberOfCardsDrawnWithObservatoire() {
        robotRandom.setCharacter(CharactersType.ASSASSIN);
        robotRandom.setGolds(1000);
        robotRandom.addDistrict(DistrictsType.OBSERVATOIRE);
        assertEquals(2, robotRandom.getNumberOfCardsDrawn());
        robotRandom.tryBuild();
        assertEquals(3, robotRandom.getNumberOfCardsDrawn());
    }

    @Test
    void districtAlreadyInCity() {
        robotRandom.setCharacter(CharactersType.ASSASSIN);
        robotRandom.setGolds(1000);
        robotRandom.addDistrict(DistrictsType.OBSERVATOIRE);
        robotRandom.addDistrict(DistrictsType.OBSERVATOIRE);
        robotRandom.tryBuild();
        robotRandom.tryBuild();
        assertEquals(1, robotRandom.getCity().size());
        robotRandom.addDistrict(DistrictsType.TAVERNE);
        robotRandom.tryBuild();
        assertEquals(2, robotRandom.getCity().size());
    }

    @Test
    void setChoice() {
        Robot bot = new RobotRandom("Vermouth");
        bot.setChoice(2);
        assertEquals(bot.getChoice(), 2);
    }

    @Test
    void testManufacture() {
        robotRandom.setGolds(10);
        robotRandom.getCity().add(DistrictsType.MANUFACTURE);
        DeckDistrict deck = new DeckDistrict();

        robotRandom.manufacture(deck);

        assertEquals(7, robotRandom.getGolds(), "Le robot devrait avoir 7 pièces d'or après l'utilisation de MANUFACTURE");
        assertEquals(3, robotRandom.getDistrictInHand().size(), "Le robot devrait avoir pioché 3 cartes de district");
    }

}


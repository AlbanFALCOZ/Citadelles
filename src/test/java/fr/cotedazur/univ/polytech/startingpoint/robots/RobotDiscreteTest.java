package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RobotDiscreteTest {

    private RobotDiscrete robot;

    @BeforeEach
    public void setUp() {
        robot = new RobotDiscrete("TestRobot");
    }

    @Test
    public void testCountDistrictsByType() {
        robot.getCity().addAll(Arrays.asList(DistrictsType.MANOIR, DistrictsType.CHATEAU, DistrictsType.PALAIS, DistrictsType.PORT, DistrictsType.MONASTERE));
        int count = robot.countDistrictsByType("noble");
        assertEquals(3, count);
    }

    @Test
    public void testCountDistrictsInHandByType() {
        robot.getDistrictInHand().addAll(Arrays.asList(DistrictsType.MANOIR, DistrictsType.CHATEAU, DistrictsType.PALAIS, DistrictsType.PORT, DistrictsType.MONASTERE));
        int count = robot.countDistrictsInHandByType("noble");
        assertEquals(3, count);
    }

    @Test
    void testPickCharacter() {
        List<CharactersType> availableCharacters = new ArrayList<>(Arrays.asList(CharactersType.ROI, CharactersType.EVEQUE, CharactersType.CONDOTTIERE, CharactersType.MARCHAND));
        robot.getCity().addAll(Arrays.asList(DistrictsType.MANOIR, DistrictsType.CHATEAU, DistrictsType.PALAIS, DistrictsType.PORT, DistrictsType.MONASTERE));
        List<CharactersType> availableCharactersCopy = new ArrayList<>(availableCharacters);
        robot.pickCharacter(availableCharactersCopy);
        assertEquals(CharactersType.ROI, robot.getCharacter());
    }
    @Test
    void tryBuild_shouldBuildDistrictIfAffordableAndNotAlreadyBuilt() {
        RobotDiscrete robot = new RobotDiscrete("TestRobot");
        robot.setGolds(10);

        robot.addDistrict(DistrictsType.PALAIS);

        String result = robot.tryBuild();

        assertEquals("a new Palais", result);
        assertEquals(5, robot.getCity().get(0).getCost());
        assertEquals(5, robot.getGolds());
        assertTrue(robot.getDistrictInHand().isEmpty());
    }

    @Test
    void tryBuild_shouldNotBuildDistrictIfNotAffordable() {
        // Arrange
        RobotDiscrete robot = new RobotDiscrete("TestRobot");
        robot.setGolds(2);
        robot.addDistrict(DistrictsType.CASERNE);

        String result = robot.tryBuild();

        assertEquals("nothing", result);
        assertTrue(robot.getCity().isEmpty());
        assertEquals(2, robot.getGolds());
        assertEquals(1, robot.getDistrictInHand().size()); // District should remain in hand
    }

    @Test
    void tryBuild_shouldNotBuildDistrictIfAlreadyBuilt() {
        // Arrange
        RobotDiscrete robot = new RobotDiscrete("TestRobot");
        robot.setGolds(10);
        robot.addDistrict(DistrictsType.CASERNE);
        robot.getCity().add(DistrictsType.CASERNE);

        // Act
        String result = robot.tryBuild();

        // Assert
        assertEquals("nothing", result);
        assertEquals(1, robot.getCity().size());
        assertEquals(10, robot.getGolds());
        assertFalse(robot.getDistrictInHand().isEmpty());
    }

}
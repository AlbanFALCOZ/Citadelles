package fr.cotedazur.univ.polytech.startingpoint.districts;

import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictsTypeTest {


    @Test
    void getCost() {
        assertEquals(3, DistrictsType.MANOIR.getCost());
        assertEquals(4, DistrictsType.CHATEAU.getCost());

    }

    @Test
    void getName() {
        assertEquals("Manoir", DistrictsType.MANOIR.getName());
        assertEquals("Château", DistrictsType.CHATEAU.getName());

    }

    @Test
    void getColor() {
        assertEquals("\u001B[33m", DistrictsType.MANOIR.getColor().getColorDisplay());
        assertEquals("\u001B[33m", DistrictsType.CHATEAU.getColor().getColorDisplay());

    }

    @Test
    void getScore() {
        assertEquals(3, DistrictsType.MANOIR.getScore());
        assertEquals(4, DistrictsType.CHATEAU.getScore());

    }

    @Test
    void getColorReset() {
        assertEquals("\u001B[37m", DistrictsType.MANOIR.getColorReset());
        assertEquals("\u001B[37m", DistrictsType.CHATEAU.getColorReset());

    }



    @Test
    void toStringTest() {
        assertEquals("(Manoir, 3)", DistrictsType.MANOIR.toString());
        assertEquals("(Château, 4)", DistrictsType.CHATEAU.toString());

    }

    @Test
    void getType() {
        assertEquals("noble", DistrictsType.MANOIR.getType());
        assertEquals("noble", DistrictsType.CHATEAU.getType());

    }
}
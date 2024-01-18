package fr.cotedazur.univ.polytech.startingpoint.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharactersTypeTest {

    @Test
    void getNumber() {
        assertEquals(1, CharactersType.ASSASSIN.getNumber());
        assertEquals(2, CharactersType.VOLEUR.getNumber());

    }

    @Test
    void getRole() {
        assertEquals("Assassin", CharactersType.ASSASSIN.getRole());
        assertEquals("Voleur", CharactersType.VOLEUR.getRole());

    }

    @Test
    void getType() {
        assertEquals("assassin", CharactersType.ASSASSIN.getType());
        assertEquals("voleur", CharactersType.VOLEUR.getType());

    }

    @Test
    void getColor() {
        assertEquals("\u001B[37;1m", CharactersType.ASSASSIN.getColor());
        assertEquals("\u001B[37;1m", CharactersType.VOLEUR.getColor());

    }
}
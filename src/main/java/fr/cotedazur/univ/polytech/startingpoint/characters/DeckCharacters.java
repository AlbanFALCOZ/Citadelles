package fr.cotedazur.univ.polytech.startingpoint.characters;

import java.util.*;

public class DeckCharacters {
    private List<CharactersType> charactersInHand;


    public DeckCharacters() {
        this.charactersInHand = new ArrayList<>(Arrays.asList(CharactersType.values()));
    }
    public List<CharactersType> getCharactersInHand() {
        return new ArrayList<>(charactersInHand);

    }

}


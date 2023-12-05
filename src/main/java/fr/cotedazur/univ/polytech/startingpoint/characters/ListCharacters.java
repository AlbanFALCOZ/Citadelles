package fr.cotedazur.univ.polytech.startingpoint.characters;

import java.util.ArrayList;
import java.util.List;

public class ListCharacters {
    private List<CharactersType> charactersInHand;

    public ListCharacters() {
        this.charactersInHand = new ArrayList<>();
    }
    public List<CharactersType> getCharactersInHand() {
        return new ArrayList<>(charactersInHand);
    }
}


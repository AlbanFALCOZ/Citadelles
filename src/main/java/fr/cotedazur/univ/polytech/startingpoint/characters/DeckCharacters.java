package fr.cotedazur.univ.polytech.startingpoint.characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeckCharacters {
    private List<CharactersType> charactersInHand;

    public class ListCharacters {
        private List<CharactersType> charactersInHand;

        public ListCharacters() {

            this.charactersInHand = new ArrayList<>(Arrays.asList(CharactersType.values()));
        }

        public List<CharactersType> getCharactersInHand() {
            return new ArrayList<>(charactersInHand);
        }
    }
}


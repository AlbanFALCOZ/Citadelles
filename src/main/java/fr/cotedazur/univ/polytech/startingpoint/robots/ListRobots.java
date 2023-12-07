package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListRobots extends ArrayList<Robot> {


        private List<Robot> sortedRobots = new ArrayList<>();

        public List<Robot> getSortedRobots() {
            return sortedRobots;
        }

        public void assignRandomCharacterToRobots(DeckCharacters deckCharacters) {
            Collections.shuffle(this);
            Collections.shuffle(deckCharacters.getCharactersInHand());

            for (int i = 0; i < this.size(); i++) {
                this.get(i).setCharacter(deckCharacters.getCharactersInHand().get(i));
            }
        }

        public void sortRobots() {
            Collections.sort(sortedRobots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        }
}




package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private List<Robot> bots = new ArrayList<>();
    private DeckDistrict deckDistrict;
    private DeckCharacters deckCharacters;

    public GameEngine() {
        deckDistrict = new DeckDistrict();
        deckCharacters = new DeckCharacters();
        initializeBots();
        playTurns();
    }


    private void initializeBots() {
        for (int i = 0; i < 4; i++) {
            bots.add(new Robot(i));
        }
    }

    public List<Robot> getBots() {
        return bots;
    }


    public void assignRandomCharacterToRobots() {
        Collections.shuffle(deckCharacters.getCharactersInHand());

        for (int i = 0; i < bots.size(); i++) {
            bots.get(i).setCharacter(deckCharacters.getCharactersInHand().get(i));
        }
    }


    private void playTurns() {
        for (Robot bot : bots) {
            bot.startTurn(); //début du tour pour chaque bot
        }
    }

}
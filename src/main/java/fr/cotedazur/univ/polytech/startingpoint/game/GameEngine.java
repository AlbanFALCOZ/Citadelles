package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.sql.SQLOutput;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private List<Robot> bots = new ArrayList<>();
    private DeckDistrict deckDistricts;
    private DeckCharacters deckCharacters;

    public GameEngine() {
        deckDistricts = new DeckDistrict();
        deckCharacters = new DeckCharacters();
        initializeBots();

    }

    public void initializeBots() {
        for (int i = 0; i < 4; i++) {
            Robot bot = new Robot("Bot " + i);
            for (int j = 0; j < 4; j++) {
                bot.addDistrict(deckDistricts.getDistrictsInDeck());
            }
            bots.add(bot);
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

    public void playTurns() {

        for (Robot bot : bots) {
            bot.startTurn(); //début du tour pour chaque bot
            System.out.println(bot.getName() + " gagne 2 golds. Total golds maintenant: " + bot.getGolds());
        }
    }
    private void districtConstructions(){
        for (Robot bot : bots){
            String builtBuilding = bot.tryBuild();
            System.out.println(bot.getName() + "Built a new disctrict" + builtBuilding);
        }
    }


    public void calculateScores() {
        for (Robot bot : bots) {
            int score = bot.calculateScore();
            System.out.println(bot.getName() + " a un score de " + score);
        }
    }






}
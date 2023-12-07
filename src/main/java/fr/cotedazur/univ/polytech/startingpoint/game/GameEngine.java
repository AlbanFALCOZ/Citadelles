package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.sql.SQLOutput;
import java.util.*;

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
        String name[] = {"Alban","Sara","Stacy","Nora"};
        for (int i = 0; i < 4; i++) {
            Robot bot = new Robot(name[i]);
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
            int choice = (int) (Math.random()*2);
            System.out.println(choice);
            switch (choice) {
                case 0:
                    bot.pickDistrictCard();
                    System.out.println(bot.getName() + " a maintenant dans sa main: " + bot.getNumberOfDistrictInHand() + " districts");
                    System.out.println(bot.getName() + " built " + bot.tryBuild() + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
                    break;
                case 1:
                    bot.setGolds(bot.getGolds() + 2);
                    System.out.println(bot.getName() + " gagne 2 golds. Total golds maintenant: " + bot.getGolds());
                    System.out.println(bot.getName() + " built " + bot.tryBuild() + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
                    break;
                default:
                    System.out.println("Vous n'avez pas choisi une option valide");
                    break;
            }

        }
    }
    public void districtConstructions(){
        for (Robot bot : bots){
            String builtBuilding = bot.tryBuild();
            if(builtBuilding!=null) {
                System.out.println(bot.getName() + " built a new " + builtBuilding + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
            }
        }
    }


    public void calculateScores() {
        for (Robot bot : bots) {
            int score = bot.calculateScore();
            System.out.println(bot.getName() + " a un score de " + score);
        }

    }

    public String getWinner(){
        Robot winner = bots.get(0);
        for (Robot bot : bots){
            if(bot.calculateScore() > winner.calculateScore()){
                winner = bot;
            }
        }
        return winner.getName();

    }

    public ArrayList<Robot> sortRobots(){
        ArrayList<Robot> sortedBots = new ArrayList<>();
         Collections.sort(sortedBots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
         return sortedBots;
    }













}
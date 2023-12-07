package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.*;

public class GameEngine {

    private ArrayList<Robot> bots = new ArrayList<>();
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
        List<CharactersType> ListCharacters = deckCharacters.getCharactersInHand();
        Collections.shuffle(ListCharacters);

        for (int i = 0; i < bots.size(); i++) {
            bots.get(i).setCharacter(ListCharacters.get(i));
        }
    }

    public void assignCrown(){
        Collections.shuffle(bots);
        bots.get(0).setHasCrown(true);
        Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        System.out.println(bots.get(0).getName() + " has crown");


    }

    public void playTurns() {
        assignCrown();
        for (Robot bot : bots) {
            int choice = (int) (Math.random()*2);
            System.out.println(bot.statusOfPlayer());
            switch (choice) {
                case 0:
                    DistrictsType district = bot.pickDistrictCard();
                    System.out.println("Le bot a choisi de piocher : " + district.getColor() + district + district.getColorReset());
                    System.out.println(bot.getName() + " has now in hand: " + bot.getNumberOfDistrictInHand() + " districts");
                    System.out.println(bot.getName() + " built " + bot.tryBuild() + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
                    System.out.println(bot.statusOfPlayer() + "\n");
                    break;
                case 1:
                    bot.setGolds(bot.getGolds() + 2);
                    System.out.println(bot.getName() + " earn 2 golds. Total golds now: " + bot.getGolds());
                    System.out.println(bot.getName() + " built " + bot.tryBuild() + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
                    System.out.println(bot.statusOfPlayer() + "\n");
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


















}
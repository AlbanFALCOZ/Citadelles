package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.sql.SQLOutput;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameEngine {

    private List<Robot> bots = new ArrayList<>();
    private DeckDistrict deckDistricts;
    private DeckCharacters deckCharacters;

    public GameEngine() {
        deckDistricts = new DeckDistrict();
        deckCharacters = new DeckCharacters();
        this.bots = new ArrayList<>();
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
            bot.startTurn(); //début du tour pour chaque bot
            bot.pickDistrictCard();
            System.out.println(bot.getName() + " gagne 2 golds. Total golds maintenant: " + bot.getGolds() + " et a dans sa main: " + bot.getNumberOfDistrictInHand());
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


    public void clearBots() {
        bots.clear();
    }


    public void addRobot(Robot robot) {
        this.bots.add(robot);
    }

    public List<String> getWinners() {

        List<Robot> winners = new ArrayList<>();
        int highestScore = -1;

        for (Robot bot : bots) {
            int score = bot.calculateScore();
            if (score > highestScore) {
                winners.clear();
                winners.add(bot);
                highestScore = score;
            }
            else if (score == highestScore) {
                winners.add(bot);
            }
        }

        List<String> winnerNames = new ArrayList<>();
        for (Robot winner : winners) {
            winnerNames.add(winner.getName());
        }

        return winnerNames;
    }



    public void showWinners() {
        List<String> winners = getWinners();
        if (winners.size() == 1) {
            System.out.println("Le gagnant est : " + winners.get(0));
        }
        else {
            System.out.println("Il y a une égalité ! Les gagnants sont : " + String.join(", ", winners));
        }
    }


    public ArrayList<Robot> sortRobots(){
        ArrayList<Robot> sortedBots = new ArrayList<>();
         Collections.sort(sortedBots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
         return sortedBots;
    }


}
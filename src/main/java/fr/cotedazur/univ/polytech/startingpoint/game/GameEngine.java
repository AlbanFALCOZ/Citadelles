package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.*;

public class GameEngine {

    private ArrayList<Robot> bots;
    private DeckDistrict deckDistricts;
    private DeckCharacters deckCharacters;
    private Round round;
    
    public GameEngine() {
        deckDistricts = new DeckDistrict();
        deckCharacters = new DeckCharacters();
        this.bots = new ArrayList<>();
        round = new Round(bots);
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

    public void robotsPickCharacters() {
        int i = 1;
        List<CharactersType> ListCharacters = deckCharacters.getCharactersInHand();
        destroyCharacters(ListCharacters);
        Collections.shuffle(ListCharacters);
        for (Robot bot : bots ){
            if(bot.getHasCrown()){
                bot.setCharacter(ListCharacters.get(0));
                System.out.println(bot.getName() +" With crown Picked " +ListCharacters.get(0).getColor() + ListCharacters.get(0).getType() + bot.getRESET());
                ListCharacters.remove(ListCharacters.get(0));
            }
        }
        for (Robot bot : bots){
            if(!bot.getHasCrown()){
                bot.setCharacter(ListCharacters.get(i));
                System.out.println(bot.getName() +" Picked " +ListCharacters.get(i).getColor() + ListCharacters.get(i).getType() + bot.getRESET());
                i++;
            }
        }
    }

    public void assignCrown(){
        Collections.shuffle(bots);
        bots.get(0).setHasCrown(true);
        System.out.println(bots.get(0).getName() + " has crown and start the call of the characters");
        Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));

    }

    public boolean isBuiltEigthDistrict(){
        for (Robot bot : bots){
            if(bot.getNumberOfDistrictInCity()==8){
                return true;
            }
        }
        return false;
    }

    public void assignRandomCharacterToRobots() {
        // Get the characters available in hand
        List<CharactersType> charactersInHand = deckCharacters.getCharactersInHand();

        // Shuffle the characters
        Collections.shuffle(charactersInHand);

        // Assign characters to each bot
        for (int i = 0; i < bots.size(); i++) {
            bots.get(i).setCharacter(charactersInHand.get(i));
        }
    }


    public void gameTurns(){
        round = new Round(bots);
        String turnStarting = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Turn ";
        String turnEnding = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        System.out.println("=============================================================================GAME IS STARTING====================================================================\n");
        int comptTurn = 1;
        assignRandomCharacterToRobots();
        assignCrown();
        robotsPickCharacters();
        while(!isBuiltEigthDistrict() && comptTurn++>=1){
            System.out.println(turnStarting + comptTurn + " is starting" + turnEnding);
            bots.sort(Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
            for (Robot bot : bots) {
                if (bot.getHasCrown()) {
                    System.out.println(bot.getName() + " has crown and start the call of the characters");
                }
            }
            robotsPickCharacters();
            System.out.println(round.playTurns());
            System.out.println(turnStarting + comptTurn + " is over" + turnEnding);
            comptTurn++;
            round = new Round(bots);
        }
    }

    public void clearBots() {
        bots.clear();
    }


    public void addRobot(Robot robot) {
        this.bots.add(robot);
    }

    public void destroyCharacters(List<CharactersType> charactersInHand) {
        charactersInHand.remove(CharactersType.ROI);
        Collections.shuffle(charactersInHand, new Random());
        for (int i = 0; i < 3; i++) {
            if (!charactersInHand.isEmpty()) {
                CharactersType destroyedCharacter = charactersInHand.remove(0);
                System.out.println("Destroyed character: " + destroyedCharacter.getColor() + destroyedCharacter.getType() + bots.get(0).getRESET());
            }
        }
        charactersInHand.add(CharactersType.ROI);
    }



}
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

    public void assignCrownForKing() {
        int cpt = 0;
        int index = 0 ;
        for (Robot bot : bots) {
            if (bot.isCharacter("Roi")) {
                cpt++;
            }
        }
        if (cpt == 1) {
            for (Robot bot : bots) {
                if (bot.isCharacter("Roi")) {
                    bot.setHasCrown(true);
                }
                if (!bot.isCharacter("Roi")) {
                    bot.setHasCrown(false);
                }
            }
            Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
            cpt=0;
        }
        Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        Collections.rotate(bots, -index);

    }

    public void playTurns() {
        specialCard();
        Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        this.sortRobots();
        for (Robot bot : bots) {

            int choice = (int) (Math.random()*2);
            System.out.println("------------------------------------------------------------The turn of " + bot.getName() + " is starting -----------------------------------------------------\n");
            System.out.println(bot.statusOfPlayer());
            switch (choice) {
                case 0:
                    DistrictsType district = bot.pickDistrictCard();
                    System.out.println("The bot choose to pick : " + district.getColor() + district + district.getColorReset());
                    System.out.println(bot.getName() + " has now in hand: " + bot.getNumberOfDistrictInHand() + " districts");
                    break;
                case 1:
                    bot.addGold(2);
                    System.out.println(bot.getName() + " earn 2 golds. Total golds now: " + bot.getGolds());
                    break;
                default:
                    break;
            }
            System.out.println(bot.getName() + " built " + bot.tryBuild() + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
            System.out.println(bot.getName() + " has won " + bot.winGoldsByTypeOfBuildings() + " golds by " + bot.getCharacter().getType() + " buildings and has now " + bot.getGolds() + " golds");
            System.out.println(bot.statusOfPlayer() + "\n");
            System.out.println("-------------------------------------------------------The turn of " + bot.getName() + " is over ------------------------------------------------------------------\n");

        }
        assignCrownForKing();
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
        System.out.println("=============================================================================GAME IS STARTING====================================================================\n");
        int comptTurn = 1;
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Turn " + comptTurn + " is starting+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        assignRandomCharacterToRobots();
        assignCrown();
        robotsPickCharacters();
        playTurns();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Turn " + comptTurn + " is over+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        comptTurn++;
        while(!isBuiltEigthDistrict() && comptTurn>1){
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Turn " + comptTurn + " is starting+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
            for (Robot bot : bots) {
                if (bot.getHasCrown()) {
                    System.out.println(bot.getName() + " has crown and start the call of the characters");
                }
            }
            robotsPickCharacters();
            playTurns();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Turn " + comptTurn + " is over+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
            comptTurn++;
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
            System.out.println(bot.getName() + " has a score of " + score);
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

    public void specialCard(){
        for (Robot bot : bots){
            if(bot.getCharacter().getNumber()==6){
                bot.addGold(1);
            }
        }
    }



    public void showWinners() {
        List<String> winners = getWinners();
        if (winners.size() == 1) {
            System.out.println("The winner is : " + winners.get(0));
        }
        else {
            System.out.println("This is an equality ! The winners are: " + String.join(", ", winners));
        }
    }


    public ArrayList<Robot> sortRobots() {
        ArrayList<Robot> sortedBots = new ArrayList<>(bots);

        // Custom comparator to sort by crown status and then by character number
        Comparator<Robot> crownComparator = Comparator.comparing((Robot bot) -> !bot.getHasCrown())
                .thenComparingInt(bot -> bot.getCharacter().getNumber());

        // Sort the list using the custom comparator
        Collections.sort(sortedBots, crownComparator);

        return sortedBots;
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
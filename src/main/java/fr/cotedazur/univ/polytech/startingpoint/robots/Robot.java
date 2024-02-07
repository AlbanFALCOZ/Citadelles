package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.*;

public abstract class Robot{

    public static final String RESET = "\u001B[37m";
    protected String name;
    protected int choice;
    protected int score;
    protected int golds;
    protected int numberOfCardsDrawn = 2;
    protected int numberOfCardsChosen = 1;
    private String typeOfRobot = "Robot";
    protected List<DistrictsType> districtInHand;
    protected CharactersType character;

    protected ArrayList<DistrictsType> city;

    protected boolean hasCrown;

    private Map<String, List<CharactersType>> characterHistory;
    private Map<String, List<DistrictsType>> buildingHistory;
    private Map<String, Integer> handSizeHistory;

    public Robot(String name) {
        this.name = name;
        score = 0;
        districtInHand = new ArrayList<>();
        golds = 2;
        character = null;
        city = new ArrayList<>();
        hasCrown = false;
        IsAssassinated = false;
        this.characterHistory = new HashMap<>();
        this.buildingHistory = new HashMap<>();
        this.handSizeHistory = new HashMap<>();


    }


    protected boolean IsAssassinated;
    public List<DistrictsType> getDistrictInHand() {
        return districtInHand;
    }

    public void setDistrictInHand(List<DistrictsType> listDistrict) {
        this.districtInHand = listDistrict;

    }

    public boolean getIsAssassinated() {
        return IsAssassinated;
    }

    public void setIsAssassinated(boolean IsAssassinated) {
        this.IsAssassinated = IsAssassinated;
    }


    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRESET() {
        return RESET;
    }

    public String getName() {
        return name;
    }

    public int getGolds() {
        return golds;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public int getNumberOfCardsDrawn() {
        return numberOfCardsDrawn;
    }

    public void setNumberOfCardsDrawn(int numberOfCardsDrawn) {
        this.numberOfCardsDrawn = numberOfCardsDrawn;
    }

    public int getNumberOfCardsChosen() {
        return numberOfCardsChosen;
    }

    public void setNumberOfCardsChosen(int numberOfCardsChosen) {
        this.numberOfCardsChosen = numberOfCardsChosen;
    }


    public void addGold(int golds) {
        this.golds += golds;
    }

    public CharactersType getCharacter() {
        return character;
    }

    public void setCharacter(CharactersType character) {
        this.character = character;
    }

    public List<DistrictsType> getCity() {
        return city;
    }


    public int getNumberOfDistrictInHand() {
        return districtInHand.size();
    }

    public int getNumberOfDistrictInCity() {
        return city.size();
    }

    public String getTypeOfRobot() {
        return typeOfRobot;
    }

    public void setTypeOfRobot(String typeOfRobot) {
        this.typeOfRobot = typeOfRobot;
    }

    public boolean getHasCrown() {
        return hasCrown;
    }

    public void setHasCrown(boolean hasCrown) {
        this.hasCrown = hasCrown;

    }

    public int calculateScoreInHand(){
        int score = 0;
        for (DistrictsType district : districtInHand) {
            score += district.getScore();
        }
        return score;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public boolean hasEightDistrict() {
        return (this.getNumberOfDistrictInCity()==8);
    }

    public void addDistrict(DistrictsType district) {
        this.districtInHand.add(district);
    }

    public void addDistrict(List<DistrictsType> listDistrict) {
        this.districtInHand.addAll(listDistrict);
    }

    public String statusOfPlayer(boolean showColor) {
        String colorCharacter = "";
        if (showColor) {
            colorCharacter = character.getColor().getColorDisplay();
        }
        String status = RESET + "[Status of " + this.name + " : role (" + colorCharacter + this.character.getRole() + RESET + "), \u001B[33m" + this.golds + " golds" + RESET + ", hand {";
        status += getString(showColor, districtInHand) + "}, city {" + getString(showColor, city) + "}]";
        return status;
    }

    public String statusOfPlayer() {
        return statusOfPlayer(true);
    }

    private String getString(boolean showColor, List<DistrictsType> listDistrict) {
        String returedString = "";
        String color;
        String endColor;
        for (DistrictsType districtsType : listDistrict) {
            if (showColor) {
                color = districtsType.getColor().getColorDisplay();
                endColor = RESET;
            } else {
                color = endColor = "";
            }
            returedString += "(" + color + districtsType.getName() + "," + districtsType.getCost() + endColor + ")";
        }
        return returedString;
    }

    public Comparator<DistrictsType> compareByCost() {
        return Comparator.comparingInt(DistrictsType::getCost);
    }


    public int calculateScore() {
        int score = 0;
        for (DistrictsType district : city) {
            score += district.getScore();
        }
        return score;
    }

    public int countBuildingsByType() {
        int count = 0;

        for (DistrictsType building : city) {

            if (building.getType().equals(this.character.getType()) || building.getType().equals("ecole")) {
                count++;
            }

        }
        return count;
    }

    public int winGoldsByTypeOfBuildings() {
        int oldGolds = this.getGolds();
        addGold(countBuildingsByType());
        return this.getGolds() - oldGolds;
    }

    public boolean isCharacter(String type) {
        return this.getCharacter().getType().equals(type);
    }

    public boolean canBuildADistrictInHand() {
        for (DistrictsType district : districtInHand) {
            if (golds >= district.getCost()) return true;
        }
        return false;
    }

    public void emptyListOfCardsInHand() {
        districtInHand.clear();
    }

    public List<DistrictsType> pickListOfDistrict(DeckDistrict deck) {
        List<DistrictsType> listDistrict = new ArrayList<>();
        for (int i = 0; i < numberOfCardsDrawn; i++) {
            DistrictsType card = deck.getDistrictsInDeck();
            listDistrict.add(card);
        }
        return listDistrict;
    }


    public void specialCards(DeckDistrict deck, ActionOfBotDuringARound action) {
        if (getCity().contains(DistrictsType.MANUFACTURE)) {
            List<DistrictsType> listOfDistrictPicked = manufacture(deck);
            if (!listOfDistrictPicked.isEmpty()) action.printManufactureAction(listOfDistrictPicked);
        }
        if (getCity().contains(DistrictsType.LABORATOIRE)) {
            List<DistrictsType> listOfDistrictRemoved = laboratoire(deck);
            if (!listOfDistrictRemoved.isEmpty()) action.printLaboratoryAction(listOfDistrictRemoved);
        }
    }


    public abstract String tryBuild();

    public abstract List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck);

    public abstract int generateChoice();


    public List<DistrictsType> manufacture(DeckDistrict deck) {
        List<DistrictsType> listOfDistrictPicked = new ArrayList<>();
        if (getGolds() >= 3) {
            setGolds(getGolds() - 3);
            for (int i = 0; i < 3; i++) {
                DistrictsType card = deck.getDistrictsInDeck();
                listOfDistrictPicked.add(card);
                addDistrict(card);
            }
        }
        return listOfDistrictPicked;
    }


    public List<DistrictsType> laboratoire(DeckDistrict deck){
        List<DistrictsType> listOfDistrictRemoved = new ArrayList<>();
        if (getNumberOfDistrictInHand() >= 1) {
            int indexOfDistrictInHandToRemove = (int) (Math.random()*getNumberOfDistrictInHand());
            DistrictsType card = districtInHand.remove(indexOfDistrictInHandToRemove);
            listOfDistrictRemoved.add(card);
            deck.addDistrictToDeck(card);
            setGolds(getGolds()+1);
        }
        return listOfDistrictRemoved;
    }


    public abstract void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots);

    public Robot chooseVictimForCondottiere(List<Robot> bots){
        Robot victim = bots.get(0);
        int numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() >= numberOfDistrictsInCity && bot.getCharacter()!= CharactersType.CONDOTTIERE && !victim.hasEightDistrict()) {
                victim = bot;
                numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
            }
        }
        return victim;

    }

    public Robot chooseVictimForAssassin(List<Robot> bots,int numberOfTheCharacterToKill){
        for (Robot bot : bots) {
            if (bot.getCharacter().getNumber() == numberOfTheCharacterToKill) return bot;
        }
        return null;

    }

    public Robot chooseVictimForMagicien(List<Robot> bots){
        Robot victim = bots.get(0);
        int numberOfDistrictsInHand = victim.getNumberOfDistrictInHand();
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInHand() >= numberOfDistrictsInHand && bot.getCharacter()!= CharactersType.MAGICIEN) victim = bot;
        }
        return victim;
    }

    public CharactersType chooseVictimForVoleur(List<Robot> bots){
        List<CharactersType> characters = new ArrayList<>(Arrays.asList(CharactersType.values()));
        Collections.shuffle(characters);
        CharactersType victim = characters.get(0);
        return victim;

    }

    public Map<String, List<CharactersType>> getCharacterHistory() {
        return characterHistory;
    }

    public Map<String, Integer> getHandSizeHistory() {
        return handSizeHistory;
    }

    public Map<String, List<DistrictsType>> getBuildingHistory() {
        return buildingHistory;
    }

    public void updateHistory(List<Robot> bots) {
        for (Robot bot : bots) {
            String botName = bot.getName();
            CharactersType chosenCharacter = bot.getCharacter();

            List<DistrictsType> builtDistricts = bot.getCity();
            Integer handSize = bot.getDistrictInHand().size();

            characterHistory.putIfAbsent(botName, new ArrayList<>());
            characterHistory.get(botName).add(chosenCharacter);

            for(DistrictsType district : builtDistricts){
                buildingHistory.putIfAbsent(botName, new ArrayList<>());
                if (!buildingHistory.get(botName).contains(district)) {
                    buildingHistory.get(botName).add(district);
                }
            }
            handSizeHistory.put(botName, handSize);
        }
    }

    public CharactersType predictOpponentNextCharacter(String botName) {
        List<CharactersType> characterHistory = this.getCharacterHistory().get(botName);

        if (characterHistory == null || characterHistory.isEmpty()) {
            return null;
        }

        Map<CharactersType, Integer> characterFrequency = new HashMap<>();
        for (CharactersType character : characterHistory) {
            characterFrequency.put(character, characterFrequency.getOrDefault(character, 0) + 1);
        }

        return Collections.max(characterFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Integer countOpponentNextCharacter(String botName) {
        List<CharactersType> characterHistory = this.getCharacterHistory().get(botName);

        if (characterHistory == null || characterHistory.isEmpty()) {
            return null;
        }

        Map<CharactersType, Integer> characterFrequency = new HashMap<>();
        for (CharactersType character : characterHistory) {
            characterFrequency.put(character, characterFrequency.getOrDefault(character, 0) + 1);
        }

        return Collections.max(characterFrequency.entrySet(), Map.Entry.comparingByValue()).getValue();
    }



    public int getNumberOfCharacterToKill(List<Robot> bots) {
        return (int) (Math.random() * (8-2) + 2);
    }


}

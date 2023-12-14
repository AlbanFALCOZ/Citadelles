package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.complements.Strategies;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.*;



public class Robot {

    private String name;
    private int score;
    private int golds;
    private Strategies strategies;
    private DeckDistrict district = new DeckDistrict();
    private List<DistrictsType> districtInHand;
    private CharactersType character;
    public static final String RESET = "\u001B[0m";

    private ArrayList<DistrictsType> city;

    private boolean hasCrown;

    private boolean isKing;

    private boolean isEveque;


    public Robot(String name) {
        this.name = name;
        this.score = 0;
        this.districtInHand = new ArrayList<>();
        this.golds = 2;
        this.character = null;
        this.city = new ArrayList<>();
        this.hasCrown = false;
    }


    public int getScore() {
        return this.score;
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

    public void setScore(int score) {
        this.score = score;
    }

    public void setDistrict(DeckDistrict district) {
        this.district = district;
    }

    public void addGold(int golds) {
        this.golds += golds;
    }


    public void setCharacter(CharactersType character) {
        this.character = character;
    }


    public CharactersType getCharacter() {
        return character;
    }

    public ArrayList<DistrictsType> getCity() {
        return city;
    }

    public void setHasCrown(boolean hasCrown) {
        this.hasCrown = hasCrown;

    }


    public String tryBuild() {
        for (int i = 0; i < districtInHand.size(); i++) {
            DistrictsType district = districtInHand.get(i);
            if (district.getCost() <= this.getGolds()) {
                city.add(district);
                setGolds(getGolds() - district.getCost());
                districtInHand.remove(i);
                return "a new " + district.name();
            }
        }
        return "nothing";
    }


    public void addDistrict(DistrictsType district) {
        this.districtInHand.add(district);
    }

    public int getNumberOfDistrictInHand() {
        return districtInHand.size();
    }

    public int getNumberOfDistrictInCity() {
        return city.size();
    }

    public String statusOfPlayer(boolean showColor) {
        String endColor = "";
        String colorCharacter = "";
        if (showColor) {
            colorCharacter = character.getColor();
            endColor = RESET;
        }
        String status = endColor + "[Status of " + this.name + " : role (" + colorCharacter + this.character.getType() + endColor + "), " + this.golds + " golds, hand {";
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
        for (int numberOfDistrictInCity = 0; numberOfDistrictInCity < listDistrict.size(); numberOfDistrictInCity++) {
            if (showColor) {
                color = listDistrict.get(numberOfDistrictInCity).getColor();
                endColor = RESET;
            } else {
                color = endColor = "";
            }
            returedString += "(" + color + listDistrict.get(numberOfDistrictInCity).getName() + "," + listDistrict.get(numberOfDistrictInCity).getCost() + endColor + ")";
        }
        return returedString;
    }

    public DistrictsType pickDistrictCard() {
        DistrictsType card1 = district.getDistrictsInDeck();
        DistrictsType card2 = district.getDistrictsInDeck();

        if (card1.getScore() > card2.getScore()) {
            districtInHand.add(card1);
            district.addDistrictToDeck(card2);
            return card1;
        } else {
            districtInHand.add(card2);
            district.addDistrictToDeck(card1);
            return card2;
        }
    }

    public int calculateScore() {
        int score = 0;
        for (DistrictsType district : city) {
            score += district.getScore();
        }
        return score;
    }


    public boolean getHasCrown() {
        return hasCrown;
    }

    /*

    public boolean isKing() {
        if (this.getCharacter().getNumber() == 4) {
            return true;
        }
        return false;
    }

    public boolean isEveque(){
        if(this.getCharacter().getNumber()==5){
            return true;
        }
        return false;
    }

     */

    public boolean isCharacter(String type){
        if (this.getCharacter().getType().equals(type)) {
            return true;
        }
        return false;
    }







}



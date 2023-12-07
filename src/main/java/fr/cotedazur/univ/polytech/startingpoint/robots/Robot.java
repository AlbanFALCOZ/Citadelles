package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.complements.Strategies;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.*;



public class Robot {

    private String name;
    private int golds;
    private Strategies strategies;
    private DeckDistrict district;
    private List<DistrictsType> districtInHand;
    private CharactersType character;
    private DeckCharacters deckCharacters;
    public static final String RESET = "\u001B[0m";

    private ArrayList<DistrictsType> city;


    public Robot(String name) {
        this.name = name;
        this.districtInHand = new ArrayList<>();
        this.golds = 2;
        this.character = null;
        this.city = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public int getGolds() {
        return golds;
    }

    public Strategies getStrategies() {
        return strategies;
    }

    public DeckDistrict getDistrict() {
        return district;
    }

    public DeckCharacters getListCharacters() {
        return deckCharacters;
    }

    public void setGolds(int golds) {
        this.golds = golds;
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


    public void startTurn() {
        this.golds += 2; //incrémente de 2 le nb golds du robot
    }


    public CharactersType getCharacter() {
        return character;
    }

    public ArrayList<DistrictsType> getCity(){
        return city;
    }



    public String tryBuild() {
        for (int i = 0; i < districtInHand.size(); i++) {
            DistrictsType district = districtInHand.get(i);
            if (district.getCost() <= this.getGolds()) {
                city.add(district);
                setGolds(getGolds() - district.getCost());
                districtInHand.remove(i);
                return district.name();
            }
        }
        return null;
    }



    public void addDistrict(DistrictsType district) {
        this.districtInHand.add(district);
    }

    public int getNumberOfDistrictInHand() {
        return districtInHand.size();
    }

    public String statusOfPlayer(boolean showColor) {
        String color;
        String endColor;
        String status = "[Status of " + this.name + " : role (" + this.character.getType() + "), " + this.golds + " golds, hand {";
        for (int numberOfDistrictInCity = 0; numberOfDistrictInCity < districtInHand.size(); numberOfDistrictInCity++) {
            if (showColor) {
                color = districtInHand.get(numberOfDistrictInCity).getColor();
                endColor = RESET;
            }
            else {
                color = "";
                endColor = "";
            }
            status += "(" + color +districtInHand.get(numberOfDistrictInCity).getName() + "," + districtInHand.get(numberOfDistrictInCity).getCost() + endColor + ")";
            }
        status += "}, city {";
        for (int numberOfDistrictInCity = 0; numberOfDistrictInCity < city.size(); numberOfDistrictInCity++) {
            if (showColor) {
                color = city.get(numberOfDistrictInCity).getColor();
                endColor = RESET;
            }
            else {
                color = "";
                endColor = "";
            }
            status += "(" + color + city.get(numberOfDistrictInCity).getName() + "," + city.get(numberOfDistrictInCity).getCost() + endColor +")";
        }
        status += "}]";
        return status;
    }

    public String statusOfPlayer() {
        return statusOfPlayer(true);
    }

    public int calculateScore() {
        int score = 0;
        for (DistrictsType district : city) {
            score += district.getScore();
        }
        return score;
    }



}




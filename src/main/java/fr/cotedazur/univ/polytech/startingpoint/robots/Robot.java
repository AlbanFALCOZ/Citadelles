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




    public String tryBuild() {
        for (DistrictsType district : districtInHand) {
            if (district.getCost() <= this.getGolds()) {
                city.add(district);
                setGolds(getGolds() - district.getCost());
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

    public String statusOfPlayer() {
        String status = "[Status of " + this.name + " : " + this.golds + " golds, hand {";
        for (int numberOfDistrictInCity = 0; numberOfDistrictInCity < districtInHand.size(); numberOfDistrictInCity++) {
            status += "(" + districtInHand.get(numberOfDistrictInCity).getName() + "," + districtInHand.get(numberOfDistrictInCity).getCost() + ")";
        }
        status += "}, city {";
        for (int numberOfDistrictInCity = 0; numberOfDistrictInCity < city.size(); numberOfDistrictInCity++) {
            status += "(" + city.get(numberOfDistrictInCity).getName() + "," + city.get(numberOfDistrictInCity).getCost() + ")";
        }
        status += "}]";
        return status;
    }
}




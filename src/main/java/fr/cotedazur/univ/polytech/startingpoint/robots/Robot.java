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
    private int numberOfCardsDrawn = 2;
    private int numberOfCardsChosen = 1;
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

    public String getRESET(){
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
                district.powerOfDistrict(this);
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
        String status = endColor + "[Status of " + this.name + " : role (" + colorCharacter + this.character.getRole() + endColor + "), " + this.golds + " golds, hand {";
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

    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict) {
        listDistrict.sort(compareByCost().reversed());
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int indice = 0;
        int i = 0;
        while (i < listDistrict.size()) {
            if (listDistrict.get(i).getCost() - costOfDistrictToBeBuilt <= golds) {
                costOfDistrictToBeBuilt += listDistrict.get(i).getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
                i--;
                indice++;
                if (indice == numberOfCardsChosen) break;

            }
            i++;
        }
        while (listDistrictToBuild.size() < numberOfCardsChosen) listDistrictToBuild.add(listDistrict.remove(listDistrict.size()-1));


        for (DistrictsType districtNonChosen: listDistrict) {
            district.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }

    public List<DistrictsType> pickListOfDistrict(){
        List<DistrictsType> listDistrict = new ArrayList<>();
        for (int i = 0; i < numberOfCardsDrawn; i++) {
            DistrictsType card = district.getDistrictsInDeck();
            listDistrict.add(card);
        }
        return listDistrict;
    }

    private Comparator<DistrictsType> compareByCost() {
        return Comparator.comparingInt(DistrictsType::getCost);
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


    public int countBuildingsByType() {
        int count = 0;

        for (DistrictsType building : city) {

            if(building.getType().equals(this.character.getType()) || building.getType().equals("ecole")){
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


    public boolean isCharacter(String type){
        if (this.getCharacter().getType().equals(type)) {
            return true;
        }
        return false;
    }



}


package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.*;



public class RobotRandom implements Robot{

    private final String name;
    private int score;
    private int golds;
    private int numberOfCardsDrawn = 2;
    private int numberOfCardsChosen = 1;
    private DeckDistrict district = new DeckDistrict();
    private List<DistrictsType> districtInHand;
    private CharactersType character;
    public static final String RESET = "\u001B[0m";

    public int choice ;

    private boolean hasFiveColors;

    private ArrayList<DistrictsType> city;

    private boolean hasCrown;



    public RobotRandom(String name) {
        this.name = name;
        this.score = 0;
        this.districtInHand = new ArrayList<>();
        this.golds = 2;
        this.character = null;
        this.city = new ArrayList<>();
        this.hasCrown = false;

    }

    public List<DistrictsType> getDistrictInHand() {
        return districtInHand;
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
        List<String> listDistrictName = new ArrayList<>();
        for (DistrictsType districtsType : city) listDistrictName.add(districtsType.getName());
        for (int i = 0; i < districtInHand.size(); i++) {
            DistrictsType district = districtInHand.get(i);
            if (district.getCost() <= this.getGolds() && !listDistrictName.contains(district.getName()) ) {
                district.powerOfDistrict(this);
                city.add(district);
                setGolds(getGolds() - district.getCost());
                districtInHand.remove(i);
                return "a new " + district.getName();
            }
        }
        return "nothing";
    }


    public void addDistrict(DistrictsType district) {
        this.districtInHand.add(district);
    }

    public void addDistrict(List<DistrictsType> listDistrict) {
        this.districtInHand.addAll(listDistrict);
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
        for (DistrictsType districtsType : listDistrict) {
            if (showColor) {
                color = districtsType.getColor();
                endColor = RESET;
            } else {
                color = endColor = "";
            }
            returedString += "(" + color + districtsType.getName() + "," + districtsType.getCost() + endColor + ")";
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
        return this.getCharacter().getType().equals(type);
    }

    public boolean canBuildADistrictInHand() {
        for (DistrictsType district : districtInHand) {
            if (golds >= district.getCost()) return true;
        }
        return false;
    }



    public int generateChoice() {
        return (int) (Math.random()*2);
    }

    public void setChoice(int choice){
        this.choice = choice ;
    }

    public int getChoice(){
        return choice ;
    }

    public void setDistrictInHand(List<DistrictsType> listDistrict){
        this.districtInHand = listDistrict ;

    }

    @Override
    public void emptyListOfCardsInHand() {
        int a = this.getNumberOfDistrictInHand();
        for (int i = 0; i < a; i++) {
            this.districtInHand.remove(0);
        }

    }

    @Override
    public void setHasFriveColors(boolean b) {
        this.hasFiveColors = b ;
    }

    @Override
    public boolean hasFiveColors() {
        return this.hasFiveColors;
    }


}







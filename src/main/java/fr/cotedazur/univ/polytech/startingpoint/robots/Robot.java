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

    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict) {
        listDistrict.sort(compareByCost().reversed());
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int indice = 0;
        int i = 0;
        System.out.println("listDistrict : " + listDistrict);
        System.out.println("listDistrictToBuild : " + listDistrictToBuild);
        while (i < listDistrict.size()) {
            if (listDistrict.get(i).getCost() - costOfDistrictToBeBuilt <= golds) {
                System.out.println("i : " + i);
                System.out.println("listDistrict : " + listDistrict);
                System.out.println("listDistrictToBuild : " + listDistrictToBuild);
                costOfDistrictToBeBuilt += listDistrict.get(i).getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
                i--;
                indice++;
                if (indice == numberOfCardsChosen) break;

            }
            i++;
        }
        while (listDistrictToBuild.size() < numberOfCardsChosen) listDistrictToBuild.add(listDistrict.remove(listDistrict.size()-1));
        System.out.println("listDistrict : " + listDistrict);
        System.out.println("listDistrictToBuild : " + listDistrictToBuild);
        /*
        System.out.println("Tour ajout");
        for (int indiceOfDistrict : listIndice) {
            cardChosen = listDistrict.remove(indiceOfDistrict);
            listDistrictToBuild.add(cardChosen);
            System.out.println("listIndice : " + listIndice);
            System.out.println("listDistrict : " + listDistrict);
            System.out.println("listDistrictToBuild : " + listDistrictToBuild);
        }
         */


        //districtInHand.add(cardChosen);
        for (DistrictsType districtNonChosen: listDistrict) {
            district.addDistrictToDeck(districtNonChosen);
        }
        //listDistrictToBuild.add(cardChosen);
        return listDistrictToBuild;

        /*
        //System.out.println("ListDistrict : " + listDistrict);
        listDistrict.sort(compareByCost().reversed());
        int indice = 0;
        int costOfDistrictBuilt = 0;
        List<Integer> listIndice = new ArrayList<>();
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        for (int i = 0; i < listDistrict.size();i++) {
            if (listDistrict.get(i).getCost() - costOfDistrictBuilt <= golds) {
                costOfDistrictBuilt += listDistrict.get(i).getCost();
                listIndice.add(i);
                indice++;
                if (indice == numberOfCardsChosen) break;
            }
        }
        System.out.println("numberOfCardsChosen : " + numberOfCardsChosen);
        System.out.println("ListIndice : " + listIndice);
        for (int i = 0; i < listDistrict.size() && i < numberOfCardsChosen-indice; i++) if (!listIndice.contains(i)) listIndice.add(i);
        //while (listIndice.size() < numberOfCardsChosen) listIndice.add(indice--);
        DistrictsType cardChosen;
        listIndice.sort(Collections.reverseOrder());
        System.out.println("ListIndice : " + listIndice);
        System.out.println("ListDistrict : " + listDistrict);
        System.out.println("listDistrictToBuild : " + listDistrictToBuild);
        System.out.println("Tour ajout");
        int length = listIndice.size();
        for (int i = 0; i < length; i++) {
            indice = listIndice.remove(length - i);
            cardChosen = listDistrict.remove(indice);
            districtInHand.add(cardChosen);
            listDistrictToBuild.add(cardChosen);
            System.out.println("ListIndice : " + listIndice);
            System.out.println("ListDistrict : " + listDistrict);
            System.out.println("listDistrictToBuild : " + listDistrictToBuild);
        }
        //System.out.println("listDistrictToBuild : " + listDistrictToBuild);
        for (DistrictsType districtNonChosen: listDistrict) {
            district.addDistrictToDeck(districtNonChosen);
        }
        System.out.println("listDistrictToBuild : " + listDistrictToBuild);
        return listDistrictToBuild;
         */

        /*listDistrict.sort(compareByCost().reversed());
        int indice = listDistrict.size()-1;
        for (int i = 0; i < listDistrict.size();i++) {
            if (listDistrict.get(i).getCost() <= golds) {
                indice = i;
                break;
            }
        }
        DistrictsType cardChosen;
        cardChosen = listDistrict.remove(indice);
        districtInHand.add(cardChosen);
        for (DistrictsType districtNonChosen: listDistrict) {
            district.addDistrictToDeck(districtNonChosen);
        }
        return cardChosen;
         */
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





}



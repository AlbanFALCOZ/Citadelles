package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import java.util.List;
import java.util.ArrayList;

public class RobotRandom extends Robot {

    public RobotRandom(String name) {
        super(name);
    }

    @Override
    public String tryBuild() {
        List<String> listDistrictName = new ArrayList<>();
        for (DistrictsType districtsType : getCity()) listDistrictName.add(districtsType.getName());
        for (int i = 0; i < getDistrictInHand().size(); i++) {
            DistrictsType district = getDistrictInHand().get(i);
            if (district.getCost() <= getGolds() && !listDistrictName.contains(district.getName())) {
                district.powerOfDistrict(this);
                getCity().add(district);
                setGolds(getGolds() - district.getCost());
                getDistrictInHand().remove(i);
                return "a new " + district.getName();
            }
        }
        return "nothing";
    }


    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        listDistrict.sort(compareByCost().reversed());
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int indice = 0;
        int i = 0;
        while (i < listDistrict.size()) {
            if (listDistrict.get(i).getCost() - costOfDistrictToBeBuilt <= getGolds()) {
                costOfDistrictToBeBuilt += listDistrict.get(i).getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
                i--;
                indice++;
                if (indice == getNumberOfCardsChosen()) break;

            }
            i++;
        }
        while (listDistrictToBuild.size() < getNumberOfCardsChosen())
            listDistrictToBuild.add(listDistrict.remove(listDistrict.size() - 1));


        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }

    @Override
    public int generateChoice() {
        return (int) (Math.random() * 2);
    }

    @Override
    public List<DistrictsType> manufacture(DeckDistrict deck) {
        List<DistrictsType> listOfDistrictPicked = new ArrayList<>();
        if (getGolds() >= 3) {
            setGolds(getGolds() - 3); // dépense 3 or
            for (int i = 0; i < 3; i++) {
                DistrictsType card = deck.getDistrictsInDeck();
                listOfDistrictPicked.add(card);
                addDistrict(card);
            }
        }
        return listOfDistrictPicked;
    }

    @Override
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


}







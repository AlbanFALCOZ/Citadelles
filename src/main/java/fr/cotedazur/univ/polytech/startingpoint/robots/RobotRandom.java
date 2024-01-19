package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import java.util.List;
import java.util.ArrayList;

public class RobotRandom implements StrategyBot {
    private RobotNora bot;

    public String tryBuild() {
        List<String> listDistrictName = new ArrayList<>();
        for (DistrictsType districtsType : bot.getCity()) listDistrictName.add(districtsType.getName());
        for (int i = 0; i < bot.getDistrictInHand().size(); i++) {
            DistrictsType district = bot.getDistrictInHand().get(i);
            if (district.getCost() <= bot.getGolds() && !listDistrictName.contains(district.getName())) {
                district.powerOfDistrict(bot);
                bot.getCity().add(district);
                bot.setGolds(bot.getGolds() - district.getCost());
                bot.getDistrictInHand().remove(i);
                return "a new " + district.getName();
            }
        }
        return "nothing";
    }


    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        listDistrict.sort(bot.compareByCost().reversed());
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int indice = 0;
        int i = 0;
        while (i < listDistrict.size()) {
            if (listDistrict.get(i).getCost() - costOfDistrictToBeBuilt <= bot.getGolds()) {
                costOfDistrictToBeBuilt += listDistrict.get(i).getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
                i--;
                indice++;
                if (indice == bot.getNumberOfCardsChosen()) break;

            }
            i++;
        }
        while (listDistrictToBuild.size() < bot.getNumberOfCardsChosen())
            listDistrictToBuild.add(listDistrict.remove(listDistrict.size() - 1));


        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }


    public int generateChoice() {
        return (int) (Math.random() * 2);
    }

    public List<DistrictsType> manufacture(DeckDistrict deck) {
        List<DistrictsType> listOfDistrictPicked = new ArrayList<>();
        if (bot.getGolds() >= 3) {
            bot.setGolds(bot.getGolds() - 3); // dépense 3 or
            for (int i = 0; i < 3; i++) {
                DistrictsType card = deck.getDistrictsInDeck();
                listOfDistrictPicked.add(card);
                bot.addDistrict(card);
            }
        }
        return listOfDistrictPicked;
    }

    public List<DistrictsType> laboratoire(DeckDistrict deck){
        List<DistrictsType> listOfDistrictRemoved = new ArrayList<>();
        if (bot.getNumberOfDistrictInHand() >= 1) {
            int indexOfDistrictInHandToRemove = (int) (Math.random()*bot.getNumberOfDistrictInHand());
            DistrictsType card = bot.districtInHand.remove(indexOfDistrictInHandToRemove);
            listOfDistrictRemoved.add(card);
            deck.addDistrictToDeck(card);
            bot.setGolds(bot.getGolds()+1);
        }
        return listOfDistrictRemoved;
    }


}







package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.Colors;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RobotSarsor extends Robot{


    public RobotSarsor(String name){
        super(name);
        super.setTypeOfRobot("RobotSarsor");
    }

    @Override
    public String tryBuild() {
        List<String> listDistrictName = getCity().stream().map(DistrictsType::getName).collect(Collectors.toList());


        List<DistrictsType> orderedDistricts = getDistrictInHand().stream()
                .sorted((d1, d2) -> compareDistrictsForBuilding(d1, d2))
                .collect(Collectors.toList());

        for (DistrictsType district : orderedDistricts) {
            if (district.getCost() <= getGolds() && !listDistrictName.contains(district.getName())) {
                district.powerOfDistrict(this,1);
                getCity().add(district);
                setGolds(getGolds() - district.getCost());
                getDistrictInHand().remove(district);
                return "a new " + district.getName();
            }
        }
        return "nothing";
    }


    private int compareDistrictsForBuilding(DistrictsType d1, DistrictsType d2) {
        Set<Colors> colorsInCity = getColorsInCity();
        int scoreD1 = calculateDistrictScore(d1, colorsInCity);
        int scoreD2 = calculateDistrictScore(d2, colorsInCity);
        return Integer.compare(scoreD2, scoreD1);
    }


    private int calculateDistrictScore(DistrictsType district, Set<Colors> colorsInCity) {
        int score = 0;
        if (colorsInCity.contains(district.getColor())) {
            score += 2;
        }

        return score;
    }
    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        listDistrict.sort(compareByCost().reversed());
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int indice = 0;
        boolean hasRedCards = listDistrict.stream().anyMatch(district -> district.getColor().equals(Colors.RED));
        for (int i = 0; i < listDistrict.size(); i++) {
            DistrictsType currentDistrict = listDistrict.get(i);
            if (hasRedCards && currentDistrict.getColor().equals(Colors.RED) && currentDistrict.getCost() - costOfDistrictToBeBuilt <= getGolds()) {
                costOfDistrictToBeBuilt += currentDistrict.getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
                i--;
                indice++;
                if (indice == getNumberOfCardsChosen()) break;
            }
        }
        if (listDistrictToBuild.isEmpty()) {
            Set<Colors> colorsInCity = getColorsInCity();

            for (int i = 0; i < listDistrict.size(); i++) {
                DistrictsType currentDistrict = listDistrict.get(i);
                if (!colorsInCity.contains(currentDistrict.getColor()) && currentDistrict.getCost() - costOfDistrictToBeBuilt <= getGolds()) {
                    costOfDistrictToBeBuilt += currentDistrict.getCost();
                    listDistrictToBuild.add(listDistrict.remove(i));
                    i--;
                    indice++;
                    if (indice == getNumberOfCardsChosen()) break;
                }
            }
        }

        while (listDistrictToBuild.size() < getNumberOfCardsChosen()) {
            listDistrictToBuild.add(listDistrict.remove(listDistrict.size() - 1));
        }
        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }


    private Set<Colors> getColorsInCity() {
        return getCity().stream().map(DistrictsType::getColor).collect(Collectors.toSet());
    }


    @Override
    public Robot chooseVictimForCondottiere(List<Robot> bots) {
        Robot victim = bots.get(0);
        int maxDistricts = victim.getNumberOfDistrictInCity();
        for (Robot bot : bots) {
            int currentDistricts = bot.getNumberOfDistrictInCity();
            if (currentDistricts > maxDistricts && bot.getCharacter() != CharactersType.CONDOTTIERE) {
                victim = bot;
                maxDistricts = currentDistricts;
            }
        }
        return victim;
    }





    @Override
    public int generateChoice() {
        if(this.getGolds()<3) {
            return 1 ;
        }
        else {
            return 0 ;
        }
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

    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        if (getHasCrown()) {
            setCharacter(availableCharacters.get(0));
            availableCharacters.remove(0);
        } else {
            CharactersType preferredCharacter = availableCharacters.stream()
                    .filter(character -> isPreferredCharacter(character))
                    .findFirst()
                    .orElse(availableCharacters.get(0));

            setCharacter(preferredCharacter);
            availableCharacters.remove(preferredCharacter);
        }
    }


    private boolean isPreferredCharacter(CharactersType character) {
        if (character.getType().equals(CharactersType.CONDOTTIERE.getType()) && getGolds() > 5) {
            return true;
        } else if (character.getType().equals(CharactersType.ASSASSIN.getType()) && getGolds() > 7) {
            return true;
        }
        return character.getType().equals(CharactersType.VOLEUR.getType());
    }

























}

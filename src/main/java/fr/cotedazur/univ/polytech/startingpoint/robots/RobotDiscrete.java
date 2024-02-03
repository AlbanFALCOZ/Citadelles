package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.*;

public class RobotDiscrete extends Robot{

    private static final String NOBLE = "noble";
    private static final String RELIGIOUS = "religieux";
    private static final String MILITARY = "militaire";
    private static final String COMMERCIAL = "marchand";

    public RobotDiscrete(String name) {
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


    @Override
    public int generateChoice() {
        if (getDistrictInHand().isEmpty()) return 0;
        if (!canBuildADistrictInHand()) return 1;
        return (int) (Math.random() * 2);
    }

    public int countDistrictsByType(String type) {
        return (int) getCity().stream()
                .filter(district -> district.getType().equals(type))
                .count();
    }

    public int countDistrictsInHandByType(String type) {
        return (int) getDistrictInHand().stream()
                .filter(district -> district.getType().equals(type))
                .count();
    }

    @Override
    public void pickCharacter(List<CharactersType> availableCharacters) {
        Map<CharactersType, Integer> characterCounts = new HashMap<>();
        characterCounts.put(CharactersType.ROI, countDistrictsByType(NOBLE) + countDistrictsInHandByType(NOBLE));
        characterCounts.put(CharactersType.EVEQUE, countDistrictsByType(RELIGIOUS) + countDistrictsInHandByType(RELIGIOUS));
        characterCounts.put(CharactersType.CONDOTTIERE, countDistrictsByType(MILITARY) + countDistrictsInHandByType(MILITARY));
        characterCounts.put(CharactersType.MARCHAND, countDistrictsByType(COMMERCIAL) + countDistrictsInHandByType(COMMERCIAL));

        List<CharactersType> priorityOrder = characterCounts.entrySet().stream()
                .sorted(Map.Entry.<CharactersType, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        for (CharactersType character : priorityOrder) {
            if (availableCharacters.contains(character)) {
                setCharacter(character);
                availableCharacters.remove(character);
                break;
            }
        }
    }

    @Override
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
}
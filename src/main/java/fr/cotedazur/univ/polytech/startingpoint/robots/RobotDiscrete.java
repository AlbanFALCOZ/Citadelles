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
        else if (getGolds() == 0) return 1;
        else return (int) (Math.random()*2);
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
        boolean sameTypeFound = false;
        for (DistrictsType currentDistrict : listDistrict) {
            if (!this.getCity().contains(currentDistrict) && !this.getDistrictInHand().contains(currentDistrict)){
                if (currentDistrict.getType().equals(this.getCharacter().getType())) {
                    listDistrictToBuild.add(currentDistrict);
                    listDistrict.remove(currentDistrict);
                    indice++;
                    sameTypeFound = true;
                } else if (!sameTypeFound && currentDistrict.getCost() - costOfDistrictToBeBuilt <= getGolds()) {
                    costOfDistrictToBeBuilt += currentDistrict.getCost();
                    listDistrictToBuild.add(currentDistrict);
                    listDistrict.remove(currentDistrict);
                    indice++;

                }
                else{
                    listDistrictToBuild.add(currentDistrict);
                    listDistrict.remove(currentDistrict);
                    indice++;
                }
            }
            if (indice == getNumberOfCardsChosen()) break;

        }

        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }


}

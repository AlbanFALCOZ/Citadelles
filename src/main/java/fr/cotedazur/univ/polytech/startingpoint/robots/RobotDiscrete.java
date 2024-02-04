package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.*;

import static fr.cotedazur.univ.polytech.startingpoint.game.Main.logger;

public class RobotDiscrete extends Robot {

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
        return 0;
        /*if (getDistrictInHand().isEmpty()) return 0;
        else if (getGolds() == 0) return 1;
        else return (int) (Math.random() * 2);*/
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
        int indice = 0;

        indice = chooseDistrictByType(listDistrict, indice, listDistrictToBuild);

        indice = chooseSpecialDistrict(listDistrict, indice, listDistrictToBuild);

        indice = chooseAnyDistrict(listDistrict, indice, listDistrictToBuild);

        if (indice < getNumberOfCardsChosen()){
            logger.info(this.getName() + " can't choose any district because they are already in his hand or city ");

        }

        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }

    private int chooseAnyDistrict(List<DistrictsType> listDistrict, int indice, List<DistrictsType> listDistrictToBuild) {
        if (indice < getNumberOfCardsChosen()){
            for (DistrictsType currentDistrict : listDistrict) {
                if (!isDistrictInCityOrHand(currentDistrict) ) {
                    indice = chooseDistrict(listDistrict, currentDistrict, listDistrictToBuild, indice);
                    logger.info(this.getName() + " chose " + currentDistrict.getName() + " because it is not in his hand or city.");
                }
                if (indice == getNumberOfCardsChosen()) break;
            }
        }
        return indice;
    }

    private int chooseSpecialDistrict(List<DistrictsType> listDistrict, int indice, List<DistrictsType> listDistrictToBuild) {
        if (indice < getNumberOfCardsChosen()){
            for (DistrictsType currentDistrict : listDistrict) {
                if (!isDistrictInCityOrHand(currentDistrict) && isSpecialDistrictType(currentDistrict.getType())) {
                    indice = chooseDistrict(listDistrict, currentDistrict, listDistrictToBuild, indice);
                    logger.info(this.getName() + " chose " + currentDistrict.getName() + " because it is a special district and the most expensive and is not in his hand or city.");
                }
                if (indice == getNumberOfCardsChosen()) break;
            }
        }
        return indice;
    }

    private int chooseDistrictByType(List<DistrictsType> listDistrict, int indice, List<DistrictsType> listDistrictToBuild) {
        for (DistrictsType currentDistrict : listDistrict) {
            if (!isDistrictInCityOrHand(currentDistrict) && (currentDistrict.getType().equals(this.getCharacter().getType()))) {
                indice = chooseDistrict(listDistrict, currentDistrict, listDistrictToBuild, indice);
                logger.info(this.getName() + " chose " + currentDistrict.getName() + " because it is the same type as his character.");

            }
            if (indice == getNumberOfCardsChosen()) break;
        }
        return indice;
    }

    private boolean isDistrictInCityOrHand(DistrictsType district) {
        return this.getCity().contains(district) || this.getDistrictInHand().contains(district);
    }

    private boolean isSpecialDistrictType(String type) {
        return type.equals(NOBLE) || type.equals(RELIGIOUS) || type.equals(COMMERCIAL) || type.equals(MILITARY);
    }

    private int chooseDistrict(List<DistrictsType> listDistrict, DistrictsType currentDistrict, List<DistrictsType> listDistrictToBuild, int indice) {
        listDistrictToBuild.add(currentDistrict);
        listDistrict.remove(currentDistrict);
        indice++;
        return indice;
    }




}

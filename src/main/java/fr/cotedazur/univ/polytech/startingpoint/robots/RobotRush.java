package fr.cotedazur.univ.polytech.startingpoint.robots;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RobotRush extends Robot {

    public RobotRush(String name) {
        super(name);
    }


    @Override
    public void pickCharacter (List<CharactersType> availableCharacters, List<Robot> bots) {
        //prioriser l'architecte pour construire rapidement
        if (availableCharacters.contains(CharactersType.ARCHITECTE)) {
            setCharacter(CharactersType.ARCHITECTE);
        }
        //si le nb de districts est proche de 8: prioriser le roi ou l'évêque pour la protection
        else if (getNumberOfDistrictInCity() >= 6) {
            if (availableCharacters.contains(CharactersType.ROI)) {
                setCharacter(CharactersType.ROI);
            } else if (availableCharacters.contains(CharactersType.EVEQUE)) {
                setCharacter(CharactersType.EVEQUE);
            }
        }
        //si les ressources sont suffisantes, choisir le marchand pour augmenter l'or
        else if (getGolds() >= 3 && availableCharacters.contains(CharactersType.MARCHAND)) {
            setCharacter(CharactersType.MARCHAND);
        }
        else {
            CharactersType chosenCharacter = availableCharacters.get(0);
            for (CharactersType character : availableCharacters) {
                if (getPriority(character) > getPriority(chosenCharacter)) {
                    chosenCharacter = character;
                }
            }
            setCharacter(chosenCharacter);
        }
    }

    private int getPriority(CharactersType character) {
        switch(character) {
            case ARCHITECTE: return 5;
            case ROI: return 4;
            case EVEQUE: return 3;
            case MARCHAND: return 2;
            case MAGICIEN: return 1;
            default: return 0;
        }
    }

    @Override
    public String tryBuild() {
        // construire districts les - chers pour accélérer le processus de construction
        getDistrictInHand().sort(Comparator.comparingInt(DistrictsType::getCost));
        String built = "nothing";
        for (DistrictsType district : getDistrictInHand()) {
            if (district.getCost() <= getGolds()) {
                district.powerOfDistrict(this,1);
                getCity().add(district);
                setGolds(getGolds() - district.getCost());
                getDistrictInHand().remove(district);
                built = "a new " + district.getName();
                break; //construire un seul district par tour
            }
        }
        return built;
    }

    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        //choisit la carte la - chère ou celle qui lui donne le plus d'avantages
        listDistrict.sort(Comparator.comparingInt(DistrictsType::getCost));
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int i = 0;
        while (i < listDistrict.size() && listDistrictToBuild.size() < getNumberOfCardsChosen()) {
            if (listDistrict.get(i).getCost() - costOfDistrictToBeBuilt <= getGolds()) {
                costOfDistrictToBeBuilt += listDistrict.get(i).getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
            } else {
                i++;
            }
        }

        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }


    @Override
    public int generateChoice() {
        if (!getDistrictInHand().isEmpty() && canBuildADistrictInHand()) {
            return 0;  //construire
        }
        return 1;  //prendr ressources si construction pas possible
    }

    @Override
    public List<DistrictsType> laboratoire(DeckDistrict deck) {
        return Collections.emptyList();
    }

    @Override
    public List<DistrictsType> manufacture(DeckDistrict deck) {
        return Collections.emptyList();
    }

}

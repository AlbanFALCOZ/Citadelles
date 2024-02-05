package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.ArrayList;
import java.util.List;

public class RobotSarsor extends Robot{

    private boolean aggressive;

    public RobotSarsor(String name, boolean aggressive) {
        super(name);
        this.aggressive = aggressive;
    }

    @Override
    public String tryBuild() {
        List<String> listDistrictName = new ArrayList<>();
        for (DistrictsType districtsType : getCity()) listDistrictName.add(districtsType.getName());

        // Reorder districts in hand to prioritize red districts
        List<DistrictsType> orderedDistricts = new ArrayList<>();
        for (DistrictsType district : getDistrictInHand()) {
            if (district.getColor().equals("red")) {
                orderedDistricts.add(0, district);
            } else {
                orderedDistricts.add(district);
            }
        }

        for (int i = 0; i < orderedDistricts.size(); i++) {
            DistrictsType district = orderedDistricts.get(i);
            if (district.getCost() <= getGolds() && !listDistrictName.contains(district.getName())) {
                district.powerOfDistrict(this);
                getCity().add(district);
                setGolds(getGolds() - district.getCost());
                getDistrictInHand().remove(district);
                return "a new " + district.getName();
            }
        }
        return "nothing";
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

    @Override
    public int generateChoice() {
        if(this.getGolds()<4) {
            return 1 ;
        }
        else {
            return 0 ;
        }
    }

    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        if (getHasCrown()) {
            setCharacter(availableCharacters.get(0));
            availableCharacters.remove(0);
        } else {
            if (aggressive) {
                CharactersType aggressiveCharacter = availableCharacters.stream()
                        .filter(character -> character.getType().equals(CharactersType.ASSASSIN.getType()) || character.getType().equals(CharactersType.VOLEUR.getType()) || character.getType().equals(CharactersType.CONDOTTIERE.getType()) )
                        .findFirst()
                        .orElse(availableCharacters.get(0));

                setCharacter(aggressiveCharacter);
                availableCharacters.remove(aggressiveCharacter);
            } else {
                setCharacter(availableCharacters.get(0));
                availableCharacters.remove(0);
            }
        }
    }


}

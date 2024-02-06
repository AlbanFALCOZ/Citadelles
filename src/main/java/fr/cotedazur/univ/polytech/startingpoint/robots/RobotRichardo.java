package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;


import java.util.*;

public class RobotRichardo extends Robot {
    private static final String NOBLE = "noble";
    private static final String RELIGIOUS = "religieux";
    private static final String MILITARY = "militaire";
    private static final String COMMERCIAL = "marchand";

    private int cond = 0;
    private int assas = 0;
    private int march = 0;
    private int arch = 0;

    private boolean agressif = false;
    private boolean batisseur = false;
    private boolean opportuniste = false;

    private DeckCharacters listOfCharacters = new DeckCharacters();
    private List<CharactersType> availableCharacters;

    public RobotRichardo(String name) {
        super(name);

    }

    @Override
    public String tryBuild() {
        if (batisseur) return tryBuildBatisseur();
        return buildDistrictAndRetrieveItsName();
    }

    //Dans le cas où Richardo est un batisseur, il essaie de construire les quartiers noble/marchands en priorité.
    public String tryBuildBatisseur() {
        for (int i = 0; i < getDistrictInHand().size(); i++) {
            DistrictsType district = getDistrictInHand().get(i);
            if (district.getCost() <= getGolds() && !city.contains(district) && character.getType().equals(district.getType())) {
                district.powerOfDistrict(this,1);
                getCity().add(district);
                setGolds(getGolds() - district.getCost());
                getDistrictInHand().remove(i);
                return "a new " + district.getName();
            }
        }
        return buildDistrictAndRetrieveItsName();
    }

    //On construit le premier district possible
    private String buildDistrictAndRetrieveItsName() {
        for (int i = 0; i < getDistrictInHand().size(); i++) {
            DistrictsType district = getDistrictInHand().get(i);
            if (district.getCost() <= getGolds() && !city.contains(district)) {
                district.powerOfDistrict(this,1);
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
        if(this.getGolds()<5) {
            return 1 ;
        }
        else {
            return 0 ;
        }
    }


    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        if (batisseur) {
            pickBatisseur(availableCharacters);
            /*
        } else if (opportuniste) {
            pickOpportuniste();

             */
        } else if (agressif) {
            pickAgressif(availableCharacters, bots);
        } else {
            setCharacter(availableCharacters.get(0));
            availableCharacters.remove(0);
        }
    }

    public void isAgressif(List<Robot> bots) {
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() > this.getNumberOfDistrictInCity() + 2 || bot.getNumberOfDistrictInCity() > 5) {
                cond++;
                agressif = true;
            } else if (thereIsA(CharactersType.VOLEUR, availableCharacters)) {
                assas++;
                agressif = true;
            }
        }
        agressif = false;
    }

    public boolean thereIsA(CharactersType character, List<CharactersType> availableCharacters) {
        if (this.hasCrown) {
            listOfCharacters.getCharactersInHand().removeAll(availableCharacters);
            return listOfCharacters.getCharactersInHand().contains(character);
        }
        return false;
    }

    public void isBatisseur() {
        if (this.getGolds() < 2) {
            march++;
            batisseur = true;
        } else if (this.getGolds() > 6 && this.getNumberOfDistrictInHand() > 3) {
            arch++;
        }
    }

    public void pickOpportuniste() {
        Map<CharactersType, Integer> characterCounts = new HashMap<>();
        characterCounts.put(CharactersType.ROI, countDistrictsByType(NOBLE) + countDistrictsInHandByType(NOBLE));
        characterCounts.put(CharactersType.EVEQUE, countDistrictsByType(RELIGIOUS) + countDistrictsInHandByType(RELIGIOUS));
        characterCounts.put(CharactersType.CONDOTTIERE, countDistrictsByType(MILITARY) + countDistrictsInHandByType(MILITARY));
        characterCounts.put(CharactersType.MARCHAND, countDistrictsByType(COMMERCIAL) + countDistrictsInHandByType(COMMERCIAL));

        List<CharactersType> priorityOrder = characterCounts.entrySet().stream()
                .sorted(Map.Entry.<CharactersType, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        CharactersType chosenCharacter = null;

        for (CharactersType character : priorityOrder) {
            if (availableCharacters.contains(character)) {

                chosenCharacter = character;
                availableCharacters.remove(character);
                break;
            }
        }

        if (chosenCharacter == null && !availableCharacters.isEmpty()) {

            chosenCharacter = availableCharacters.get(0);
            availableCharacters.remove(0);
        }

        setCharacter(chosenCharacter);
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
    public void pickAgressif(List<CharactersType> availableCharacters, List<Robot> bots) {
        isAgressif(bots);
        if (cond > assas) {
            pickCharacterCard(availableCharacters, CharactersType.CONDOTTIERE);
        } else {
            pickCharacterCard(availableCharacters, CharactersType.ASSASSIN);
        }
    }

    public void pickBatisseur(List<CharactersType> availableCharacters) {
        isBatisseur();
        pickCharacterCard(availableCharacters,CharactersType.ROI);
        if (character == CharactersType.ROI) return;
        else if (march > arch) {
            pickCharacterCard(availableCharacters, CharactersType.MARCHAND);
            if (character == CharactersType.MARCHAND) return;
        } else {
            pickCharacterCard(availableCharacters, CharactersType.ARCHITECTE);
            if (character == CharactersType.ARCHITECTE) return;
        }
        character = availableCharacters.get(0);
        availableCharacters.remove(0);

    }

    public void pickCharacterCard(List<CharactersType> availableCharacters, CharactersType character) {
        if (availableCharacters.contains(character)) {
            this.setCharacter(character);
            availableCharacters.remove(character);
        }
    }

    @Override
    public Robot chooseVictimForCondottiere(List<Robot> bots){
        Robot victim = bots.get(0);
        int numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() >= numberOfDistrictsInCity && bot.getCharacter()!= CharactersType.CONDOTTIERE && !victim.hasEightDistrict()) {
                victim = bot;
                numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
            }
        }
        return victim;

    }

    @Override
    public Robot chooseVictimForMagicien(List<Robot> bots){
        Robot victim = bots.get(0);
        int numberOfDistrictsInHand = victim.getNumberOfDistrictInHand();
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInHand() >= numberOfDistrictsInHand && bot.getCharacter()!= CharactersType.MAGICIEN) victim = bot;
        }
        return victim;
    }

    @Override
    public Robot chooseVictimForAssassin(List<Robot> bots, int numberOfTheCharacterToKill) {
        Robot victim = null;
        int maxGold = Integer.MIN_VALUE;
        for (Robot bot : bots) {
            if (bot.getCharacter().getNumber() == numberOfTheCharacterToKill && bot.getGolds() > maxGold) {
                victim = bot;
                maxGold = bot.getGolds();
            }
        }
        return victim;
    }

    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        if (batisseur && (character == CharactersType.ROI || character == CharactersType.MARCHAND) ) return pickDistrictCardBatisseur( listDistrict,deck);
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
        while (listDistrictToBuild.size() < getNumberOfCardsChosen()) {
            listDistrictToBuild.add(listDistrict.remove(listDistrict.size() - 1));
        }
        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }

    public List<DistrictsType> pickDistrictCardBatisseur(List<DistrictsType> listDistrict, DeckDistrict deck) {
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        for(DistrictsType district : listDistrict) {
            if (character.getRole().equals(district.getType())) listDistrictToBuild.add(district);
            if (listDistrictToBuild.size() == numberOfCardsChosen) return listDistrictToBuild;
        }
        while (listDistrictToBuild.size() < numberOfCardsChosen) listDistrictToBuild.add(listDistrict.remove(0));
        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }

}













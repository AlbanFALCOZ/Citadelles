package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotDiscrete;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotRichardo extends RobotDiscrete {
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
        return null;
    }

    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        return null;
    }

    @Override
    public int generateChoice() {
        return 0;
    }

    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        if (batisseur) {
            pickBatisseur(availableCharacters);
        } else if (opportuniste) {
            pickOpportuniste();
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
                .sorted((Comparator<? super Map.Entry<CharactersType, Integer>>) Map.Entry.comparingByValue().reversed())
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
        if (march > arch) {
            pickCharacterCard(availableCharacters, CharactersType.MARCHAND);
        } else {
            pickCharacterCard(availableCharacters, CharactersType.ARCHITECTE);
        }
    }

    public void pickCharacterCard(List<CharactersType> availableCharacters, CharactersType character) {
        if (availableCharacters.contains(character)) {
            this.setCharacter(character);
            availableCharacters.remove(character);
        }
    }
}













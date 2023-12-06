package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.complements.Strategies;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.List;

public class Robot {

    private int number;
    private int golds;
    private Strategies strategies;
    private DistrictsType district;
    private DeckCharacters deckCharacters;

    public Robot(int number, DistrictsType district, List<DeckCharacters> deckCharacters, Strategies strategy, int golds) {
        this.number = number;
        this.district = district;
        this.golds = golds;
        this.deckCharacters = getRandomCharacter(deckCharacters);
    }

    public Robot(int number, DistrictsType district, List<DeckCharacters> deckCharacters, Strategies strategy) {
        this(number, district, deckCharacters, strategy, 2);
    }

    public int getNumber() {
        return number;
    }

    public int getGolds() {
        return golds;
    }

    public Strategies getStrategies() {
        return strategies;
    }

    public DistrictsType getDistrict() {
        return district;
    }

    public DeckCharacters getListCharacters() {
        return deckCharacters;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public void setDistrict(DistrictsType district) {
        this.district = district;
    }


    private DeckCharacters getRandomCharacter(List<DeckCharacters> deckCharacters) {
        if (deckCharacters != null && !deckCharacters.isEmpty()) {
            int randomIndex = (int) (Math.random() * deckCharacters.size());
            return deckCharacters.get(randomIndex);
        }
        return null;
    }
}




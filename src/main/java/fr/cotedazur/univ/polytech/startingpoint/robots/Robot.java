package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.complements.Strategies;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.List;

public class Robot {

    private int number;
    private int golds;
    private Strategies strategies;
    private DeckDistrict district;
    private CharactersType character;
    private DeckCharacters deckCharacters;

    public Robot(int number) {
        this.number = number;
        this.district = null;
        this.golds = 2;
        this.character = null;
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

    public DeckDistrict getDistrict() {
        return district;
    }

    public DeckCharacters getListCharacters() {
        return deckCharacters;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public void setDistrict(DeckDistrict district) {
        this.district = district;
    }



    public void setCharacter(CharactersType character) {
        this.character = character;
    }

}




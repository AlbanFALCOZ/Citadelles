package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.ListCharacters;
import fr.cotedazur.univ.polytech.startingpoint.complements.Strategies;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.List;

public class Robots {

    private int number;
    private int golds;
    private Strategies strategies;

    private List<DistrictsType> district;
    private CharactersType character;
    private ListCharacters listCharacters;

    public Robots(int number, List<DistrictsType> district, CharactersType character, Strategies strategy, int golds) {
        this.number = number;
        this.district = district;
        this.golds = golds;
        this.character = character;
    }

    public Robots(int number, List<DistrictsType> district, CharactersType character, Strategies strategy) {
        this(number, district, character, strategy, 2);
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


    public void setGolds(int golds) {
        this.golds = golds;
    }

    public void setDistrict(List<DistrictsType> district) {
        this.district = district;
    }


    public void setDistricts(List<DistrictsType> districtsForBot) {
        this.district = districtsForBot;
    }


    public void setCharacter(CharactersType character) {
        this.character = character;
    }

}




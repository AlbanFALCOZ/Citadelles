package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.ListCharacters;
import fr.cotedazur.univ.polytech.startingpoint.complements.Strategies;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.List;

public class Robot {

    private int number;
    private int golds;
    private Strategies strategies;
    private DistrictsType district;
    private ListCharacters listCharacters;

    public Robot(int number, DistrictsType district, List<ListCharacters> listCharacters, Strategies strategy, int golds) {
        this.number = number;
        this.district = district;
        this.golds = golds;
        this.listCharacters = getRandomCharacter(listCharacters);
    }

    public Robot(int number, DistrictsType district, List<ListCharacters> listCharacters, Strategies strategy) {
        this(number, district, listCharacters, strategy, 2);
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

    public ListCharacters getListCharacters() {
        return listCharacters;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public void setDistrict(DistrictsType district) {
        this.district = district;
    }


    private ListCharacters getRandomCharacter(List<ListCharacters> listCharacters) {
        if (listCharacters != null && !listCharacters.isEmpty()) {
            int randomIndex = (int) (Math.random() * listCharacters.size());
            return listCharacters.get(randomIndex);
        }
        return null;
    }
}




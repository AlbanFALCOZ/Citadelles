package fr.cotedazur.univ.polytech.startingpoint.GameEngine;

import fr.cotedazur.univ.polytech.startingpoint.characters.ListCharacters;
import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.ListDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robots;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private List<Robots> bots = new ArrayList<>();
    private ListDistrict listDistrict;
    private ListCharacters listCharacters;

    public GameEngine() {
        listDistrict = new ListDistrict();
        listCharacters = new ListCharacters();
        initializeBots();
    }
    

    private void initializeBots() {
        for (int i = 0; i < 4; i++) {
            bots.add(new Robots(i, null, null, null));
        }
    }


}

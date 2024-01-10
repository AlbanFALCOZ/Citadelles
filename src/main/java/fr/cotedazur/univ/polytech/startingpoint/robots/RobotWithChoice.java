package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.complements.Strategies;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.*;

public class RobotWithChoice extends RobotRandom implements Robot{

    public RobotWithChoice(String name) {
        super(name);
    }

    @Override
    public int getChoice() {
        if (getDistrictInHand().isEmpty()) return 0;
        if (!canBuildADistrictInHand()) return 1;
        return (int) (Math.random()*2);
    }



}


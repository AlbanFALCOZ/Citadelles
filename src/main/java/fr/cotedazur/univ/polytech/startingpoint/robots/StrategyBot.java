package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.List;

public interface StrategyBot {

    String tryBuild();
    List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck);

    int generateChoice();

    List<DistrictsType> laboratoire(DeckDistrict deck);

    List<DistrictsType> manufacture(DeckDistrict deck);
}

package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.List;

public interface Robot {

    int getScore();

    String getRESET();

    String getName();

    int getGolds();

    void setGolds(int golds);

    int getNumberOfCardsDrawn();

    void setNumberOfCardsDrawn(int numberOfCardsDrawn);

    int getNumberOfCardsChosen();

    void setNumberOfCardsChosen(int numberOfCardsChosen);

    void setScore(int score);

    void setDistrict(DeckDistrict district);

    void addGold(int golds);

    void setCharacter(CharactersType character);

    CharactersType getCharacter();

    List<DistrictsType> getCity();

    void setHasCrown(boolean hasCrown);

    String tryBuild();

    void addDistrict(DistrictsType district);

    void addDistrict(List<DistrictsType> listDistrict);

    int getNumberOfDistrictInHand();

    int getNumberOfDistrictInCity();

    String statusOfPlayer(boolean showColor);

    String statusOfPlayer();

    List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict);

    List<DistrictsType> pickListOfDistrict();

    int calculateScore();

    boolean getHasCrown();

    int countBuildingsByType();

    int winGoldsByTypeOfBuildings();

    boolean isCharacter(String type);



    void setPower(Power aPower);

    int getChoice();

    boolean canBuildADistrictInHand();

    Power getPower();


}

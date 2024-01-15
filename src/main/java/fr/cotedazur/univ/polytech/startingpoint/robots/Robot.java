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

    List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck);

    List<DistrictsType> pickListOfDistrict(DeckDistrict deck);

    int calculateScore();

    boolean getHasCrown();

    int countBuildingsByType();

    int winGoldsByTypeOfBuildings();

    boolean isCharacter(String type);


    //void setPower(Power aPower);

    int getChoice();

    boolean canBuildADistrictInHand();

    int generateChoice();

    void setChoice(int choice);

    List<DistrictsType> getDistrictInHand();

    void setDistrictInHand(List<DistrictsType> districtInHand);

    void emptyListOfCardsInHand();


    void setHasFriveColors(boolean b);

    boolean hasFiveColors();


    boolean getIsAssassinated();

    void setIsAssassinated(boolean IsAssassinated);
}




    //Power getPower();




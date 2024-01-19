package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.List;

public interface Robot {

    int getScore();

    void setScore(int score);

    String getRESET();

    String getName();

    int getGolds();

    void setGolds(int golds);

    int getNumberOfCardsDrawn();

    void setNumberOfCardsDrawn(int numberOfCardsDrawn);

    int getNumberOfCardsChosen();

    void setNumberOfCardsChosen(int numberOfCardsChosen);

    void addGold(int golds);

    CharactersType getCharacter();

    void setCharacter(CharactersType character);

    List<DistrictsType> getCity();

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

    void setHasCrown(boolean hasCrown);

    int countBuildingsByType();

    int winGoldsByTypeOfBuildings();

    boolean isCharacter(String type);


    //void setPower(Power aPower);

    int getChoice();

    void setChoice(int choice);

    boolean canBuildADistrictInHand();

    int generateChoice();

    List<DistrictsType> getDistrictInHand();

    void setDistrictInHand(List<DistrictsType> districtInHand);

    void emptyListOfCardsInHand();


    boolean getIsAssassinated();

    void setIsAssassinated(boolean IsAssassinated);

    boolean hasEightDistrict();


    //Power getPower();
    List<DistrictsType> manufacture(DeckDistrict deck);

    List<DistrictsType> laboratoire(DeckDistrict deck);

    void specialCards(DeckDistrict deck, ActionOfBotDuringARound action);

}

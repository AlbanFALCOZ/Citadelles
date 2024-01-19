package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotNora;

import java.util.List;

public class ActionOfBotDuringARound {


    private RobotNora bot;
    private List<DistrictsType> listDistrictDrawn;
    private List<DistrictsType> listDistrictPicked;

    public ActionOfBotDuringARound(RobotNora bot) {
        this.bot = bot;
    }

    public void startTurnOfBot() {
        System.out.println("------------------------------------------------------------The turn of " + bot.getName() + " is starting -----------------------------------------------------\n");
        System.out.println(bot.statusOfPlayer());
    }

    public void addListOfDistrict(List<DistrictsType> listDistrictDrawn, List<DistrictsType> listDistrictPicked) {
        this.listDistrictDrawn = listDistrictDrawn;
        listDistrictDrawn.addAll(listDistrictPicked);
        this.listDistrictPicked = listDistrictPicked;
    }

    public void printActionOfBotWhoHasBuilt() {

        String cardDrawn = "";
        String cardPicked = "";
        for (int i = 0; i < listDistrictDrawn.size(); i++) {
            DistrictsType districtInListDistrict = listDistrictDrawn.get(i);
            cardDrawn += districtInListDistrict.getColor().getColorDisplay() + districtInListDistrict + districtInListDistrict.getColorReset();
            if (i < listDistrictDrawn.size()-1) cardDrawn += ",";
        }
        System.out.println(bot.getName() + " drew the following cards : {" + cardDrawn + "}");
        for (int i = 0; i < listDistrictPicked.size(); i++) {
            DistrictsType districtInListDistrict = listDistrictPicked.get(i);
            cardPicked += districtInListDistrict.getColor().getColorDisplay() + districtInListDistrict + districtInListDistrict.getColorReset();
            if (i < listDistrictPicked.size()-1) cardPicked += ",";
            //bot.addDistrict(districtInListDistrict);
        }

        System.out.println(bot.getName() + " choose to pick : {" + cardPicked + "}");
        System.out.println(bot.getName() + " has now in hand: " + bot.getNumberOfDistrictInHand() + " districts");
    }

    private String getStringOfListOfDistrict(List<DistrictsType> listOfDistrict) {
        String stringOfDistricts = "";
        for (int i = 0; i < listOfDistrict.size(); i++) {
            DistrictsType districtInListDistrict = listOfDistrict.get(i);
            stringOfDistricts += districtInListDistrict.getColor().getColorDisplay() + districtInListDistrict + districtInListDistrict.getColorReset();
            if (i < listOfDistrict.size() - 1) stringOfDistricts += ",";
            //bot.addDistrict(districtInListDistrict);
        }
        return stringOfDistricts;
    }

    public void printActionOfBotWhoGainedGold(int goldGained) {
        System.out.println(bot.getName() + " earned " + goldGained + " golds. Total golds now: " + bot.getGolds());
    }

    public void printActionOfSellerBotWhoGainedGold() {
        System.out.println("By being a merchant, " + bot.getName() + " earned 1 gold. Total golds now: " + bot.getGolds());
    }

    public void printBuildingAndPowerOfBot(String hasBuilt, int goldsWon) {
        if (!hasBuilt.equals("nothing"))
            System.out.println(bot.getName() + " built " + hasBuilt + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
        if (goldsWon > 0)
            System.out.println(bot.getName() + " has won " + goldsWon + " golds by " + bot.getCharacter().getType() + " buildings and has now " + bot.getGolds() + " golds");
        System.out.println(bot.statusOfPlayer());
        System.out.println("\n-------------------------------------------------------The turn of " + bot.getName() + " is over ------------------------------------------------------------------");
    }


    public void printBuildingOfBot(String hasBuilt) {
        if (!hasBuilt.equals("nothing")) {
            System.out.println(bot.getName() + " built " + hasBuilt + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
        }
    }


    public void printActionOfDestroyDistrict(RobotNora victim, DistrictsType district, int goldsRestarts) {
        System.out.println(bot.getName() + " destroyed " + district + " of " + victim.getName() + " and now has " + goldsRestarts + " golds");
    }

    public void printActionOfNoneDistrictDestroyed(RobotNora victim, int destructorGolds) {
        System.out.println(bot.getName() + " can't destroy the districts" + " of " + victim.getName() + " because he has only " + destructorGolds + " golds or the district is a Donjon");
    }

    public void printEvequeImmune(RobotNora victim, DistrictsType district) {
        System.out.println(bot.getName() + " can't destroy " + district + " of " + victim.getName() + " because he is " + victim.getCharacter().getRole());
    }

    public void printVictimAssassined(RobotNora victim) {
        System.out.println(bot.getName() + " murdered " + victim.getCharacter().getRole());

    }

    public void printMagicianSwap(RobotNora victim) {
        System.out.println(bot.getName() + " swapped cards with " + victim.getName());
    }

    public void printChoiceOfThief(RobotNora bot, int numberOfCharacter) {
        System.out.println(bot.getName() + " chose to steal from " + getNameOfCharacterFromNumber(numberOfCharacter));
    }

    public void printThiefStill(RobotNora victim) {
        System.out.println(bot.getName() + " stole " + victim.getGolds() + " golds from " + victim.getName() + ". Total golds now " + bot.getGolds());
    }

    public void printMagicianSwapWithDeck() {
        System.out.println(bot.getName() + " choosed to swap with deck");
    }

    public String getNameOfCharacterFromNumber(int number) {
        String[] listName = {"Assassin", "Voleur", "Magicien", "Roi", "Évêque", "Marchand", "Architecte", "Condottière"};
        return listName[number - 1];
    }

    public void printCantAffectVictim(RobotNora victim) {
        System.out.println(bot.getName() + " can't steal " + victim.getName() + " because he has been assassinated");
    }

    public void printDistrictRecovered(RobotNora victim, DistrictsType district) {
        System.out.println(victim.getName() + " got district " + district.getName() + " back into his hand by paying 1 gold thanks to district Cimetière.");
    }

    public void printLaboratoryAction(List<DistrictsType> listOfDistrictRemoved) {
        System.out.println("Thanks to the laboratory, " + bot.getName() + " has removed " + getStringOfListOfDistrict(listOfDistrictRemoved) + " and has gained one gold");
    }

    public void printManufactureAction(List<DistrictsType> listOfDistrictPicked) {
        System.out.println("Thanks to the manufacture, " + bot.getName() + " lost 3 golds but added {" + getStringOfListOfDistrict(listOfDistrictPicked) + "} to his hand");
    }
}





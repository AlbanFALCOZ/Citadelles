package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.List;

public class ActionOfBotDuringARound {


    private Robot bot;
    private List<DistrictsType> listDistrictDrawn;
    private List<DistrictsType> listDistrictPicked;

    public ActionOfBotDuringARound(Robot bot) {
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
            cardDrawn += districtInListDistrict.getColor() + districtInListDistrict + districtInListDistrict.getColorReset();
            if (i < listDistrictDrawn.size()-1) cardDrawn += ",";
        }
        System.out.println("The bot drew the following cards : {" + cardDrawn + "}");
        for (int i = 0; i < listDistrictPicked.size(); i++) {
            DistrictsType districtInListDistrict = listDistrictPicked.get(i);
            cardPicked += districtInListDistrict.getColor() + districtInListDistrict + districtInListDistrict.getColorReset();
            if (i < listDistrictPicked.size()-1) cardPicked += ",";
            bot.addDistrict(districtInListDistrict);
        }
        System.out.println("The bot choose to pick : {" + cardPicked + "}");
        System.out.println(bot.getName() + " has now in hand: " + bot.getNumberOfDistrictInHand() + " districts");
    }

    public void printActionOfBotWhoGainedGold(int goldGained) {
        System.out.println(bot.getName() + " earned " + goldGained +" golds. Total golds now: " + bot.getGolds());
    }

    public void printActionOfSellerBotWhoGainedGold() {
        System.out.println("Yolo");
        System.out.println("By being a seller, " + bot.getName() + " earned 1 gold. Total golds now: " + bot.getGolds());
    }

    public void printBuildingAndPowerOfBot(String hasBuilt, int goldsWon) {
        if (!hasBuilt.equals("nothing")) System.out.println(bot.getName() + " built " + bot.tryBuild() + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
        if (goldsWon > 0) System.out.println(bot.getName() + " has won " + bot.winGoldsByTypeOfBuildings() + " golds by " + bot.getCharacter().getType() + " buildings and has now " + bot.getGolds() + " golds");
        System.out.println(bot.statusOfPlayer());
        System.out.println("\n-------------------------------------------------------The turn of " + bot.getName() + " is over ------------------------------------------------------------------");
    }


}

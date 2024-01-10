package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.List;

public class ActionOfBotDuringARound {


    private int choiceOfBot;
    private Robot bot;
    private List<DistrictsType> listDistrictDrawn;
    private List<DistrictsType> listDistrictPicked;

    public ActionOfBotDuringARound(Robot bot, int choiceOfBot, List<DistrictsType> listDistrictDrawn, List<DistrictsType> listDistrictPicked) {
        this.bot = bot;
        this.choiceOfBot = choiceOfBot;
        this.listDistrictDrawn = listDistrictDrawn;
        this.listDistrictPicked = listDistrictPicked;
    }

    public ActionOfBotDuringARound(Robot bot) {
        this.bot = bot;
    }

    public void showActionOfBotWhoHasBuilt() {
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
}

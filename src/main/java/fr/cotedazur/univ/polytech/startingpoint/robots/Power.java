package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.List;

public class Power {

    private String name ;
    private ActionOfBotDuringARound action;
    public Power(String name, ActionOfBotDuringARound action){
        this.name = name ;
        this.action = action;
    }



    public void choosePowerOfBot(Robot bot) {
        if (bot.getCharacter().getType().equals("marchand")) {
            marchand(bot);
        }
        //else if (bot.getCharacter().isCharacter("architecte")) {
            //architecte(bot);
        //}
    }
   
    public void marchand(Robot bot){
        //Nouvelle action
        //ActionOfBotDuringARound action = new ActionOfBotDuringARound(bot);
        //Since the player is a seller , he earns 1 gold
        bot.addGold(1);
        //Then the classic scenario occurs
        action.printActionOfSellerBotWhoGainedGold();
        /*
        bot.addGold(2);
        action.printActionOfBotWhoGainedGold(2);
        List<DistrictsType> listDistrictDrawn = bot.pickListOfDistrict();
        List<DistrictsType> listDistrictPicked = bot.pickDistrictCard(listDistrictDrawn);
        action.addListOfDistrict(listDistrictDrawn,listDistrictPicked);
        bot.addDistrict(listDistrictPicked);
        bot.tryBuild();
        action.printActionOfBotWhoHasBuilt();
         */
    }



    public void architecte(Robot bot) {
        //ActionOfBotDuringARound action = new ActionOfBotDuringARound(bot);
        int i = bot.getChoice();
        if(i == 0 ) {
            bot.addGold(2);
            action.printActionOfBotWhoGainedGold(2);
            List<DistrictsType> listDistrictDrawn = bot.pickListOfDistrict();
            List<DistrictsType> listDistrictPicked = bot.pickDistrictCard(listDistrictDrawn);
            action.addListOfDistrict(listDistrictDrawn, listDistrictPicked);
            bot.addDistrict(listDistrictPicked);

            for (int j = 0; j < 3; j++) {
                bot.tryBuild();
                action.printActionOfBotWhoHasBuilt();
            }
        } else {
            System.out.println("Salut");



        }



    }
}

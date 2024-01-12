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
        else if (bot.getCharacter().getType().equals("architecte")) {
            architecte(bot);
        }
    }
   
    public void marchand(Robot bot){
        bot.addGold(1);
        action.printActionOfSellerBotWhoGainedGold();

    }



    public void architecte(Robot bot) {
        int i = bot.generateChoice();
        System.out.println(i);
        if(i == 0 ) {
            bot.setChoice(7);
            System.out.println(bot.getChoice());
            System.out.println(bot.getChoice());
            List<DistrictsType> listDistrictDrawn = bot.pickListOfDistrict();
            listDistrictDrawn.add(bot.pickListOfDistrict().get(0)) ;
            listDistrictDrawn.add(bot.pickListOfDistrict().get(1)) ;
            List<DistrictsType> listDistrictPicked = bot.pickDistrictCard(listDistrictDrawn);
            action.addListOfDistrict(listDistrictDrawn,listDistrictPicked);
            bot.addDistrict(listDistrictPicked);
            action.printActionOfBotWhoHasBuilt();
            }
        else {
            bot.addGold(2);
            action= new ActionOfBotDuringARound(bot);
            action.printActionOfBotWhoGainedGold(2);
            bot.setChoice(0);
            System.out.println(bot.getChoice());
        }
        String hasBuilt = bot.tryBuild();
        action.printBuildingOfBot(hasBuilt);
        String hasBuilt2 = bot.tryBuild();
        action.printBuildingOfBot(hasBuilt2);
        }
    }





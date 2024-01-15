package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.*;

public class Power {
    private ActionOfBotDuringARound action;
    private Robot bot;

    public static final String MILITATE = "militaire";
    public static final String RELIGIOUS = "religieux";
    public static final String ASSASSIN = "assassin";

    public Power(Robot bot, ActionOfBotDuringARound action){
        this.bot = bot;
        this.action = action;

    }

   
    public void marchand(){

        bot.addGold(1);
        action.printActionOfSellerBotWhoGainedGold();

    }


    public void architecte(Robot bot) {
        //ActionOfBotDuringARound action = new ActionOfBotDuringARound(bot);
        int i = bot.getChoice();
        if(i == 0 ) {
            bot.setChoice(7);
            List<DistrictsType> listDistrictDrawn = bot.pickListOfDistrict();
            listDistrictDrawn.add(bot.pickListOfDistrict().get(0)) ;
            listDistrictDrawn.add(bot.pickListOfDistrict().get(1)) ;
            List<DistrictsType> listDistrictPicked = bot.pickDistrictCard(listDistrictDrawn);
            action.addListOfDistrict(listDistrictDrawn,listDistrictPicked);
            bot.addDistrict(listDistrictPicked);
            action.printActionOfBotWhoHasBuilt();
            }

        if(i == 1) {
            bot.setChoice(0);
            bot.addGold(2);
            action= new ActionOfBotDuringARound(bot);
            action.printActionOfBotWhoGainedGold(2);

        }
        String hasBuilt = bot.tryBuild();
        action.printBuildingOfBot(hasBuilt);
        String hasBuilt2 = bot.tryBuild();
        action.printBuildingOfBot(hasBuilt2);
        }


    public boolean canDestroyDistrict(Robot victim, DistrictsType district){
        int destructorGolds = bot.getGolds();
        boolean districtInCity = victim.getCity().contains(district);
        return destructorGolds >= district.getCost() && districtInCity;
    }

    public void condottiere(Robot victim) {
        int destructorGolds = bot.getGolds();
        List<DistrictsType> victimDistricts = victim.getCity();

        victimDistricts.sort(Comparator.comparingInt(DistrictsType::getCost).reversed());

        for (DistrictsType district : victimDistricts) {
            boolean verify = canDestroyDistrict(victim, district);

            if (verify) {
                if (bot.getCharacter().getType().equals(MILITATE) &&
                        !victim.getCharacter().getType().equals(RELIGIOUS)) {
                    victim.getCity().remove(district);
                    int goldsAfterDestruction = destructorGolds - district.getCost();
                    bot.setGolds(goldsAfterDestruction + 1);
                    action.printActionOfDestroyDistrict(victim, district, bot.getGolds());
                } else {
                    action.printEvequeImmune(victim, district);
                }
                return;
            }

        }
        action.printActionOfNoneDistrictDestroyed(victim, bot.getGolds());



    }

    public void assassin(Robot victim, List<Robot> bots){
        if(bot.getCharacter().getType().equals(ASSASSIN)){
            bots.remove(victim);
            action.printVictimAssassined(victim);
        }
    }




}


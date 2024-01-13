package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.*;

public class Power {
    private ActionOfBotDuringARound action;
    private Robot bot;

    public static final String MILITAIRE = "militaire";
    public static final String RELIGIEUX = "religieux";

    public Power(Robot bot, ActionOfBotDuringARound action){
        this.bot = bot;
        this.action = action;

    }

    public void marchand(){

        bot.addGold(1);
        //Then the classic scenario occurs
        action.printActionOfSellerBotWhoGainedGold();

    }



    public void architecte() {
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
                if (bot.getCharacter().getType().equals(MILITAIRE) &&
                        !victim.getCharacter().getType().equals(RELIGIEUX)) {
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



}

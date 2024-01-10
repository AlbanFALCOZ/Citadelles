package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Round {

    private List<Robot> bots;

    public Round(List<Robot> bots) {
        this.bots = new ArrayList<>(bots);
    }

    public void assignCrownForKing() {
        int cpt = 0;
        int index = 0 ;
        for (Robot bot : bots) {
            if (bot.isCharacter("Roi")) {
                cpt++;
            }
        }
        if (cpt == 1) {
            for (Robot bot : bots) {
                if (bot.isCharacter("Roi")) {
                    bot.setHasCrown(true);
                }
                if (!bot.isCharacter("Roi")) {
                    bot.setHasCrown(false);
                }
            }
            Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
            cpt=0;
        }
        Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        Collections.rotate(bots, -index);

    }


    public void specialCard(){
        for (Robot bot : bots){
            if(bot.getCharacter().getNumber()==6){
                bot.addGold(1);
            }
        }
    }

    public ArrayList<Robot> sortRobots() {
        ArrayList<Robot> sortedBots = new ArrayList<>(bots);

        // Custom comparator to sort by crown status and then by character number
        Comparator<Robot> crownComparator = Comparator.comparing((Robot bot) -> !bot.getHasCrown())
                .thenComparingInt(bot -> bot.getCharacter().getNumber());

        // Sort the list using the custom comparator
        Collections.sort(sortedBots, crownComparator);

        return sortedBots;
    }

    public void playTurns() {
        specialCard();
        Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        this.sortRobots();
        for (Robot bot : bots) {
            int choice = bot.getChoice();
            ActionOfBotDuringARound actionOfBotDuringARound = new ActionOfBotDuringARound(bot);
            actionOfBotDuringARound.startTurnOfBot();
            switch (choice) {
                case 0:
                    List<DistrictsType> listDistrictDrawn = bot.pickListOfDistrict();
                    List<DistrictsType> listDistrictPicked = bot.pickDistrictCard(listDistrictDrawn);
                     actionOfBotDuringARound.addListOfDistrict(listDistrictDrawn,listDistrictPicked);
                    bot.addDistrict(listDistrictPicked);
                    actionOfBotDuringARound.printActionOfBotWhoHasBuilt();
                    break;
                case 1:
                    bot.addGold(2);
                    actionOfBotDuringARound = new ActionOfBotDuringARound(bot);
                    actionOfBotDuringARound.printActionOfBotWhoGainedGold();
                    break;
                default:
                    break;
            }
            String hasBuilt = bot.tryBuild();
            int goldsWon =  bot.winGoldsByTypeOfBuildings();
            actionOfBotDuringARound.printBuildingAndPowerOfBot(hasBuilt, goldsWon);
        }
        assignCrownForKing();
    }
}
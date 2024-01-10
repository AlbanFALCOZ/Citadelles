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


    public void thiefAction(Robot thief) {
        List<Robot> otherBots = new ArrayList<>(bots);
        otherBots.remove(thief);
        thief.chooseTarget(otherBots);
        Robot target = thief.getTarget();

        int stolenGold = target.getGolds();
        thief.addGold(stolenGold);
        target.setGolds(0);
        System.out.println(thief.getName() + " a volé " + stolenGold + " pièces d'or à " + target.getName());
    }

    public void playTurns() {
        System.out.println(bots);
        specialCard();
        Collections.sort(bots, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        this.sortRobots();
        for (Robot bot : bots) {
            if (bot.isCharacter("voleur")) {
                thiefAction(bot);
            }

            int choice = (int) (Math.random()*2);
            System.out.println("------------------------------------------------------------The turn of " + bot.getName() + " is starting -----------------------------------------------------\n");
            System.out.println(bot.statusOfPlayer());
            switch (choice) {
                case 0:
                    List<DistrictsType> listDistrictDrawn = bot.pickListOfDistrict();

                    String cardDrawn = "";
                    String cardPicked = "";
                    for (int i = 0; i < listDistrictDrawn.size(); i++) {
                        DistrictsType districtInListDistrict = listDistrictDrawn.get(i);
                        cardDrawn += districtInListDistrict.getColor() + districtInListDistrict + districtInListDistrict.getColorReset();
                        if (i < listDistrictDrawn.size()-1) cardDrawn += ",";
                    }
                    System.out.println("The bot drew the following cards : {" + cardDrawn + "}");
                    List<DistrictsType> listDistrictPicked = bot.pickDistrictCard(listDistrictDrawn);
                    for (int i = 0; i < listDistrictPicked.size(); i++) {
                        DistrictsType districtInListDistrict = listDistrictPicked.get(i);
                        cardPicked += districtInListDistrict.getColor() + districtInListDistrict + districtInListDistrict.getColorReset();
                        if (i < listDistrictPicked.size()-1) cardPicked += ",";
                        bot.addDistrict(districtInListDistrict);
                    }
                    System.out.println("The bot choose to pick : {" + cardPicked + "}");
                    System.out.println(bot.getName() + " has now in hand: " + bot.getNumberOfDistrictInHand() + " districts");
                    break;
                case 1:
                    bot.addGold(2);
                    System.out.println(bot.getName() + " earn 2 golds. Total golds now: " + bot.getGolds());
                    break;
                default:
                    break;
            }
            System.out.println(bot.getName() + " built " + bot.tryBuild() + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
            System.out.println(bot.getName() + " has won " + bot.winGoldsByTypeOfBuildings() + " golds by " + bot.getCharacter().getType() + " buildings and has now " + bot.getGolds() + " golds");
            System.out.println(bot.statusOfPlayer() + "\n");
            System.out.println("-------------------------------------------------------The turn of " + bot.getName() + " is over ------------------------------------------------------------------\n");

        }
        assignCrownForKing();
    }
}
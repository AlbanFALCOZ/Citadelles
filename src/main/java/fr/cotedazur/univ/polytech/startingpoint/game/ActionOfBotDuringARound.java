package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
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
        System.out.println(bot.getName() + " drew the following cards : {" + cardDrawn + "}");
        for (int i = 0; i < listDistrictPicked.size(); i++) {
            DistrictsType districtInListDistrict = listDistrictPicked.get(i);
            cardPicked += districtInListDistrict.getColor() + districtInListDistrict + districtInListDistrict.getColorReset();
            if (i < listDistrictPicked.size()-1) cardPicked += ",";
            //bot.addDistrict(districtInListDistrict);
        }
        System.out.println(bot.getName() + " choose to pick : {" + cardPicked + "}");
        System.out.println(bot.getName() + " has now in hand: " + bot.getNumberOfDistrictInHand() + " districts");
    }

    public void printActionOfBotWhoGainedGold(int goldGained) {
        System.out.println(bot.getName() + " earned " + goldGained +" golds. Total golds now: " + bot.getGolds());
    }

    public void printActionOfSellerBotWhoGainedGold() {
        System.out.println("By being a merchant, " + bot.getName() + " earned 1 gold. Total golds now: " + bot.getGolds());
    }

    public void printBuildingAndPowerOfBot(String hasBuilt, int goldsWon) {
        if (!hasBuilt.equals("nothing")) System.out.println(bot.getName() + " built " + hasBuilt + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
        if (goldsWon > 0) System.out.println(bot.getName() + " has won " + goldsWon + " golds by " + bot.getCharacter().getType() + " buildings and has now " + bot.getGolds() + " golds");
        System.out.println(bot.statusOfPlayer());
        System.out.println("\n-------------------------------------------------------The turn of " + bot.getName() + " is over ------------------------------------------------------------------");
    }



    public void printBuildingOfBot(String hasBuilt){
        if (!hasBuilt.equals("nothing")){
            System.out.println(bot.getName() + " built " + hasBuilt + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
        }
    }


    public void printPowerAndGoldEarned(int goldsWon){
        if (goldsWon > 0) System.out.println(bot.getName() + " has won " + goldsWon + " golds by " + bot.getCharacter().getType() + " buildings and has now " + bot.getGolds() + " golds");
        System.out.println(bot.statusOfPlayer());
        System.out.println("\n-------------------------------------------------------The turn of " + bot.getName() + " is over ------------------------------------------------------------------");
    }


    public void printActionOfDestroyDistrict(Robot victim, DistrictsType district, int goldsRestarts) {
        System.out.println(bot.getName() + " destroyed " + district + " of " + victim.getName() + " and now has " + goldsRestarts + " golds");
    }

    public void printActionOfNoneDistrictDestroyed(Robot victim, int destructorGolds) {
        System.out.println(bot.getName() + " can't destroy the districts" + " of " + victim.getName() + " because he has only " + destructorGolds + " golds");
    }

    public void printEvequeImmune(Robot victim, DistrictsType district) {
        System.out.println(bot.getName() + " can't destroy " + district + " of " + victim.getName() + " because he is " + victim.getCharacter().getRole());
    }

    public void printVictimAssassined(Robot victim) {
        System.out.println(bot.getName() + " murdered " + victim.getCharacter().getRole());

    }

    public void printMagicianSwap(Robot victim){
        System.out.println(bot.getName() + " swapped cards with " + victim.getName()) ;
    }

    public void printChoiceOfThief(Robot bot,int numberOfCharacter) {
        System.out.println(bot.getName() + " chose to steal from " + getNameOfCharacterFromNumber(numberOfCharacter));
    }

    public void printThiefStill(Robot victim){
        System.out.println(bot.getName() + " stole " + victim.getGolds() + " golds from " + victim.getName() +". Total golds now " + bot.getGolds());
    }

    public void printMagicianSwapWithDeck(){
        System.out.println(bot.getName() + " choosed to swap with deck");
    }

    public String getNameOfCharacterFromNumber(int number) {
        String[] listName = {"Assassin","Voleur","Magicien","Roi","Évêque","Marchand","Architecte","Condottière"};
        return listName[number-1];
    }
}





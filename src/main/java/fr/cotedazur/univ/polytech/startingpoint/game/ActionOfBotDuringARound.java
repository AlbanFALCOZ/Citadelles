package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fr.cotedazur.univ.polytech.startingpoint.game.Main.logger;


public class ActionOfBotDuringARound {


    private Robot bot;
    private List<DistrictsType> listDistrictDrawn;
    private List<DistrictsType> listDistrictPicked;

    private static final Logger logger = Logger.getLogger(ActionOfBotDuringARound.class.getName());


    public ActionOfBotDuringARound(Robot bot, boolean systemPrint) {
        this.bot = bot;
        System.setProperty("java.util.logging.SimpleFormatter.format","\u001B[37m %5$s%6$s%n \u001B[0m");
        if (!systemPrint) logger.setLevel(Level.OFF);
    }

    public void startTurnOfBot() {
        logger.info("\u001B[32m------------------------------------------------------------The turn of " + bot.getName() + " is starting -----------------------------------------------------\n");
        showStatusOfBot();
    }

    public void showStatusOfBot() {
        logger.info(bot.statusOfPlayer());
    }

    public void showCityOfBot(Robot bot) {
        logger.info("City of " + bot.getName() + " : " + getStringOfListOfDistrict(bot.getCity()));
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
        logger.info(bot.getName() + " drew the following cards : {" + cardDrawn + "}");
        for (int i = 0; i < listDistrictPicked.size(); i++) {
            DistrictsType districtInListDistrict = listDistrictPicked.get(i);
            cardPicked += districtInListDistrict.getColor().getColorDisplay() + districtInListDistrict + districtInListDistrict.getColorReset();
            if (i < listDistrictPicked.size()-1) cardPicked += ",";
            //bot.addDistrict(districtInListDistrict);
        }

        logger.info(bot.getName() + " choose to pick : {" + cardPicked + "}");
        logger.info(bot.getName() + " has now in hand: " + bot.getNumberOfDistrictInHand() + " districts");
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
        logger.info(bot.getName() + " earned \u001B[33m"  + goldGained + " golds \u001B[37m. Total golds now: " + bot.getGolds());
    }

    public void printActionOfSellerBotWhoGainedGold() {
        logger.info("By being a merchant, " + bot.getName() + " earned 1 gold. Total golds now: " + bot.getGolds());
    }

    public void printBuildingAndPowerOfBot(String hasBuilt, int goldsWon) {
        if (!hasBuilt.equals("nothing")) {
            int golds = bot.getGolds() - goldsWon;
            logger.info(bot.getName() + " built " + hasBuilt + " and now has " + golds + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
        }
        if (goldsWon > 0)
            logger.info(bot.getName() + " has won " + goldsWon + " golds by " + bot.getCharacter().getType() + " buildings and has now " + bot.getGolds() + " golds");
        logger.info(bot.statusOfPlayer());
        logger.info("\n\u001B[32m  ------------------------------------------------------------The turn of " + bot.getName() + " is over ------------------------------------------------------------------");
    }


    public void printBuildingOfBot(String hasBuilt) {
        if (!hasBuilt.equals("nothing")) {
            logger.info(bot.getName() + " built " + hasBuilt + " and now has " + bot.getGolds() + " golds and has in hand: " + bot.getNumberOfDistrictInHand() + " districts");
        }
    }


    public void printActionOfDestroyDistrict(Robot victim, DistrictsType district, int goldsRestarts) {
        logger.info(bot.getName() + " destroyed " + district + " of " + victim.getName() + " and now has " + goldsRestarts + " golds");
    }

    public void printActionOfNoneDistrictDestroyed(Robot victim, int destructorGolds) {
        logger.info(bot.getName() + " can't destroy the districts" + " of " + victim.getName() + " because he has only " + destructorGolds + " golds or the district is a Donjon");
    }

    public void printEvequeImmune(Robot victim, DistrictsType district) {
        logger.info(bot.getName() + " can't destroy " + district + " of " + victim.getName() + " because he is " + victim.getCharacter().getRole());
    }

    public void printVictimAssassined(Robot victim) {
        logger.info(bot.getName() + " murdered " + victim.getCharacter().getRole());

    }

    public void printMagicianSwap(Robot victim) {
        logger.info(bot.getName() + " swapped cards with " + victim.getName());
    }

    public void printChoiceOfThief(Robot bot, int numberOfCharacter) {
        logger.info(bot.getName() + " chose to steal from " + getNameOfCharacterFromNumber(numberOfCharacter));
    }

    public void printThiefStill(Robot victim) {
        logger.info(bot.getName() + " stole " + victim.getGolds() + " golds from " + victim.getName() + ". Total golds now " + bot.getGolds());
    }

    public void printMagicianSwapWithDeck() {
        logger.info(bot.getName() + " choosed to swap with deck");
    }

    public String getNameOfCharacterFromNumber(int number) {
        String[] listName = {"Assassin", "Voleur", "Magicien", "Roi", "Évêque", "Marchand", "Architecte", "Condottière"};
        return listName[number - 1];
    }

    public void printCantAffectVictim(Robot victim) {
        logger.info(bot.getName() + " can't steal " + victim.getName() + " because he has been assassinated");
    }

    public void printDistrictRecovered(Robot victim, DistrictsType district) {
        logger.info(victim.getName() + " got district " + district.getName() + " back into his hand by paying 1 gold thanks to district Cimetière.");
    }

    public void printLaboratoryAction(List<DistrictsType> listOfDistrictRemoved) {
        logger.info("Thanks to the laboratory, " + bot.getName() + " has removed " + getStringOfListOfDistrict(listOfDistrictRemoved) + " and has gained one gold");
    }

    public void printManufactureAction(List<DistrictsType> listOfDistrictPicked) {
        logger.info("Thanks to the manufacture, " + bot.getName() + " lost 3 golds but added {" + getStringOfListOfDistrict(listOfDistrictPicked) + "} to his hand");
    }

    public void printPickAnyDistrict(DistrictsType currentDistrict) {
        logger.info(bot.getName() + " chose " + currentDistrict.getName() + " because it is not in his hand or city.");
    }

    public void printPickSpecialDistrict(DistrictsType currentDistrict) {
        logger.info(bot.getName() + " chose " + currentDistrict.getName() + " because it is a special district and the most expensive and is not in his hand or city.");
    }

    public void printPickDistrictByType(DistrictsType currentDistrict) {
        logger.info(bot.getName() + " chose " + currentDistrict.getName() + " because it is the same type as his character.");
    }

    public void printCantPickDistrict() {
        logger.info(bot.getName() + " can't choose any district because they are already in his hand or city ");
    }

    public void printBuildDistrictWithSameType(String district) {
        logger.info(bot.getName() + " build " + district + " because it is the same type as his character.");

    }

    public void printBuildFrequentTypeDistrict(String district) {
        logger.info(bot.getName() + " build " + district + " because it is the most frequent type.");

    }

    public void printPickCharacterWithPriority(CharactersType character) {
        logger.info(bot.getName() + " chose " + character + " because he has the most districts of this type and it is available.");
    }

    public void printPickdefaultCharacter() {
        logger.info(bot.getName() + " chose the first character in the list because he has no districts of any type.");
    }
}





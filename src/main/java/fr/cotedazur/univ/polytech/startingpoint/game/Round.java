package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Power;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.*;

/**
 * cette classe permet de gérer les tours de jeu
 */
public class Round {

    private List<Robot> bots;

    /**
     * @param bots la liste des robots
     *             Constructeur de la classe Round
     */
    public Round(List<Robot> bots) {
        this.bots = new ArrayList<>(bots);
    }

  
    /**
     * cette méthode permet d'assigner la couronne au roi
     */
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


    /**
     * @return la liste des robots triée par ordre de couronne et de numéro de personnage
     */
    public ArrayList<Robot> sortRobots() {
        ArrayList<Robot> sortedBots = new ArrayList<>(bots);

        // Custom comparator to sort by crown status and then by character number
        Comparator<Robot> crownComparator = Comparator.comparing((Robot bot) -> !bot.getHasCrown())
                .thenComparingInt(bot -> bot.getCharacter().getNumber());

        // Sort the list using the custom comparator
        Collections.sort(sortedBots, crownComparator);

        return sortedBots;
    }



    /**
     * @param thief le robot voleur
     *              cette méthode permet au voleur de voler de l'or à un autre robot
     */
    public void thiefAction(Robot thief) {
        /*
        List<Robot> otherBots = new ArrayList<>(bots);
        otherBots.remove(thief);
        thief.chooseTarget(otherBots);
        Robot target = thief.getTarget();

        int stolenGold = target.getGolds();
        thief.addGold(stolenGold);
        target.setGolds(0);
        System.out.println(thief.getName() + " a volé " + stolenGold + " pièces d'or à " + target.getName());

         */
    }

    public void choosePowerOfBot(Robot bot) {
        List<Robot> robots = new ArrayList<>(this.bots);
        ActionOfBotDuringARound actionOfBotDuringARound = new ActionOfBotDuringARound(bot);
        Power powerOfBot = new Power(bot, actionOfBotDuringARound);
        switch (bot.getCharacter()) {
            case ASSASSIN:
                robots.removeIf(robot -> robot.getCharacter().equals(CharactersType.ASSASSIN));
                Collections.shuffle(robots);
                if (!robots.isEmpty()) {
                    powerOfBot.assassin(robots.get(0), bots);
                }
                break;
            case MARCHAND:
                powerOfBot.marchand();
                break;
            case ARCHITECTE:
                powerOfBot.architecte(bot);
                break;
            case CONDOTTIERE:
                robots.removeIf(robot -> robot.getCharacter().equals(CharactersType.CONDOTTIERE));
                Collections.shuffle(robots);
                if (!robots.isEmpty()) {
                    powerOfBot.condottiere(robots.get(0));
                }
                break;
            default:
                break;
        }

    }


    /**
     * cette méthode permet de jouer les tours de jeu
     * On trie les robots par ordre croissant de numéro de personnage
     * @see Round#sortRobots() pour trier les robots par ordre de couronne et de numéro de personnage
     * @see Round#thiefAction(Robot) pour voler de l'or à un autre robot
     * @see Robot#pickListOfDistrict() pour piocher une liste de cartes
     * @see Robot#pickDistrictCard(List) pour choisir une carte dans la liste de cartes piochées
     * @see Robot#addDistrict(DistrictsType) pour ajouter une carte dans la main du robot
     * @see Robot#addGold(int) pour ajouter de l'or au robot
     * @see Robot#tryBuild() pour construire un district
     * @see Robot#winGoldsByTypeOfBuildings() pour gagner de l'or en fonction du type de bâtiment construit
     * @see Robot#countBuildingsByType() pour compter le nombre de bâtiments d'un type donné
     * @see Robot#isCharacter(String) pour savoir si un robot a un personnage donné
     *
     */
    public void playTurns() {
        bots.sort(Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        this.sortRobots();
        for (Robot bot : bots) {

            ActionOfBotDuringARound actionOfBotDuringARound = new ActionOfBotDuringARound(bot);
            actionOfBotDuringARound.startTurnOfBot();
            bot.setChoice(bot.generateChoice());
            choosePowerOfBot(bot);
            switch (bot.getChoice()) {
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
                    actionOfBotDuringARound.printActionOfBotWhoGainedGold(2);
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
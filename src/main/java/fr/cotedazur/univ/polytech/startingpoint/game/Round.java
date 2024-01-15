package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Power;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.*;

/**
 * cette classe permet de gérer les tours de jeu
 */
public class Round {

    private List<Robot> bots;
    private boolean systemPrint;
    private DeckDistrict deck;
    private int numberOfCharacterToStealFrom = 0;
    private Robot voleur;
    private Robot victimOfVoleur;

    /**
     * @param bots la liste des robots
     *             Constructeur de la classe Round
     */
    public Round(List<Robot> bots, boolean systemPrint, DeckDistrict deck) {
        this.bots = new ArrayList<>(bots);
        this.systemPrint = systemPrint;
        this.deck = deck;
    }

    public Round(List<Robot> bots) { this(bots,true,new DeckDistrict());}

  
    /**
     * cette méthode permet d'assigner la couronne au roi
     */
    public void assignCrownForKing() {
        int cpt = 0;
        int index = 0 ;
        for (Robot bot : bots) {
            if (bot.isCharacter("noble")) {
                cpt++;
            }
        }
        if (cpt == 1) {
            for (Robot bot : bots) {
                if (bot.isCharacter("noble")) {
                    bot.setHasCrown(true);
                }
                if (!bot.isCharacter("noble")) {
                    bot.setHasCrown(false);
                }
            }
            bots.sort(Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        }
        bots.sort(Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        Collections.rotate(bots, -index);

    }


    /**
     * @return la liste des robots triée par ordre de couronne et de numéro de personnage
     */
    public List<Robot> sortRobots() {
        ArrayList<Robot> sortedBots = new ArrayList<>(bots);


        Comparator<Robot> crownComparator = Comparator.comparing((Robot bot) -> !bot.getHasCrown())
                .thenComparingInt(bot -> bot.getCharacter().getNumber());


        Collections.sort(sortedBots, crownComparator);
        return sortedBots;
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
                    powerOfBot.assassin(robots.get(0));
                }
                break;
            case MARCHAND:
                powerOfBot.marchand();
                break;
            case ARCHITECTE:
                powerOfBot.architecte(bot, deck);
                break;
            case CONDOTTIERE:
                robots.removeIf(robot -> robot.getCharacter().equals(CharactersType.CONDOTTIERE));
                Collections.shuffle(robots);
                if (!robots.isEmpty()) {
                    powerOfBot.condottiere(robots.get(0));
                }
                break;
            case VOLEUR:
                if (numberOfCharacterToStealFrom == 0) {
                    numberOfCharacterToStealFrom = (int) (Math.random()*6 + 2);
                    this.voleur = bot;
                    actionOfBotDuringARound.printChoiceOfThief(voleur, numberOfCharacterToStealFrom);
                }
                else {
                    powerOfBot.voleur(victimOfVoleur);
                }
                break;
            case MAGICIEN:
                robots.removeIf(robot -> robot.getCharacter().equals(CharactersType.MAGICIEN));
                Collections.shuffle(robots);
                if (!robots.isEmpty()) {
                    powerOfBot.magicien(robots.get(0), deck);
                }
                break ;
            default:
                break;
        }

    }

    public Robot getRandomRobotDifferentFrom(Robot bot) {
        Collections.shuffle(bots);
        while (bots.get(0).getName().equals(bot.getName())) Collections.shuffle(bots);
        return bots.get(0);
    }


    /**
     * cette méthode permet de jouer les tours de jeu
     * On trie les robots par ordre croissant de numéro de personnage
     * @see Round#sortRobots() pour trier les robots par ordre de couronne et de numéro de personnage
     * @see Robot#pickListOfDistrict(DeckDistrict) pour piocher une liste de cartes
     * @see Robot#pickDistrictCard(List, DeckDistrict) pour choisir une carte dans la liste de cartes piochées
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
        numberOfCharacterToStealFrom = 0;
        for (Robot bot : bots) {
            if(!bot.getIsAssassinated()){
                ActionOfBotDuringARound actionOfBotDuringARound = new ActionOfBotDuringARound(bot);
                actionOfBotDuringARound.startTurnOfBot();
                if (bot.getCharacter().getNumber() == numberOfCharacterToStealFrom) {
                    this.victimOfVoleur = bot;
                    choosePowerOfBot(voleur);
                }
                bot.setChoice(bot.generateChoice());
                choosePowerOfBot(bot);
                switch (bot.getChoice()) {
                    case 0:
                        List<DistrictsType> listDistrictDrawn = bot.pickListOfDistrict(deck);
                        List<DistrictsType> listDistrictPicked = bot.pickDistrictCard(listDistrictDrawn, deck);
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

        }
        for(Robot bot : bots){
            bot.setIsAssassinated(false);

        }
        assignCrownForKing();

    }
}
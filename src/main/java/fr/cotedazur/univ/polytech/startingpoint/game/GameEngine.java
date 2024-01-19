package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotNora;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotRandom;
import fr.cotedazur.univ.polytech.startingpoint.robots.RobotWithChoice;

import java.util.*;

/**
 * cette classe représente le moteur du jeu
 */
public class GameEngine {

    private ArrayList<RobotNora> bots;
    private DeckDistrict deckDistricts;
    private DeckCharacters deckCharacters;
    private Round round;

    private  int list[] = {4, 2, 2, 2};

    private boolean systemPrint = false;

    //private Power power = new Power("a power") ;

    /**
     * Constructeur de la classe GameEngine
     * On initialise le deck de districts
     * On initialise le deck de personnages
     * On initialise la liste des robots*
     * On initialise le round
     * On initialise les robots
     */
    public GameEngine(boolean systemPrint) {
        this.systemPrint = systemPrint;
        deckDistricts = new DeckDistrict();
        deckCharacters = new DeckCharacters();
        this.bots = new ArrayList<>();
        round = new Round(bots);
        initializeBots();
    }

    public GameEngine() {
        this(true);
    }


    /**
     * cette méthode permet d'initialiser les robots
     * On ajoute 4 robots dans la liste des robots
     * On ajoute 4 districts dans la main de chaque robot
     * On mélange les districts
     */
    public void initializeBots() {
        String[] name = {"Alban", "Sara", "Stacy", "Nora"};
        for (int i = 0; i < 4; i++) {
            RobotNora bot;
            if (i == 0) bot = new RobotNora(name[i]);
            else bot = new RobotNora(name[i]);
            for (int j = 0; j < 4; j++) {
                bot.addDistrict(deckDistricts.getDistrictsInDeck());
            }
            bots.add(bot);
        }

    }

    /**
     * @return la liste des robots
     */
    public List<RobotNora> getBots() {
        return bots;
    }

    /**
     * cette méthode permet de distribuer les personnages aux robots
     * On mélange les personnages
     * on donne au robot qui a la couronne son personnage en premier
     * on donne aux autres robots leurs personnages
     * on affiche le personnage de chaque robot
     */
    public void robotsPickCharacters() {
        int i = 1;
        List<CharactersType> listCharacters = deckCharacters.getCharactersInHand();
        destroyCharacters(listCharacters);
        Collections.shuffle(listCharacters);

        for (Robot bot : bots) {
            if (bot.getHasCrown()) {
                bot.setCharacter(listCharacters.get(0));
                if (systemPrint)
                    System.out.println(bot.getName() + " With crown Picked " + listCharacters.get(0).getColor().getColorDisplay() + listCharacters.get(0).getRole() + bot.getRESET());
                listCharacters.remove(listCharacters.get(0));
            }
        }
        for (Robot bot : bots) {
            if (!bot.getHasCrown()) {
                bot.setCharacter(listCharacters.get(i));
                if (systemPrint)
                    System.out.println(bot.getName() + " Picked " + listCharacters.get(i).getColor().getColorDisplay() + listCharacters.get(i).getRole() + bot.getRESET());
                i++;
            }
        }
    }


    /**
     * cette méthode permet de donner la couronne à un robot
     * On mélange les robots
     * On donne la couronne au premier robot de la liste
     * On trie les robots par ordre croissant de numéro de personnage
     */
    public void assignCrown() {
        Collections.shuffle(bots);
        bots.get(0).setHasCrown(true);

    }

    /**
     * @return true si un robot a construit 8 districts
     */
    public boolean isBuiltEigthDistrict() {
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() == 8) {
                return true;
            }
        }
        return false;
    }


    /**
     * cette méthode permet de jouer les tours du jeu
     * On crée un nouveau round
     * On donne la couronne au premier robot de la liste
     * <p>
     * On appelle la méthode robotsPickCharacters pour que les robots choisissent leurs personnages
     * On appelle la méthode playTurns pour que les robots jouent leurs tours
     * On répète les étapes précédentes jusqu'à ce qu'un robot construise 8 districts
     */
    public void gameTurns() {
        round = new Round(bots, systemPrint, deckDistricts);
        String turnStarting = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Turn ";
        String turnEnding = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        if (systemPrint)
            System.out.println("=============================================================================GAME IS STARTING====================================================================\n");
        int comptTurn = 1;
        assignCrown();

        while (!isBuiltEigthDistrict()) {


            for (Robot bot : bots) {
                if (bot.getHasCrown()) {
                    if (systemPrint)
                        System.out.println(bot.getName() + " has crown and start the call of the characters");
                }
            }
            robotsPickCharacters();
            if (systemPrint) System.out.println(turnStarting + comptTurn + " is starting" + turnEnding);
            bots.sort(Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));

            round.playTurns();
            if (systemPrint) System.out.println(turnStarting + comptTurn + " is over" + turnEnding);
            comptTurn++;
            round = new Round(bots, systemPrint, deckDistricts);

        }

        {
        int i = 0;
        for (RobotNora bot : bots) {
            if (bot.hasEightDistrict()) {
                bot.setScore(bot.getScore() + list[i]);
                if(i!=0){
                    System.out.println(bot.getName() + " gets 2 extra points for having 8 districts");
                } else {
                    System.out.println(bot.getName() + " ended the game and earns 4 extra points");
                }
                i++;
                }
            }
        }
        Winner winner = new Winner(bots);
        winner.miracleDistrictEffect();


    }
    /**
     * cette méthode permet de vider la liste des robots
     */
    public void clearBots() {
        bots.clear();
    }


    /**
     * @param robot le robot à ajouter
     *              cette méthode permet d'ajouter un robot à la liste des robots
     */
    public void addRobot(RobotNora robot) {
        this.bots.add(robot);
    }


    /**
     * @param charactersInHand la liste des personnages
     *                         cette méthode permet de détruire 3 personnages
     */
    public void destroyCharacters(List<CharactersType> charactersInHand) {
        charactersInHand.remove(CharactersType.ROI);
        Collections.shuffle(charactersInHand, new Random());
        for (int i = 0; i < 3; i++) {
            if (!charactersInHand.isEmpty()) {
                CharactersType destroyedCharacter = charactersInHand.remove(0);

                if (systemPrint) System.out.println("Destroyed character: " + destroyedCharacter.getColor().getColorDisplay() + destroyedCharacter.getRole() + bots.get(0).getRESET());

            }
        }
        charactersInHand.add(CharactersType.ROI);
    }





}
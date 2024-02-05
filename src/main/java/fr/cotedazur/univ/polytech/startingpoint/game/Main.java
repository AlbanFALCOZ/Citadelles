package fr.cotedazur.univ.polytech.startingpoint.game;


import com.beust.jcommander.JCommander;
import fr.cotedazur.univ.polytech.startingpoint.arguments.CitadelleArguments;

import java.util.logging.Logger;

/**
 * cette classe permet de jouer une partie de Citadelle et de déterminer le gagnant
 */
public class Main {
    
    public static final Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {
        CitadelleArguments citadelleArguments = new CitadelleArguments();
        JCommander commander = JCommander.newBuilder()
                .addObject(citadelleArguments)
                .build();
        commander.parse(args);
        if (citadelleArguments._2ThousandsGame()) testBots();
        if (citadelleArguments.isDemoMode()) showGame();

    }


    public static void showGame() {
        GameEngine Game = new GameEngine();
        Game.gameTurns();
        logger.info("\n");
        logger.info("End of the game !!!");
        Winner winner = new Winner(Game.getBots());
        winner.printScore();
        logger.info(winner.showWinners());
    }

    public static void testBots() {
        long start = System.currentTimeMillis();
        int[] listWinners = new int[4];
        int numberOfGames = 10000;
        int i;
        String listName[] = {"Alban","Stacy","Nora","Sara"};
        for (i = 0; i < numberOfGames; i++) {

            GameEngine Game = new GameEngine(false);
            Game.gameTurns();
            Winner winner = new Winner(Game.getBots());
            for (String winners : winner.getWinners()) {
                if (winners.equals("Stacy")) {
                    listWinners[0]++;
                }
                if (winners.equals("Nora")) {
                    listWinners[1]++;
                }
                if (winners.equals("Sara")) {
                    listWinners[2]++;
                }
                if (winners.equals("Alban")) {
                    listWinners[3]++;
                }
            }
        }
        logger.info("Nombre de parties : " + i);
        for (i = 0; i < listWinners.length; i++) {
            logger.info("Winrate N°" + listName[i] + " : " + (float) listWinners[i] / numberOfGames * 100 + "%");

        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        logger.info(timeElapsed / 1000 + "sec");
    }


}

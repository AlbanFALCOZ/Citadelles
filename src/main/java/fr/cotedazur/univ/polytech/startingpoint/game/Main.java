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
        testBots();

    }


    public static void showGame() {
        GameEngine Game = new GameEngine();
        Game.gameTurns();
        logger.info("\n");
        logger.info("End of the game !!!");
        Winner winner = new Winner(Game.getBots(),true);
        winner.printScore();
        logger.info(winner.showWinners());
    }

    public static void testBots() {
        long start = System.currentTimeMillis();
        int[] listWinners = new int[4];
        int[] listWinnersTied = new int[4];
        int numberOfGames = 1000;
        int i;
        String[] listName = {"Stacy","Richardo","Sara","Alban"};
        for (i = 0; i < numberOfGames; i++) {
            GameEngine Game = new GameEngine(false);
            Game.gameTurns();
            Winner winner = new Winner(Game.getBots(),false);
            winner.setScores();
            for (String winners : winner.getWinners()) {
                if (winners.equals("Stacy")) {
                    if (winner.getWinners().size() == 1) listWinners[0]++;
                    else listWinnersTied[0]++;
                }
                if (winners.equals("Richardo")) {
                    if (winner.getWinners().size() == 1) listWinners[1]++;
                    else listWinnersTied[1]++;
                }
                if (winners.equals("Sara")) {
                    if (winner.getWinners().size() == 1) listWinners[2]++;
                    else listWinnersTied[2]++;
                }
                if (winners.equals("Alban")) {
                    if (winner.getWinners().size() == 1) listWinners[3]++;
                    else listWinnersTied[3]++;
                }
            }
        }
        logger.info("Nombre de parties : " + i);
        for (i = 0; i < listWinners.length; i++) {
            int numberOfGamesWon = listWinners[i];
            float winRate =(float) numberOfGamesWon / numberOfGames * 100;
            int numberOfGamesWonButTied = listWinnersTied[i];
            float tieRate =(float) numberOfGamesWonButTied / numberOfGames * 100;
            int numberOfGamesLost = numberOfGames - numberOfGamesWon - numberOfGamesWonButTied;
            float loseRate =(float) numberOfGamesLost / numberOfGames * 100;
            logger.info(listName[i] + " : " + "nombres de parties jouées : " + numberOfGames);
            logger.info("-nombres de parties gagnées : " + numberOfGamesWon + "/" + numberOfGames + "soit " + winRate + "%");
            logger.info("-nombres d'égalitées : " + numberOfGamesWonButTied + "/" + numberOfGames + "soit " + tieRate + "%");
            logger.info("-nombres de parties perdues : " + numberOfGamesLost + "/" + numberOfGames + "soit " + loseRate + "%");
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        logger.info(timeElapsed / 1000 + "sec");
    }


}

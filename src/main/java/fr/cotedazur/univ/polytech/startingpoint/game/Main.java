package fr.cotedazur.univ.polytech.startingpoint.game;


import com.beust.jcommander.JCommander;
import fr.cotedazur.univ.polytech.startingpoint.arguments.CitadelleArguments;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.gamestats.ParseFullGameStats;
import fr.cotedazur.univ.polytech.startingpoint.gamestats.WriteStatsByLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * cette classe permet de jouer une partie de Citadelle et de déterminer le gagnant
 */
public class Main {
    
    public static final Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws Exception {

        CitadelleArguments citadelleArguments = new CitadelleArguments();
        JCommander commander = JCommander.newBuilder()
                .addObject(citadelleArguments)
                .build();
        commander.parse(args);
        if (citadelleArguments._2ThousandsGame()) testBots();
        if (citadelleArguments.isDemoMode()) showGame();
=======
        if (citadelleArguments.getCsvFilePath()){
            testBots();
            ParseFullGameStats.parseFullStats();
        }
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
        String[] listName = {"Stacy","Nora","Sara","Alban"};
        play1000Games(false,listName);
        String[] listNameDiscretBots = {"RobotDiscret1","RobotDiscret2","RobotDiscret3","RobotDiscret4"};
        play1000Games(true,listNameDiscretBots);
    }

    public static void play1000Games(boolean onlyDiscretBots, String[] listName) {
        int[] listWinners = new int[4];
        int[] listWinnersTied = new int[4];
        int numberOfGames = 1000;
        int i;


        Map<String,Integer> mapScore = new HashMap<>();
        for (String name : listName) mapScore.put(name,0);

        for (i = 0; i < numberOfGames; i++) {
            GameEngine Game = new GameEngine(false,onlyDiscretBots);
            Game.gameTurns();
            Winner winner = new Winner(Game.getBots(),false);
            winner.setScores();

            for(Robot bot : Game.getBots()) {
                mapScore.put(bot.getName(),mapScore.get(bot.getName())+bot.getScore());
            }

            for (String winners : winner.getWinners()) {
                for (int j = 0; j < listName.length; j++) {
                    if (winners.equals(listName[j])) {
                        if (winner.getWinners().size() == 1) listWinners[j]++;
                        else listWinnersTied[j]++;
                    }
                }
            }
        }
        logger.info("Debut des statistiques");
        for (i = 0; i < listWinners.length; i++) {
            int numberOfGamesWon = listWinners[i];
            int numberOfGamesWonButTied = listWinnersTied[i];
            int numberOfGamesLost = numberOfGames - numberOfGamesWon - numberOfGamesWonButTied;

            logger.info(listName[i] + " : " + "nombres de parties jouees : " + numberOfGames);
            logger.info("-Score moyen : " + (float) mapScore.get(listName[i])/numberOfGames);
            logger.info("-Nombres de parties gagnees : " + numberOfGamesWon + "/" + numberOfGames + " soit " + getRateFromNumberOfGames(numberOfGamesWon,numberOfGames) + "%");
            logger.info("-Nombres d'egalitees        : " + numberOfGamesWonButTied + "/" + numberOfGames + " soit " + getRateFromNumberOfGames(numberOfGamesWonButTied,numberOfGames) + "%");
            logger.info("-Nombres de parties perdues : " + numberOfGamesLost + "/" + numberOfGames + " soit " + getRateFromNumberOfGames(numberOfGamesLost,numberOfGames) + "%");
        }
        logger.info("Fin des statistiques\n");
    }

    public static float getRateFromNumberOfGames(int numberOfGames, int totalNumberOfGames) {
        return (float) numberOfGames / totalNumberOfGames * 100;
    }


}

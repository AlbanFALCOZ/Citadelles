package fr.cotedazur.univ.polytech.startingpoint.game;


import com.beust.jcommander.JCommander;
import fr.cotedazur.univ.polytech.startingpoint.arguments.CitadelleArguments;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;
import fr.cotedazur.univ.polytech.startingpoint.gamestats.ParseFullGameStats;
import fr.cotedazur.univ.polytech.startingpoint.gamestats.WriteStatsByLine;

import java.util.HashMap;
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
        if (citadelleArguments.getCsvFilePath()){
            testBots();
            ParseFullGameStats.parseFullStats();
        }
    }


    /**
     * cette méthode permet de montrer les logs d'une seule game
     */
    public static void showGame() {
        GameEngine Game = new GameEngine();
        Game.gameTurns();
        logger.info("\n");
        logger.info("End of the game !!!");
        Winner winner = new Winner(Game.getBots(),true);
        winner.printScore();
        logger.info(winner.showWinners());
    }

    /**
     * cette méthode permet de jouer 1000 games avec nos 4 bots
     * puis de jouer 1000 games avec uniquement notre meilleur bot
     */
    public static void testBots() {
        play1000Games(false);
        play1000Games(true);
    }

    /**
     * cette méthode permet de jouer 1000 games
     * et d'afficher les statistiques des games
     */

    public static void play1000Games(boolean onlyDiscretBots) {
        String[] listName = new String[4];
        int[] listWinners = new int[4];
        int[] listWinnersTied = new int[4];
        int numberOfGames = 1000;
        int i;
        int compt = 0;

        //On stocke les noms des robots dans une liste pour savoir quel robot a gagné
        GameEngine tempGame = new GameEngine(false,onlyDiscretBots);
        for(Robot bot: tempGame.getBots()) listName[compt++] = bot.getName();

        //Ce dictionnaire va nous permettre de stocker le score moyen de chaque bot
        Map<String,Integer> mapScore = new HashMap<>();
        for (String name : listName) mapScore.put(name,0);

        for (i = 0; i < numberOfGames; i++) {
            //onlyDiscretBots nous permet de savoir s'il faut jouer avec 4 bots différents ou 4 bots discrets qui est notre meilleur bot
            GameEngine Game = new GameEngine(false,onlyDiscretBots);
            Game.gameTurns();

            Winner winner = new Winner(Game.getBots(),false);
            winner.setScores();

            //On update le score de chaque bot dans le dictionnaire
            for(Robot bot : Game.getBots()) {
                mapScore.put(bot.getName(),mapScore.get(bot.getName())+bot.getScore());
            }

            //On update le nombre de win de +1 pour chaque bot qui a gagné
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

            String[][] data = {{listName[i], String.valueOf(numberOfGamesWon), String.valueOf(getRateFromNumberOfGames(numberOfGamesWon,numberOfGames)), String.valueOf(numberOfGamesWonButTied), String.valueOf(getRateFromNumberOfGames(numberOfGamesWonButTied,numberOfGames)), String.valueOf(numberOfGamesLost), String.valueOf(getRateFromNumberOfGames(numberOfGamesLost,numberOfGames)), String.valueOf((float) mapScore.get(listName[i])/numberOfGames)}};
            WriteStatsByLine.writeDataLineByLine(data);

        }
        logger.info("Fin des statistiques\n");
    }


    /**
     * cette méthode permet de récupérer le winrate à partir d'un certain nombre de games
     */
    public static float getRateFromNumberOfGames(int numberOfGames, int totalNumberOfGames) {
        return (float) numberOfGames / totalNumberOfGames * 100;
    }


}

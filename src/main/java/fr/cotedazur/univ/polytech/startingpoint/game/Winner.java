package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.ArrayList;
import java.util.List;


/**
 * cette classe permet de calculer les scores des robots et de déterminer le gagnant
 */
public class Winner {

    private List<Robot> bots;


    /**
     * @param bots la liste des robots
     *             Constructeur de la classe Winner
     */
    public Winner(List<Robot> bots) {
        this.bots = new ArrayList<>(bots);

    }


    /**
     * @return les scores des robots
     * cette méthode permet de calculer les scores des robots
     */
    public String calculateScores() {
        String scores = "";
        for (Robot bot : bots) {
            int score = bot.calculateScore();
            scores += bot.getName() + " has a score of " + score + "\n";
        }
        return scores;

    }


    /**
     * @return la liste des gagnants
     */
    public List<String> getWinners() {

        List<Robot> winners = new ArrayList<>();
        int highestScore = -1;

        for (Robot bot : bots) {
            int score = bot.calculateScore();
            if (score > highestScore) {
                winners.clear();
                winners.add(bot);
                highestScore = score;
            } else if (score == highestScore) {
                winners.add(bot);
            }
        }

        List<String> winnerNames = new ArrayList<>();
        for (Robot winner : winners) {
            winnerNames.add(winner.getName());
        }

        return winnerNames;
    }


    /**
     * @return le gagnant ou les gagnants
     */
    public String showWinners() {
        List<String> winners = getWinners();
        if (winners.size() == 1) {
            return "The winner is : " + winners.get(0);
        } else {
            return "This is an equality ! The winners are: " + String.join(", ", winners);
        }
    }

}

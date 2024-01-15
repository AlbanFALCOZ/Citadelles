package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.*;


/**
 * cette classe permet de calculer les scores des robots et de déterminer le gagnant
 */
public class Winner {

    private List<Robot> bots;

    private boolean hasFiveColors = false ;






    /**
     * @param bots la liste des robots
     *             Constructeur de la classe Winner
     */
    public Winner(List<Robot> bots) {
        this.bots = new ArrayList<>(bots);

    }



    public void setScores(){
        for(Robot bot : bots) {
            bot.setScore(bot.calculateScore());
        }
        awardEndGameBonus();
        awardDistrictColorBonus();

    }
    /**
     * @return les scores des robots
     * cette méthode permet de calculer les scores des robots
     */


    public String printScore(){
        String scores = "" ;
        setScores();
        for (Robot bot : bots){
            scores += bot.getName() + " has a score of " + bot.getScore() + "\n";
        }
        return scores;
    }


    /**
     * @return la liste des gagnants
     */
    public List<String> getWinners() {

        List<Robot> winners = new ArrayList<>();
        int highestScore = -1;
        System.out.println("Je suis dans trouver winners");
        for (Robot bot : bots) {
            int score = bot.getScore();
            System.out.println(bot.getName() + " has a score of " + score); // Debug output
            if (score > highestScore) {
                winners.clear();
                winners.add(bot);
                highestScore = score;
            }
            else if (score == highestScore) {
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
        if (winners.isEmpty()) {
            return "No winners.";
        } else if (winners.size() == 1) {
            return "The winner is: " + winners.get(0);
        } else {
            return "There is an equality! The winners are: " + String.join(", ", winners);
        }
    }
    public void awardEndGameBonus() {
        Iterator<Robot> iterator = bots.iterator();
        while (iterator.hasNext()) {
            Robot bot = iterator.next();
            if (bot.getNumberOfDistrictInCity() == 8) {
                bot.setScore(bot.getScore() + 8);
                System.out.println(bot.getName() + " ended the game, they get 8 extra points");
                break;
            }
        }
    }


    public void awardDistrictColorBonus() {
        for (Robot bot : bots) {
            Set<String> uniqueColors = new HashSet<>();
            for (DistrictsType district : bot.getDistrictInHand()) {
                uniqueColors.add(district.getColor());
            }
            if (uniqueColors.size() >= 5) {
                bot.setScore(bot.getScore()+5);
                System.out.println(bot.getName() + "Earned 5 extra points for having 5 different colors");
            }
        }
    }




}

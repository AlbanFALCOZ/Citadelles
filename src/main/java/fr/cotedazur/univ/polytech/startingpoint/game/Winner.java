package fr.cotedazur.univ.polytech.startingpoint.game;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.Colors;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.sql.SQLOutput;
import java.util.*;


/**
 * cette classe permet de calculer les scores des robots et de déterminer le gagnant
 */
public class Winner {

    private List<Robot> bots;

    private List<Robot> winnerBots ;

    private boolean hasFiveColors = false ;


    /**
     * @param bots la liste des robots
     *             Constructeur de la classe Winner
     */
    public Winner(List<Robot> bots) {
        this.bots = new ArrayList<>(bots);

    }



    public List<Robot> getWinnerBots(){
        return winnerBots;
    }



    public void setScores(){
        for(Robot bot : bots) {
            bot.setScore(bot.getScore() + bot.calculateScore());
        }

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

        for (Robot bot : bots) {
            int score = bot.getScore();

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



    public void miracleDistrictEffect(){
        for (Robot bot  : bots){
            if(bot.getCity().contains(DistrictsType.COURT_DES_MIRACLES)){
                int index = bot.getCity().indexOf(DistrictsType.COURT_DES_MIRACLES);
               if( index != bot.getCity().size()-1 ) {
                   Colors randomColor =Colors.getRandomColorCode() ;
                   bot.getCity().get(index).setColor(randomColor);
                   System.out.println(bot.getName() + " choosed to change the color of their miracle card to " + randomColor);

               }
            }
        }
    }


    public void awardDistrictColorBonus() {
        for (Robot bot : bots) {
            int originalScore = bot.getScore();
            Set<String> uniqueColorsInCity = new HashSet<>();
            for (DistrictsType district : bot.getCity()) {
                uniqueColorsInCity.add(district.getColor().getColorDisplay());
            }
            if (uniqueColorsInCity.size() >= 5) {
                System.out.println(bot.getName() + " has " + bot.getScore() + " points ");
                System.out.println(bot.getName() + " has 5 districts in their city they earn 3 extra points ");
                bot.setScore(originalScore + 3);
                System.out.println(bot.getName() + " has now " + bot.getScore());
                System.out.println("--------------------------------------------------");

            }
        }
    }




}

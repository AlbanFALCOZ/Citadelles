package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;

import java.util.List;

public class StrategyAgressif {

    public StrategyAgressif(){}
    public void isAgressif(List<Robot> bots, RobotRichardo robot) {
        for (Robot bot : bots) {
            if ((bot.getNumberOfDistrictInCity() > robot.getNumberOfDistrictInCity() + 2 || bot.getNumberOfDistrictInCity() > 2) && (robot.thereIsA(CharactersType.VOLEUR, robot.getAvailableCharacters()))) {
                robot.setAgressif(true);
                break ;
            }
            else if(robot.getNumberOfDistrictInCity() >4){
                robot.setAgressif(true);

            }
            else if(bot.getNumberOfDistrictInHand() <=1){
                robot.setAgressif(true);
            }
        }

    }


    public Robot chooseVictimForCondottiere(List<Robot> bots , RobotRichardo robot) {
        Robot victim = bots.get(0);
        if (robot.thereIsA(CharactersType.CONDOTTIERE, robot.getAvailableCharacters())) {
            int numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
            for (Robot bot : bots) {
                if (bot.getNumberOfDistrictInCity() >= numberOfDistrictsInCity && bot.getCharacter() != CharactersType.CONDOTTIERE && !victim.hasEightDistrict()) {
                    victim = bot;
                    numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
                }
            }
        }
        return victim;


    }


    public void pickAgressif(List<CharactersType> availableCharacters, List<Robot> bots , RobotRichardo richardo) {
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() > richardo.getNumberOfDistrictInCity() + 2 || bot.getNumberOfDistrictInCity() > 5) {
                richardo.pickCharacterCard(availableCharacters, CharactersType.CONDOTTIERE);

                if(richardo.getCharacter() == CharactersType.CONDOTTIERE) return ;
            }
            else if (richardo.thereIsA(CharactersType.VOLEUR, availableCharacters) || (bot.getNumberOfDistrictInHand() <= 1 )){
                richardo.pickCharacterCard(availableCharacters , CharactersType.ASSASSIN);
                if(richardo.getCharacter() == CharactersType.ASSASSIN) return ;

            }


        }

        richardo.setCharacter(availableCharacters.get(0)) ;
        availableCharacters.remove(availableCharacters.get(0)) ;
    }


    public Robot chooseVictimForAssassin(List<Robot> bots, int numberOfTheCharacterToKill , RobotRichardo robot  ) {
        Robot victim = bots.get(0);
        if (robot.thereIsA(CharactersType.VOLEUR, robot.getAvailableCharacters())) {
            numberOfTheCharacterToKill = 2;
        } else if (robot.thereIsA(CharactersType.CONDOTTIERE, robot.getAvailableCharacters()) || hasMaxDistricts(bots , robot)) {
            numberOfTheCharacterToKill = 8;
        }
        for (Robot bot : bots) {
            if(bot.getNumberOfDistrictInHand() <= 1  ){
                victim = bot ;
                break ;
            }
            if (bot.getCharacter().getNumber() == numberOfTheCharacterToKill) {
                victim = bot;
                break;
            }
        }
        return victim;
    }



    public boolean hasMaxDistricts(List<Robot> bots , RobotRichardo robot) {
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() > robot.getNumberOfDistrictInCity()) {
                return false;
            }
        }
        return true ;
    }







    public boolean hasMaxGolds(List<Robot> bots , RobotRichardo robot){
        for (Robot bot : bots){
            if (bot.getGolds() > robot.getGolds()){
                return false ;
            }
        }
        return true ;
    }








}
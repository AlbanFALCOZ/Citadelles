package fr.cotedazur.univ.polytech.startingpoint.richardo;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.List;

public class StrategyAgressif {


    public StrategyAgressif() {
    }

    public void isAgressif(List<Robot> bots, RobotRichardo robot) {
        for (Robot bot : bots) {
            if ((bot.getNumberOfDistrictInCity() > robot.getNumberOfDistrictInCity() + 2 || bot.getNumberOfDistrictInCity() > 2) && (robot.thereIsA(CharactersType.VOLEUR, robot.getAvailableCharacters()))) {
                robot.setAgressif(true);
                break;
            } else if (robot.getNumberOfDistrictInCity() > 4) {
                robot.setAgressif(true);

            } else if (bot.getNumberOfDistrictInHand() <= 1) {
                robot.setAgressif(true);
            }
        }

    }


    public Robot chooseVictimForCondottiere(List<Robot> bots, RobotRichardo robot) {
        ActionOfBotDuringARound action = new ActionOfBotDuringARound(robot, true);
        Robot victim = bots.get(0);
        if (robot.thereIsA(CharactersType.CONDOTTIERE, robot.getAvailableCharacters())) {
            int numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
            for (Robot bot : bots) {
                if (bot.getNumberOfDistrictInCity() >= numberOfDistrictsInCity && bot.getCharacter() != CharactersType.CONDOTTIERE && !victim.hasEightDistrict()) {
                    victim = bot;
                    numberOfDistrictsInCity = victim.getNumberOfDistrictInCity();
                }
            }
            action.printVictimCondottiere(victim);
        }

        return victim;


    }


    public void pickAgressif(List<CharactersType> availableCharacters, List<Robot> bots, RobotRichardo richardo) {
        ActionOfBotDuringARound action = new ActionOfBotDuringARound(richardo, true);
        for (Robot bot : bots) {
            if ((bot.getNumberOfDistrictInCity() > richardo.getNumberOfDistrictInCity() + 2 || bot.getNumberOfDistrictInCity() > 5) && availableCharacters.contains(CharactersType.CONDOTTIERE)) {
                richardo.pickCharacterCard(availableCharacters, CharactersType.CONDOTTIERE);
                if (richardo.getCharacter() == CharactersType.CONDOTTIERE) {
                    action.printRichardPickCondottiere(bot);
                    return;
                }
            } else if ((richardo.thereIsA(CharactersType.VOLEUR, availableCharacters) || (bot.getNumberOfDistrictInHand() <= 1)) && availableCharacters.contains(CharactersType.ASSASSIN)) {
                richardo.pickCharacterCard(availableCharacters, CharactersType.ASSASSIN);
                if (richardo.getCharacter() == CharactersType.ASSASSIN) {
                    action.printRichardoPickAssassin();
                    return;
                }

            } else if ((availableCharacters.contains(CharactersType.EVEQUE)) && bot.getNumberOfDistrictInCity() > 5) {

                richardo.pickCharacterCard(availableCharacters, CharactersType.EVEQUE);
                if (richardo.getCharacter() == CharactersType.EVEQUE) {
                    action.printRichardPickEveque(bot);
                    return;
                }
            }

        }
        richardo.setCharacter(availableCharacters.get(0));
        availableCharacters.remove(availableCharacters.get(0));
    }


    public Robot chooseVictimForAssassin(List<Robot> bots, int numberOfTheCharacterToKill, RobotRichardo robot) {

        ActionOfBotDuringARound action = new ActionOfBotDuringARound(robot, true);

        Robot victim = bots.get(0);
        for (Robot bot : bots) {
            if (robot.thereIsA(CharactersType.VOLEUR, robot.getAvailableCharacters()) && robot.getGolds() > 4) {
                numberOfTheCharacterToKill = 2;
                action.printBotBonus();

            } else if (robot.thereIsA(CharactersType.CONDOTTIERE, robot.getAvailableCharacters()) || hasMaxDistricts(bots, robot)) {
                numberOfTheCharacterToKill = 8;

            } else {
                {
                    if (bot.getNumberOfDistrictInHand() <= 1 || robot.getNumberOfDistrictInHand() == 3) {
                        numberOfTheCharacterToKill = 3;
                    }
                }
            }
            if (bot.getCharacter().getNumber() == numberOfTheCharacterToKill) {
                victim = bot;
                break;
            }

        }
        action.printVictimAssassined(victim.getCharacter());
        return victim;


    }

    public boolean hasMaxDistricts(List<Robot> bots, RobotRichardo robot) {

        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() > robot.getNumberOfDistrictInCity()) {
                return false;
            }
        }

        return true;
    }

    public Robot chooseVictimForMagicien(List<Robot> bots, RobotRichardo robot) {
        ActionOfBotDuringARound action = new ActionOfBotDuringARound(robot, true);
        Robot victim = bots.get(0);
        int numberOfDistrictsInHand = victim.getNumberOfDistrictInHand();

        for (Robot bot : bots) {
            if (robot.getNumberOfDistrictInHand() <= 1 || bot.getNumberOfDistrictInHand() >= numberOfDistrictsInHand && bot.getCharacter() != CharactersType.MAGICIEN)
                victim = bot;
        }
        action.printVictimeForMagicien(victim);
        return victim;
    }

    public boolean hasMaxGolds(List<Robot> bots, RobotRichardo robot) {
        for (Robot bot : bots) {
            if (bot.getGolds() > robot.getGolds()) {
                return false;

            }
        }
        return true;
    }

}
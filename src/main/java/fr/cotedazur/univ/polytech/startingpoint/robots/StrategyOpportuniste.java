package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.cotedazur.univ.polytech.startingpoint.robots.RobotRichardo.*;


public class StrategyOpportuniste {

    public void pickOpportuniste(RobotRichardo robot) {
        Map<CharactersType, Integer> characterCounts = new HashMap<>();
        characterCounts.put(CharactersType.ROI, robot.countDistrictsByType(NOBLE) + robot.countDistrictsInHandByType(NOBLE));
        characterCounts.put(CharactersType.EVEQUE, robot.countDistrictsByType(RELIGIOUS) + robot.countDistrictsInHandByType(RELIGIOUS));
        characterCounts.put(CharactersType.CONDOTTIERE, robot.countDistrictsByType(MILITARY) + robot.countDistrictsInHandByType(MILITARY));
        characterCounts.put(CharactersType.MARCHAND, robot.countDistrictsByType(COMMERCIAL) + robot.countDistrictsInHandByType(COMMERCIAL));

        List<CharactersType> priorityOrder = characterCounts.entrySet().stream()
                .sorted(Map.Entry.<CharactersType, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        CharactersType chosenCharacter = null;
        List<CharactersType> availableCharacters = robot.getAvailableCharacters();

        for (CharactersType character : priorityOrder) {
            if (availableCharacters.contains(character)) {
                chosenCharacter = character;
                availableCharacters.remove(character);
                break;
            }
        }

        if (chosenCharacter == null && !availableCharacters.isEmpty()) {

            chosenCharacter = availableCharacters.get(0);
            availableCharacters.remove(0);
        }

        robot.setCharacter(chosenCharacter);
    }

}

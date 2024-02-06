package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import fr.cotedazur.univ.polytech.startingpoint.game.Round;

import java.util.*;
import java.util.stream.Collectors;

public class RobotAnalyzer extends Robot {
    private ActionOfBotDuringARound action;
    private Map<String, List<CharactersType>> characterHistory;
    private Map<String, List<DistrictsType>> buildingHistory;

    public RobotAnalyzer(String name) {
        super(name);
        this.action = new ActionOfBotDuringARound(this, true);
        this.characterHistory = new HashMap<>();
        this.buildingHistory = new HashMap<>();
    }

    @Override
    public String tryBuild() {
        List<DistrictsType> districtsInHand = new ArrayList<>(getDistrictInHand());
        Set<String> uniqueDistrictTypesInCity = getCity().stream().map(DistrictsType::getType).collect(Collectors.toSet());
        String builtDistrictName = "nothing";

        districtsInHand.sort(Comparator.comparingInt(DistrictsType::getScore).reversed().thenComparingInt(DistrictsType::getCost));

        for (DistrictsType district : districtsInHand) {
            if (district.getCost() <= getGolds()) {
                if (!uniqueDistrictTypesInCity.contains(district.getType())) {
                    buildDistrict(district);
                    builtDistrictName = district.getName();
                    break;
                }
            }
        }

        if (builtDistrictName.equals("nothing")) {
            for (DistrictsType district : districtsInHand) {
                if (district.getCost() <= getGolds()) {
                    buildDistrict(district);
                    builtDistrictName = district.getName();
                    break;
                }
            }
        }

        return builtDistrictName.equals("nothing") ? "nothing" : "a new " + builtDistrictName;
    }

    private void buildDistrict(DistrictsType district) {
        district.powerOfDistrict(this, 1);
        getCity().add(district);
        setGolds(getGolds() - district.getCost());
        getDistrictInHand().remove(district);
        action.printBuildingOfBot("a new " + district.getName());
    }


    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        Set<String> uniqueDistrictTypesInCity = getCity().stream().map(DistrictsType::getType).collect(Collectors.toSet());

        listDistrict.sort(Comparator.comparingInt(DistrictsType::getScore).reversed().thenComparingInt(DistrictsType::getCost));

        List<DistrictsType> chosenDistricts = new ArrayList<>();
        int totalCost = 0;

        for (DistrictsType district : listDistrict) {
            if (totalCost + district.getCost() <= getGolds() && chosenDistricts.size() < getNumberOfCardsChosen()) {
                if (!uniqueDistrictTypesInCity.contains(district.getType())) {
                    chosenDistricts.add(district);
                    totalCost += district.getCost();
                }
            }
        }

        for (DistrictsType district : listDistrict) {
            if (!chosenDistricts.contains(district) && totalCost + district.getCost() <= getGolds() && chosenDistricts.size() < getNumberOfCardsChosen()) {
                chosenDistricts.add(district);
                totalCost += district.getCost();
            }
        }

        for (DistrictsType district : listDistrict) {
            if (!chosenDistricts.contains(district)) {
                deck.addDistrictToDeck(district);
            }
        }

        return chosenDistricts;
    }

    @Override
    public int generateChoice() {
        List<DistrictsType> buildableDistricts = getDistrictInHand().stream()
                .filter(district -> district.getCost() <= getGolds())
                .collect(Collectors.toList());

        if (buildableDistricts.isEmpty()) {
            return 1;//ressources
        }

        Optional<DistrictsType> optionalDistrict = buildableDistricts.stream()
                .max(Comparator.comparingDouble(district -> ((double) district.getScore()) / district.getCost()));

        if (optionalDistrict.isPresent()) {
            DistrictsType districtToBuild = optionalDistrict.get();
            if (districtToBuild.getScore() > 2) {
                return 0;
            }
        }

        return 1;
    }

    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        Map<CharactersType, Integer> opponentCharacterPredictions = new HashMap<>();
        Map<DistrictsType, Integer> opponentBuildingPredictions = new HashMap<>();

        Map<String, Integer> opponentGolds = new HashMap<>();
        Map<String, Integer> opponentHandSizes = new HashMap<>();

        for (Robot bot : bots) {
            if (!bot.getName().equals(this.getName())) {//aux adversaires
                CharactersType predictedCharacter = predictOpponentNextCharacter(bot.getName());
                DistrictsType predictedBuilding = predictOpponentNextBuilding(bot.getName());

                // compte les prédictions pour voir quels persoo/bat sont les plus probables
                if (predictedCharacter != null) {
                    opponentCharacterPredictions.put(predictedCharacter,
                            opponentCharacterPredictions.getOrDefault(predictedCharacter, 0) + 1);
                }
                if (predictedBuilding != null) {
                    opponentBuildingPredictions.put(predictedBuilding,
                            opponentBuildingPredictions.getOrDefault(predictedBuilding, 0) + 1);
                }

                // ressources des adversaires
                opponentGolds.put(bot.getName(), bot.getGolds());
                opponentHandSizes.put(bot.getName(), bot.getDistrictInHand().size());
            }
        }
        CharactersType chosenCharacter = null;

        for (Map.Entry<CharactersType, Integer> entry : opponentCharacterPredictions.entrySet()) {
            CharactersType predictedCharacter = entry.getKey();
            Integer predictionCount = entry.getValue();


            if (predictionCount > 1) { // si plus d'un adversaire prédit pour choisir ce perso : contrer
                switch (predictedCharacter.getType()) {
                    case "Roi":
                        if (availableCharacters.contains(CharactersType.ASSASSIN)) {
                            chosenCharacter = CharactersType.ASSASSIN;
                        }
                        break;
                    case "Marchand":
                        if (availableCharacters.contains(CharactersType.VOLEUR)) {
                            chosenCharacter = CharactersType.VOLEUR;
                        }
                        break;
                    case "Architecte":
                        //condotiere
                        if (opponentGolds.values().stream().anyMatch(gold -> gold > 3) && availableCharacters.contains(CharactersType.VOLEUR)) {
                            chosenCharacter = CharactersType.VOLEUR;
                        }
                        break;
                }
            }

            if (chosenCharacter != null) break;
        }


        if (chosenCharacter == null && !availableCharacters.isEmpty()) {
            chosenCharacter = availableCharacters.get(0);
        }

        setCharacter(chosenCharacter);
    }





    public CharactersType predictOpponentNextCharacter(String botName) {
        List<CharactersType> characterHistory = this.characterHistory.get(botName);

        if (characterHistory == null || characterHistory.isEmpty()) {
            return null;
        }

        //count fréquence des choix de perso
        Map<CharactersType, Integer> characterFrequency = new HashMap<>();
        for (CharactersType character : characterHistory) {
            characterFrequency.put(character, characterFrequency.getOrDefault(character, 0) + 1);
        }

        // le perso le plus souvent choisi
        return Collections.max(characterFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public DistrictsType predictOpponentNextBuilding(String botName) {
        List<DistrictsType> buildingHistory = this.buildingHistory.get(botName);

        if (buildingHistory == null || buildingHistory.isEmpty()) {
            return null;
        }

        // count fréquence de constructions
        Map<DistrictsType, Integer> buildingFrequency = new HashMap<>();
        for (DistrictsType building : buildingHistory) {
            buildingFrequency.put(building, buildingFrequency.getOrDefault(building, 0) + 1);
        }

        // le bat le plus souvent construit
        return Collections.max(buildingFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

}
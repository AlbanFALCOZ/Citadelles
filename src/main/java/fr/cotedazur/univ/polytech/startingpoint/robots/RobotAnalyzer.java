package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.Colors;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.*;
import java.util.stream.Collectors;

public class RobotAnalyzer extends Robot {
    private ActionOfBotDuringARound action;
    private Map<String, List<CharactersType>> characterHistory;
    private Map<String, List<DistrictsType>> buildingHistory;
    private List<Robot> allPlayers;

    public RobotAnalyzer(String name, List<Robot> allPlayers) {
        super(name);
        this.action = new ActionOfBotDuringARound(this, true);
        this.characterHistory = new HashMap<>();
        this.buildingHistory = new HashMap<>();
        this.allPlayers = allPlayers;

    }


    @Override
    public String tryBuild() {
        List<DistrictsType> districtsInHand = new ArrayList<>(getDistrictInHand());
        Set<String> uniqueDistrictTypesInCity = getCity().stream().map(DistrictsType::getType).collect(Collectors.toSet());
        String builtDistrictName = "nothing";

        // Priorité aux districts spéciaux
        List<DistrictsType> specialDistricts = districtsInHand.stream()
                .filter(d -> d.getColor() == Colors.PURPLE && d.getCost() <= getGolds())
                .collect(Collectors.toList());
        if (!specialDistricts.isEmpty()) {
            builtDistrictName = buildFirstAvailableDistrict(specialDistricts, uniqueDistrictTypesInCity);
        }

        // Bloquer la stratégie de l'adversaire
        if ("nothing".equals(builtDistrictName)) {
            String mostBuiltTypeByOpponents = predictMostBuiltDistrictTypeByOpponents();
            List<DistrictsType> blockingDistricts = districtsInHand.stream()
                    .filter(d -> d.getType().equals(mostBuiltTypeByOpponents) && d.getCost() <= getGolds())
                    .collect(Collectors.toList());
            if (!blockingDistricts.isEmpty()) {
                builtDistrictName = buildFirstAvailableDistrict(blockingDistricts, uniqueDistrictTypesInCity);
            }
        }

        // Construction du district le plus rentable si aucune des options ci-dessus n'est possible
        if ("nothing".equals(builtDistrictName)) {
            for (DistrictsType district : districtsInHand) {
                if (district.getCost() <= getGolds() && !uniqueDistrictTypesInCity.contains(district.getType())) {
                    buildDistrict(district);
                    builtDistrictName = district.getName();
                    break; // Arrêtez après avoir construit le premier district disponible dans les limites de l'or.
                }
            }
        }

        return "nothing".equals(builtDistrictName) ? "nothing" : "a new " + builtDistrictName;
    }


    private String buildFirstAvailableDistrict(List<DistrictsType> districts, Set<String> uniqueDistrictTypesInCity) {
        for (DistrictsType district : districts) {
            if (!uniqueDistrictTypesInCity.contains(district.getType())) {
                buildDistrict(district);
                return district.getName();
            }
        }
        return "nothing";
    }


    private String predictMostBuiltDistrictTypeByOpponents() {
        Map<String, Integer> typeFrequency = new HashMap<>();

        for (Map.Entry<String, List<DistrictsType>> entry : buildingHistory.entrySet()) {

            if (entry.getKey().equals(this.getName())) {
                continue;
            }

            for (DistrictsType district : entry.getValue()) {
                String districtType = district.getType();
                typeFrequency.put(districtType, typeFrequency.getOrDefault(districtType, 0) + 1);
            }
        }

        // le type de district le plus fréquemment construit
        return typeFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    private void buildDistrict(DistrictsType district) {
        getCity().add(district);
        setGolds(getGolds() - district.getCost());
        getDistrictInHand().remove(district);
        action.printBuildingOfBot("a new " + district.getName());
    }


    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        Set<String> uniqueDistrictTypesInCity = getCity().stream().map(DistrictsType::getType).collect(Collectors.toSet());
        Map<String, Integer> districtTypeFrequencyInCity = getCity().stream().collect(Collectors.groupingBy(DistrictsType::getType, Collectors.summingInt(e -> 1)));

        //trier les districts par score coût et fréquence dans la ville pour diversité
        listDistrict.sort(Comparator.comparingInt((DistrictsType d) -> districtTypeFrequencyInCity.getOrDefault(d.getType(), 0))
                .thenComparingInt(DistrictsType::getScore).reversed()
                .thenComparingInt(DistrictsType::getCost));

        List<DistrictsType> chosenDistricts = new ArrayList<>();
        int totalCost = 0;

        //strat de choix de carte basée sur le coût, la diversité et perturbation des adversaires
        for (DistrictsType district : listDistrict) {
            if (totalCost + district.getCost() <= getGolds() && chosenDistricts.size() < getNumberOfCardsChosen()) {
                if (!uniqueDistrictTypesInCity.contains(district.getType()) || shouldPickBasedOnAdversaries(district)) {
                    chosenDistricts.add(district);
                    totalCost += district.getCost();
                }
            }
        }

        //si les districts choisis ne sont pas suffisants, choisir par coût et score
        if (chosenDistricts.size() < getNumberOfCardsChosen()) {
            for (DistrictsType district : listDistrict) {
                if (!chosenDistricts.contains(district) && totalCost + district.getCost() <= getGolds()) {
                    chosenDistricts.add(district);
                    totalCost += district.getCost();
                    if (chosenDistricts.size() >= getNumberOfCardsChosen()) break;
                }
            }
        }

        //ajouter les districts non choisis au deck
        listDistrict.stream().filter(district -> !chosenDistricts.contains(district)).forEach(deck::addDistrictToDeck);

        return chosenDistricts;
    }


    private boolean shouldPickBasedOnAdversaries(DistrictsType district) {
        //obtenir seulement les adversaires
        List<Robot> opponents = allPlayers.stream()
                .filter(player -> !player.equals(this))
                .collect(Collectors.toList());

        for (Robot opponent : opponents) {
            //chaque Robot a une méthode getCity() qui retourne les districts construits
            long count = opponent.getCity().stream()
                    .filter(d -> d.getType().equals(district.getType()))
                    .count();

            if (count > 1) { //adversaire construit ce type + une fois
                return true; //a bloquer
            }
        }

        return false;
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

        CharactersType chosenCharacter = null;
        Map<Integer, CharactersType> characterFrequency = new HashMap<>();

        for (Robot bot : bots) {
            characterFrequency.put(countOpponentNextCharacter(bot.getName()), predictOpponentNextCharacter(bot.getName()));
        }

        CharactersType maxCharacterFrequency = Collections.max(characterFrequency.entrySet(), Map.Entry.comparingByKey()).getValue();

        System.out.println(characterFrequency);
        System.out.println(maxCharacterFrequency);

        if (maxCharacterFrequency!=null) {
            switch (maxCharacterFrequency) {
                case ROI:
                    if (availableCharacters.contains(CharactersType.ASSASSIN)) {
                        chosenCharacter = CharactersType.ASSASSIN;
                        availableCharacters.remove(chosenCharacter);

                    }
                    break;
                case MARCHAND:
                    if (availableCharacters.contains(CharactersType.VOLEUR)) {
                        chosenCharacter = CharactersType.VOLEUR;
                        availableCharacters.remove(chosenCharacter);
                    }
                    break;
                case ARCHITECTE:
                    if (availableCharacters.contains(CharactersType.CONDOTTIERE)) {
                        chosenCharacter = CharactersType.CONDOTTIERE;
                        availableCharacters.remove(chosenCharacter);

                    } else if (availableCharacters.contains(CharactersType.VOLEUR)) {
                        chosenCharacter = CharactersType.VOLEUR;
                        availableCharacters.remove(chosenCharacter);

                    }
                    break;
                default:
                    chosenCharacter = availableCharacters.get(0);
                    availableCharacters.remove(chosenCharacter);
                    break;
            }
        }

        if (chosenCharacter == null) {
            chosenCharacter = availableCharacters.get(0);
            availableCharacters.remove(0);
        }
        setCharacter(chosenCharacter);

    }


    public CharactersType predictOpponentNextCharacter(String botName) {
        List<CharactersType> characterHistory = this.getCharacterHistory().get(botName);

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

    public Integer countOpponentNextCharacter(String botName) {
        List<CharactersType> characterHistory = this.getCharacterHistory().get(botName);

        if (characterHistory == null || characterHistory.isEmpty()) {
            return null;
        }

        //count fréquence des choix de perso
        Map<CharactersType, Integer> characterFrequency = new HashMap<>();
        for (CharactersType character : characterHistory) {
            characterFrequency.put(character, characterFrequency.getOrDefault(character, 0) + 1);
        }

        // le perso le plus souvent choisi
        return Collections.max(characterFrequency.entrySet(), Map.Entry.comparingByValue()).getValue();
    }

    /*public String predictOpponentNextBuilding(String botName) {
        List<DistrictsType> buildingHistory = this.getBuildingHistory().get(botName);

        if (buildingHistory == null || buildingHistory.isEmpty()) {
            return null;
        }

        // count fréquence de constructions
        Map<String, Integer> buildingFrequency = new HashMap<>();
        for (DistrictsType building : buildingHistory) {
            buildingFrequency.put(building.getType(), buildingFrequency.getOrDefault(building.getType(), 0) + 1);
        }

        // le bat le plus souvent construit
        return Collections.max(buildingFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }*/
    
}
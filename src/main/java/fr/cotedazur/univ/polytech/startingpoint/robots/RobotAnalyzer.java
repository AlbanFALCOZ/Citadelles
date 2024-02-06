package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

import java.util.*;
import java.util.stream.Collectors;

public class RobotAnalyzer extends Robot {
    private ActionOfBotDuringARound action;

    public RobotAnalyzer(String name) {
        super(name);
        this.action = new ActionOfBotDuringARound(this, true);
    }

    @Override
    public String tryBuild() {
        List<DistrictsType> districtsInHand = new ArrayList<>(getDistrictInHand());
        Set<String> uniqueDistrictTypesInCity = getCity().stream().map(DistrictsType::getType).collect(Collectors.toSet());
        String builtDistrictName = "nothing";

        //trier districts dans la main par score (décroissant) puis par coût (croissant)
        districtsInHand.sort(Comparator.comparingInt(DistrictsType::getScore).reversed().thenComparingInt(DistrictsType::getCost));

        for (DistrictsType district : districtsInHand) {
            //si le robot a assez d'or pour construire le district
            if (district.getCost() <= getGolds()) {
                // Priorité aux districts dont le type n'est pas encore présent dans la ville
                if (!uniqueDistrictTypesInCity.contains(district.getType())) {
                    buildDistrict(district);
                    builtDistrictName = district.getName();
                    break;
                }
            }
        }

        // si aucun district unique construit et reste de l'or, construire le district le plus rentable
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
        //obtient types uniques de districts dans la ville
        Set<String> uniqueDistrictTypesInCity = getCity().stream().map(DistrictsType::getType).collect(Collectors.toSet());

        //trie les districts tirés par leur score (décroissant) puis par coût (croissant)
        listDistrict.sort(Comparator.comparingInt(DistrictsType::getScore).reversed().thenComparingInt(DistrictsType::getCost));

        List<DistrictsType> chosenDistricts = new ArrayList<>();
        int totalCost = 0;

        for (DistrictsType district : listDistrict) {
            if (totalCost + district.getCost() <= getGolds() && chosenDistricts.size() < getNumberOfCardsChosen()) {
                //priorité aux districts dont le type n'est pas encore présent dans la ville
                if (!uniqueDistrictTypesInCity.contains(district.getType())) {
                    chosenDistricts.add(district);
                    totalCost += district.getCost();
                }
            }
        }

        //si pas assez de districts uniques choisis: choisir les meilleurs parmi les restants
        for (DistrictsType district : listDistrict) {
            if (!chosenDistricts.contains(district) && totalCost + district.getCost() <= getGolds() && chosenDistricts.size() < getNumberOfCardsChosen()) {
                chosenDistricts.add(district);
                totalCost += district.getCost();
            }
        }

        //remettre non choisis dans le deck
        for (DistrictsType district : listDistrict) {
            if (!chosenDistricts.contains(district)) {
                deck.addDistrictToDeck(district);
            }
        }

        return chosenDistricts;
    }

    @Override
    public int generateChoice() {
        // Strat : si le robot peut construire un district rentable, il le fait
        // Sinon: il prend des ressources

        //obtenir les district dans la main qui peuvent être construits avec l'or actuel
        List<DistrictsType> buildableDistricts = getDistrictInHand().stream()
                .filter(district -> district.getCost() <= getGolds())
                .collect(Collectors.toList());

        //prendre des ressources.
        if (buildableDistricts.isEmpty()) {
            return 1;
        }

        //si peut construire un district, décider lequel construire:
        //prend district avec le score le plus élevé par coût
        Optional<DistrictsType> optionalDistrict = buildableDistricts.stream()
                .max(Comparator.comparingDouble(district -> ((double) district.getScore()) / district.getCost()));

        if (optionalDistrict.isPresent()) {
            DistrictsType districtToBuild = optionalDistrict.get();
            // district choisi a un bon avantage, le construire
            if (districtToBuild.getScore() > 2) { // Le seuil de 2 est arbitraire
                return 0; // 0 pour construire un district
            }
        }

        //Si aucun district n'offre un avantage significatif, prendre des ressources
        return 1; // 1 pour prendre des ressources
    }

    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        //analyser les robots adversaire et choisir un perso
        Map<String, Integer> districtTypeCounts = countDistrictTypes(bots);
        chooseCharacterBasedOnAnalysis(availableCharacters, districtTypeCounts);
    }

    private Map<String, Integer> countDistrictTypes(List<Robot> bots) {
        // count les types de quartier parmi tous les robots
        Map<String, Integer> districtTypeCounts = new HashMap<>();
        for (Robot bot : bots) {
            for (DistrictsType district : bot.getCity()) {
                String type = district.getType();
                districtTypeCounts.put(type, districtTypeCounts.getOrDefault(type, 0) + 1);
            }
        }
        return districtTypeCounts;
    }

    private void chooseCharacterBasedOnAnalysis(List<CharactersType> availableCharacters, Map<String, Integer> districtTypeCounts) {
        //type de district le plus commun dans la ville du robot
        String mostCommonDistrictType = Collections.max(districtTypeCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        //type de district le moins commun
        String leastCommonDistrictType = Collections.min(districtTypeCounts.entrySet(), Map.Entry.comparingByValue()).getKey();

        CharactersType chosenCharacter = null;

        //logique de choix perso basé sur les districts les plus/moins communs
        for (CharactersType character : availableCharacters) {
            if (character.getType().equals(mostCommonDistrictType) && districtTypeCounts.get(mostCommonDistrictType) > 2) {
                // priorise perso qui correspondent au type de district le plus commun pour maximiser le gain
                chosenCharacter = character;
                break;
            } else if (character.getType().equals(leastCommonDistrictType) && districtTypeCounts.get(leastCommonDistrictType) < 2) {
                // Choisir un personnage qui pourrait combler le manque de diversité
                chosenCharacter = character;
                break;
            }
        }

        // si aucun pers choisi selon la strat, choisir le premier personnage dispo
        if (chosenCharacter == null && !availableCharacters.isEmpty()) {
            chosenCharacter = availableCharacters.get(0);
        }

        setCharacter(chosenCharacter);
    }

}

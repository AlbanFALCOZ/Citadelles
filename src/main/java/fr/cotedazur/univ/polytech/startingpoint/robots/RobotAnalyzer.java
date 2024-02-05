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
                    // Construire
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
        // ...
        return listDistrict;
    }

    @Override
    public int generateChoice() {
        // ...
        return 0;
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
        //choisir un personnage basé sur l'analyse des types de quartier
        // ...
    }
}

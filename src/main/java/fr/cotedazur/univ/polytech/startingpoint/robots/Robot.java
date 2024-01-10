package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.*;


/**
 * cette classe représente un robot
 */
public class Robot {

    private String name;
    private int score;
    private int golds;
    private int numberOfCardsDrawn = 2;
    private int numberOfCardsChosen = 1;
    private DeckDistrict district = new DeckDistrict();
    private List<DistrictsType> districtInHand;
    private CharactersType character;
    public static final String RESET = "\u001B[0m";

    private ArrayList<DistrictsType> city;

    private boolean hasCrown;
    private Robot target;


    /**
     * @param name le nom du robot
     *             Constructeur de la classe Robot
     */
    public Robot(String name) {
        this.name = name;
        this.score = 0;
        this.districtInHand = new ArrayList<>();
        this.golds = 2;
        this.character = null;
        this.city = new ArrayList<>();
        this.hasCrown = false;
    }

    /**
     * @return le RESET qui permet d'annuler les couleurs
     */
    public String getRESET(){
        return RESET;
    }

    /**
     * @return le nom du robot
     */
    public String getName() {
        return name;
    }

    /**
     * @return les golds du robot
     */
    public int getGolds() {
        return golds;
    }


    /**
     * @param golds les golds du robot
     *              cette méthode permet de modifier les golds du robot
     */
    public void setGolds(int golds) {
        this.golds = golds;
    }

    /**
     * @return le nombre de districts tirés
     */
    public int getNumberOfCardsDrawn() {
        return numberOfCardsDrawn;
    }

    /**
     * @param numberOfCardsDrawn le nombre de districts tirés
     *                           cette méthode permet de modifier le nombre de cartes tirés
     */
    public void setNumberOfCardsDrawn(int numberOfCardsDrawn) {
        this.numberOfCardsDrawn = numberOfCardsDrawn;
    }

    /**
     * @return le nombre de districts choisis
     * cette méthode permet de modifier le nombre de districts choisis
     */
    public int getNumberOfCardsChosen() {
        return numberOfCardsChosen;
    }

    /**
     * @param numberOfCardsChosen le nombre de cartes choisies
     * cette méthode permet de modifier le nombre de cartes choisies
     */
    public void setNumberOfCardsChosen(int numberOfCardsChosen) {
        this.numberOfCardsChosen = numberOfCardsChosen;
    }

    /**
     * @param golds les golds du robot
     *              cette méthode permet d'ajouter des golds au robot
     */
    public void addGold(int golds) {
        this.golds += golds;
    }


    /**
     * @param character le personnage du robot
     *                  cette méthode permet de modifier le personnage du robot
     */
    public void setCharacter(CharactersType character) {
        this.character = character;
    }


    /**
     * @return le personnage du robot
     * cette méthode permet de retourner le personnage du robot
     */
    public CharactersType getCharacter() {
        return character;
    }

    /**
     * @return la liste des districts construits dans la cité
     */
    public ArrayList<DistrictsType> getCity() {
        return city;
    }

    /**
     * @param hasCrown l'attribut disant si le robot a la couronne ou non
     *                cette méthode permet de modifier l'attribut disant si le robot a la couronne ou non
     */
    public void setHasCrown(boolean hasCrown) {
        this.hasCrown = hasCrown;

    }

    /**
     * @return le district construit
     * cette méthode permet de construire un district
     */
    public String tryBuild() {
        for (int i = 0; i < districtInHand.size(); i++) {
            DistrictsType district = districtInHand.get(i);
            if (district.getCost() <= this.getGolds()) {
                district.powerOfDistrict(this);
                city.add(district);
                setGolds(getGolds() - district.getCost());
                districtInHand.remove(i);
                return "a new " + district.name();
            }
        }
        return "nothing";
    }


    /**
     * @param district le district à ajouter
     *                 cette méthode permet d'ajouter un district à la main du robot
     */
    public void addDistrict(DistrictsType district) {

        this.districtInHand.add(district);

    }

    /**
     * @return la liste des districts dans la main du robot
     * cette méthode permet de retourner la liste des districts dans la main du robot
     */
    public int getNumberOfDistrictInHand() {
        return districtInHand.size();
    }

    /**
     * @return la taille de la cité
     */
    public int getNumberOfDistrictInCity() {
        return city.size();
    }

    /**
     * @param showColor un booléen qui permet de savoir si on affiche les couleurs ou non
     * @return le statut du robot
     */
    public String statusOfPlayer(boolean showColor) {
        String endColor = "";
        String colorCharacter = "";
        if (showColor) {
            colorCharacter = character.getColor();
            endColor = RESET;
        }
        String status = endColor + "[Status of " + this.name + " : role (" + colorCharacter + this.character.getRole() + endColor + "), " + this.golds + " golds, hand {";
        status += getString(showColor, districtInHand) + "}, city {" + getString(showColor, city) + "}]";
        return status;
    }

    /**
     * @return le statut du robot
     * cette méthode permet de retourner le statut du robot en affichant les couleurs
     */
    public String statusOfPlayer() {
        return statusOfPlayer(true);
    }

    /**
     * @param showColor un booléen qui permet de savoir si on affiche les couleurs ou non
     * @param listDistrict la liste des districts
     * @return la liste des districts sous forme de chaîne de caractères
     */
    private String getString(boolean showColor, List<DistrictsType> listDistrict) {
        String returedString = "";
        String color;
        String endColor;
        for (int numberOfDistrictInCity = 0; numberOfDistrictInCity < listDistrict.size(); numberOfDistrictInCity++) {
            if (showColor) {
                color = listDistrict.get(numberOfDistrictInCity).getColor();
                endColor = RESET;
            } else {
                color = endColor = "";
            }
            returedString += "(" + color + listDistrict.get(numberOfDistrictInCity).getName() + "," + listDistrict.get(numberOfDistrictInCity).getCost() + endColor + ")";
        }
        return returedString;
    }

    /**
     * @param listDistrict la liste des districts
     * @return la liste des districts après avoir choisi les districts à construire
     */
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict) {
        listDistrict.sort(compareByCost().reversed());
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int indice = 0;
        int i = 0;
        while (i < listDistrict.size()) {
            if (listDistrict.get(i).getCost() - costOfDistrictToBeBuilt <= golds) {
                costOfDistrictToBeBuilt += listDistrict.get(i).getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
                i--;
                indice++;
                if (indice == numberOfCardsChosen) break;

            }
            i++;
        }
        while (listDistrictToBuild.size() < numberOfCardsChosen) listDistrictToBuild.add(listDistrict.remove(listDistrict.size()-1));


        for (DistrictsType districtNonChosen: listDistrict) {
            district.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }

    /**
     * @return la liste des districts piochés
     */
    public List<DistrictsType> pickListOfDistrict(){
        List<DistrictsType> listDistrict = new ArrayList<>();
        for (int i = 0; i < numberOfCardsDrawn; i++) {
            DistrictsType card = district.getDistrictsInDeck();
            listDistrict.add(card);
        }
        return listDistrict;
    }

    /**
     * @return la comparaison des districts par coût
     */
    private Comparator<DistrictsType> compareByCost() {
        return Comparator.comparingInt(DistrictsType::getCost);
    }

    /**
     * @return le score du robot
     */
    public int calculateScore() {
        int score = 0;
        for (DistrictsType district : city) {
            score += district.getScore();
        }
        return score;
    }


    /**
     * @return true si le robot a la couronne, false sinon
     */
    public boolean getHasCrown() {
        return hasCrown;
    }


    /**
     * @return le nombre de districts construits par le robot par type
     */
    public int countBuildingsByType() {
        int count = 0;

        for (DistrictsType building : city) {

            if(building.getType().equals(this.character.getType()) || building.getType().equals("ecole")){
                count++;
            }

        }
        return count;
    }


    /**
     * @return le nombre de golds gagnés par le robot en fonction du type de bâtiment construit
     */
    public int winGoldsByTypeOfBuildings() {
    int oldGolds = this.getGolds();
        addGold(countBuildingsByType());
        return this.getGolds() - oldGolds;
    }


    /**
     * @param role le rôle du personnage
     * @return true si le robot a le personnage donné en paramètre, false sinon
     */
    public boolean isCharacter(String role){
        if (this.getCharacter().getRole().equals(role)) {
            return true;
        }
        return false;
    }


    /**
     * @param otherBots la liste des autres robots
     *                  cette méthode permet de choisir une cible pour le voleur
     */
    public void chooseTarget(List<Robot> otherBots) {
        //logique pour choisir une cible (pour l'instant aléatoirement)
        Random random = new Random();
        Robot target = otherBots.get(random.nextInt(otherBots.size()));
        this.target = target;
    }

    /**
     * @return la cible du voleur
     */
    public Robot getTarget() {
        return target;
    }
}


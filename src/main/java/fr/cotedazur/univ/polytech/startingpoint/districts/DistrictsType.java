package fr.cotedazur.univ.polytech.startingpoint.districts;

import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;


/**
 * Cet enum englobe toutes les cartes quartiers dont on aura besoin
 * le long de la partie
 * les quartiers posséde , un nom , un cout , une couleur et un score
 * les couleurs sont null car elles ne seront pas prise en considération
 * dans cette première verion du jeu .
 * Version 0.1 du jeu citadelle.
 */
public enum DistrictsType {
    MANOIR("Manoir", 3, "\u001B[33m", 3, "noble"),
    CHATEAU("Château", 4, "\u001B[33m", 4, "noble"),
    PALAIS("Palais", 5, "\u001B[33m", 5, "noble"),

    // RELIGION
    TEMPLE("Temple", 1, "\u001B[34m", 1, "religieux"),
    EGLISE("Église", 2, "\u001B[34m", 2, "religieux"),
    MONASTERE("Monastère", 3, "\u001B[34m", 3, "religieux"),
    CATHEDRALE("Cathédrale", 5, "\u001B[34m", 5, "religieux"),
    // TRADE DISTRICTS
    TAVERNE("Taverne", 1, "\u001B[32m", 1, "marchand"),
    ECHOPPE("Échoppe", 2, "\u001B[32m", 2, "marchand"),
    MARCHE("Marché", 2, "\u001B[32m", 2, "marchand"),
    COMPTOIR("Comptoir", 3, "\u001B[32m", 3, "marchand"),
    PORT("Port", 4, "\u001B[32m", 4, "marchand"),
    HOTEL_DE_VILLE("Hôtel de ville", 5, "\u001B[32m", 5, "marchand"),


    // GUERRE
    TOUR_DE_GUET("Tour de guet", 1, "\u001B[31m", 1, "militaire"),
    PRISON("Prison", 2, "\u001B[31m", 2, "militaire"),
    CASERNE("Caserne", 3, "\u001B[31m", 3, "militaire"),
    FORTRESSE("Forteresse", 5, "\u001B[31m", 5, "militaire"),


    // SPECIAL
    COURT_DES_MIRACLES("Cour des miracles", 2, "\u001B[35m", 2, "default"),
    DONJON("Donjon", 3, "\u001B[35m", 3, "default"),
    BIBLIOTHEQUE("Bibliothèque", 6, "\u001B[35m", 6, "default"),
    ECOLE_DE_MAGIE("École de magie", 6, "\u001B[35m", 6, "ecole"),
    LABORATOIRE("Laboratoire", 5, "\u001B[35m", 5, "default"),
    MANUFACTURE("Manufacture", 5, "\u001B[35m", 5, "default"),
    OBSERVATOIRE("Observatoire", 5, "\u001B[35m", 5, "default"),
    CIMETIERE("Cimetière", 5, "\u001B[35m", 5, "default"),
    UNIVERSITE("Université", 6, "\u001B[35m", 8, "default"),
    DRACOPORT("Dracoport", 6, "\u001B[35m", 8, "default");


    int cost;
    String name;
    String color;
    String colorReset = "\u001B[0m";
    int score;
    String type;

    /**
     * Il s'agit du constructeur de l'enum DistrictsType
     *
     * @param name  nom du quartier
     * @param cost  cout du quartier
     * @param color couleur du quartier
     * @param score score du quartier
     * @param type  type du quartier
     */
    DistrictsType(String name, int cost, String color, int score, String type) {
        this.cost = cost;
        this.name = name;
        this.color = color;
        this.score = score;
        this.type = type;
    }

    /**
     * @return le cout du quartier
     */
    public int getCost() {
        return cost;
    }

    /**
     * @return le nom du quartier
     */
    public String getName() {
        return name;
    }

    /**
     * @return la couleur du quartier
     */
    public String getColor() {
        return color;
    }

    /**
     * @return le score du quartier
     */
    public int getScore() {
        return score;
    }

    /**
     * @return la couleur de reset
     */
    public String getColorReset() {
        return colorReset;
    }

    /**
     * @param player le joueur qui possède le quartier
     *               on vérifie si le quartier est un quartier spécial c'est à dire un quartier qui a un pouvoir
     */
    public void powerOfDistrict(Robot player) {
        if (name.equals("Observatoire")) {
            player.setNumberOfCardsDrawn(player.getNumberOfCardsDrawn() + 1);
        }
        if (name.equals("Bibliothèque")) {
            if (player.getNumberOfCardsChosen() < 2) player.setNumberOfCardsChosen(player.getNumberOfCardsChosen() + 1);
        }

    }


    /**
     * @return le nom et le cout du quartier
     */
    public String toString() {
        return "(" + getName() + ", " + getCost() + ")";
    }

    /**
     * @return le type du quartier
     */
    public String getType() {
        return type;
    }
}

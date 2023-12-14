package fr.cotedazur.univ.polytech.startingpoint.districts;

/**
 * Cet enum prend englobe toutes les cartes quartiers dont on aura besoin
 * le long de la partie
 * les quartiers posséde , un nom , un cout , une couleur et un score
 * les couleurs sont null car elles ne seront pas prise en considération
 * dans cette première verion du jeu .
 * Version 0.1 du jeu citadelle.
 */
public enum DistrictsType {
    MANOIR("Manoir", 3,  "\u001B[33m", 3),
    CHATEAU("Château", 4, "\u001B[33m", 4),
    PALAIS("Palais", 5, "\u001B[33m", 5),

    // RELIGION
    TEMPLE("Temple", 1, "\u001B[34m", 1),
    EGLISE("Église", 2, "\u001B[34m", 2),
    MONASTERE("Monastère", 3,"\u001B[34m", 3),
    CATHEDRALE("Cathédrale", 5,"\u001B[34m", 5),
    // TRADE DISTRICTS
    TAVERNE("Taverne", 1, "\u001B[32m", 1),
    ECHOPPE("Échoppe", 2,"\u001B[32m", 2),
    MARCHE("Marché", 2, "\u001B[32m", 2),
    COMPTOIR("Comptoir", 3, "\u001B[32m", 3),
    PORT("Port", 4, "\u001B[32m", 4),
    HOTEL_DE_VILLE("Hôtel de ville", 5, "\u001B[32m", 5),


    // GUERRE
    TOUR_DE_GUET("Tour de guet", 1,"\u001B[31m", 1),
    PRISON("Prison", 2, "\u001B[31m", 2),
    CASERNE("Caserne", 3, "\u001B[31m", 3),
    FORTRESSE("Forteresse", 5, "\u001B[31m", 5),


    // SPECIAL

    COURT_DES_MIRACLES("Cour des miracles", 2, "\u001B[35m", 2),
    DONJON("Donjon", 3, "\u001B[35m", 3),
    LABORATOIRE("Laboratoire", 5, "\u001B[35m", 5),
    MANUFACTURE("Manufacture", 5, "\u001B[35m", 5),
    OBSERVATOIRE("Observatoire", 5, "\u001B[35m", 5),
    CIMETIERE("Cimetière", 5, "\u001B[35m", 5),
    BIBLIOTHEQUE("Bibliothèque", 6, "\u001B[35m", 6),
    ECOLE_DE_MAGIE("École de magie", 6, "\u001B[35m", 6),
    UNIVERSITE("Université", 6, "\u001B[35m", 8),
    DRACOPORT("Dracoport", 6, "\u001B[35m", 8);


    int cost ;
    String name ;
    String color ;
    String colorReset = "\u001B[0m";
    int score ;

    DistrictsType(String name , int cost , String color , int score ){
        this.cost = cost ;
        this.name = name ;
        this.color = color ;
        this.score = score;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public String getColorReset() {
        return colorReset;
    }
    public String toString() {
        return "(" + getName() + ", " + getCost() + ")";
    }
}

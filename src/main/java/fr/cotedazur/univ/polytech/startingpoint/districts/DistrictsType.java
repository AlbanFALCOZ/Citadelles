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
    MANOIR("Manoir", 5,  null, 3),
    CHATEAU("Château", 4, null, 4),
    PALAIS("Palais", 2, null, 5),

    // RELIGION
    TEMPLE("Temple", 3, null, 1),
    EGLISE("Église", 4, null, 2),
    MONASTERE("Monastère", 3,null, 3),
    CATHEDRALE("Cathédrale", 2,null, 5),
    // TRADE DISTRICTS
    TAVERNE("Taverne", 5, null, 1),
    ECHOPPE("Échoppe", 3,null, 2),
    MARCHE("Marché", 4, null, 2),
    COMPTOIR("Comptoir", 3, null, 3),
    PORT("Port", 3, null, 4),
    HOTEL_DE_VILLE("Hôtel de ville", 2, null, 5),


    // GUERRE
    TOUR_DE_GUET("Tour de guet", 3,null, 1),
    PRISON("Prison", 3, null, 2),
    CASERNE("Caserne", 3, null, 3),
    FORTRESSE("Forteresse", 2, null, 5),


    // SPECIAL

    COURT_DES_MIRACLES("Cour des miracles", 1, null, 2),
    DONJON("Donjon", 1, null, 3),
    LABORATOIRE("Laboratoire", 1, null, 5),
    MANUFACTURE("Manufacture", 1, null, 5),
    OBSERVATOIRE("Observatoire", 1, null, 5),
    CIMETIERE("Cimetière", 1, null, 5),
    BIBLIOTHEQUE("Bibliothèque", 1, null, 6),
    ECOLE_DE_MAGIE("École de magie", 1, null, 6),
    UNIVERSITE("Université", 1, null, 6),
    DRACOPORT("Dracoport", 1, null, 6);


    int cost ;
    String name ;

    String color ;

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
}

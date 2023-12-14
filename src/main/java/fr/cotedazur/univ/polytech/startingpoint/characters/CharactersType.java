package fr.cotedazur.univ.polytech.startingpoint.characters;

/**
 * Cet enum englobe toutes les cartes personnages dont on aura besoin
 * le long de la partie
 * pour cette première version , tout les numero sont mis a 0 car il ne seront
 * pas pris en considération lors de la partie , de même pour la couleur
 * Version 0.1 du jeu Citadelle.
 */
public enum CharactersType {
    ASSASSIN(1, "Assassin", "\u001B[37;1m", "assassin"),
    VOLEUR(2, "Voleur", "\u001B[37;1m", "voleur"),
    MAGICIEN(3, "Magicien", "\u001B[37;1m", "magicien"),
    ROI(4, "Roi", "\u001B[33m", "noble"),
    EVEQUE(5, "Évêque", "\u001B[34m", "religieux"),
    MARCHAND(6, "Marchand", "\u001B[32m", "marchand"),
    ARCHITECTE(7, "Architecte", "\u001B[37;1m", "architecte"),
    CONDOTTIERE(8, "Condottière", "\u001B[31m", "militaire");
    int number ;
    String role;

    String type;
    String color  ;

    CharactersType(int number , String role, String color, String type){
        this.number = number ;
        this.role = role;
        this.color = color;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }




}

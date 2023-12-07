package fr.cotedazur.univ.polytech.startingpoint.characters;

/**
 * Cet enum englobe toutes les cartes personnages dont on aura besoin
 * le long de la partie
 * pour cette première version , tout les numero sont mis a 0 car il ne seront
 * pas pris en considération lors de la partie , de même pour la couleur
 * Version 0.1 du jeu Citadelle.
 */
public enum CharactersType {
    ASSASSIN(1, "Assassin", "\u001B[37;1m"),
    VOLEUR(2, "Voleur", "\u001B[37;1m"),
    MAGICIEN(3, "Magicien", "\u001B[37;1m"),
    ROI(4, "Roi", "\u001B[33m"),
    EVEQUE(5, "Évêque", "\u001B[34m"),
    MARCHAND(6, "Marchand", "\u001B[32m"),
    ARCHITECTE(7, "Architecte", "\u001B[37;1m"),
    CONDOTTIERE(8, "Condottière", "\u001B[31m");
    int number ;
    String type ;
    String color  ;

    CharactersType(int number , String type, String color){
        this.number = number ;
        this.type = type ;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }


}

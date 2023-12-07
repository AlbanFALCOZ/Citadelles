package fr.cotedazur.univ.polytech.startingpoint.characters;

/**
 * Cet enum englobe toutes les cartes personnages dont on aura besoin
 * le long de la partie
 * pour cette première version , tout les numero sont mis a 0 car il ne seront
 * pas pris en considération lors de la partie , de même pour la couleur
 * Version 0.1 du jeu Citadelle.
 */
public enum CharactersType {
    ASSASSIN(1, "Assassin", null),
    VOLEUR(2, "Voleur", null),
    MAGICIEN(3, "Magicien", null),
    ROI(4, "Roi", null),
    EVEQUE(5, "Évêque", null),
    MARCHAND(6, "Marchand", null),
    ARCHITECTE(7, "Architecte", null),
    CONDOTTIERE(8, "Condottière", null);
    int number ;
    String type ;
    String color  ;

    CharactersType(int number , String type, String color){
        this.number = number ;
        this.type = type ;
        this.color = null;
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

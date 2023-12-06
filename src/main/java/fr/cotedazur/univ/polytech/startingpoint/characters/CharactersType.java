package fr.cotedazur.univ.polytech.startingpoint.characters;

/**
 * Cet enum englobe toutes les cartes personnages dont on aura besoin
 * le long de la partie
 * pour cette première version , tout les numero sont mis a 0 car il ne seront
 * pas pris en considération lors de la partie , de même pour la couleur
 * Version 0.1 du jeu Citadelle.
 */
public enum CharactersType {
    ASSASSIN(0, "Assassin", null),
    VOLEUR(0, "Voleur", null),
    MAGICIEN(0, "Magicien", null),
    ROI(0, "Roi", null),
    EVEQUE(0, "Évêque", null),
    MARCHAND(0, "Marchand", null),
    ARCHITECTE(0, "Architecte", null),
    CONDOTTIERE(0, "Condottière", null);
    int number ;
    String type ;
    Colors color  ;

    CharactersType(int numero , String type , Colors couleur){
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

    public Colors getColor() {
        return color;
    }


}

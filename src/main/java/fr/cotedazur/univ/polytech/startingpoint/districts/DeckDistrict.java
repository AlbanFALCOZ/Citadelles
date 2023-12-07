package fr.cotedazur.univ.polytech.startingpoint.districts;

import java.util.ArrayList;
import java.util.List;

public class DeckDistrict {

    private List<DistrictsType> districtsInDeck;

    public DeckDistrict() {
        this.districtsInDeck = new ArrayList<>();
        for (int numbOfCard = 0; numbOfCard < 5; numbOfCard++) {
            if (numbOfCard < 1) {
                districtsInDeck.add(DistrictsType.COURT_DES_MIRACLES);
                districtsInDeck.add(DistrictsType.LABORATOIRE);
                districtsInDeck.add(DistrictsType.MANUFACTURE);
                districtsInDeck.add(DistrictsType.OBSERVATOIRE);
                districtsInDeck.add(DistrictsType.CIMETIERE);
                districtsInDeck.add(DistrictsType.BIBLIOTHEQUE);
                districtsInDeck.add(DistrictsType.ECOLE_DE_MAGIE);
                districtsInDeck.add(DistrictsType.UNIVERSITE);
                districtsInDeck.add(DistrictsType.DRACOPORT);
            }

            if (numbOfCard < 2) {
                districtsInDeck.add(DistrictsType.CATHEDRALE);
                districtsInDeck.add(DistrictsType.PALAIS);
                districtsInDeck.add(DistrictsType.HOTEL_DE_VILLE);
                districtsInDeck.add(DistrictsType.FORTRESSE);
                districtsInDeck.add(DistrictsType.DONJON);
            }

            if (numbOfCard < 3 ){
                districtsInDeck.add(DistrictsType.TEMPLE);
                districtsInDeck.add(DistrictsType.MONASTERE);
                districtsInDeck.add(DistrictsType.ECHOPPE);
                districtsInDeck.add(DistrictsType.COMPTOIR);
                districtsInDeck.add(DistrictsType.PORT);
                districtsInDeck.add(DistrictsType.TOUR_DE_GUET);
                districtsInDeck.add(DistrictsType.PRISON);
                districtsInDeck.add(DistrictsType.CASERNE);
            }

            if (numbOfCard < 4 ) {
                districtsInDeck.add(DistrictsType.EGLISE);
                districtsInDeck.add(DistrictsType.CHATEAU);
                districtsInDeck.add(DistrictsType.MARCHE);
            }
            //Cas 5 cartes
            districtsInDeck.add(DistrictsType.MANOIR);
            districtsInDeck.add(DistrictsType.TAVERNE);
        }
    }

    public DistrictsType getDistrictsInDeck() {
        double indice = Math.random()*districtsInDeck.size();
        DistrictsType district = districtsInDeck.get((int)indice);
        districtsInDeck.remove((int)indice);
        return district;
    }

    public int getDeckSize() {
        return districtsInDeck.size();
    }



    public static void main (String[] args){
        DeckDistrict deckDistrict = new DeckDistrict();
        System.out.println(deckDistrict.getDistrictsInDeck().name);
        System.out.println(deckDistrict.getDeckSize());
    }


    public void addDistrictToDeck(DistrictsType district) {
        this.districtsInDeck.add(district);
    }
}

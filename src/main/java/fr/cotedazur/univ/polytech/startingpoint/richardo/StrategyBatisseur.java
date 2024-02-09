package fr.cotedazur.univ.polytech.startingpoint.richardo;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.ArrayList;
import java.util.List;

public class StrategyBatisseur {


    public StrategyBatisseur() {
    }


    //Dans le cas où Richardo est un batisseur, il essaie de construire les quartiers noble/marchands en priorité.
    public String tryBuildBatisseur(RobotRichardo bot) {
        for (int i = 0; i < bot.getDistrictInHand().size(); i++) {
            DistrictsType district = bot.getDistrictInHand().get(i);
            if (district.getCost() <= bot.getGolds() && !bot.getCity().contains(district) && bot.getCharacter().getType().equals(district.getType())) {
                district.powerOfDistrict(bot, 1);
                bot.getCity().add(district);
                bot.setGolds(bot.getGolds() - district.getCost());
                bot.getDistrictInHand().remove(i);
                return "a new " + district.getName();
            }
        }
        return bot.buildDistrictAndRetrieveItsName();
    }

    public void isBatisseur(RobotRichardo bot) {
        bot.setBatisseur(bot.getGolds() < 4);
    }

    public boolean pickBatisseur(List<CharactersType> availableCharacters, RobotRichardo bot) {
        isBatisseur(bot);

        if (availableCharacters.contains(CharactersType.MARCHAND)) {
            bot.pickCharacterCard(availableCharacters, CharactersType.MARCHAND);
            if (bot.getCharacter() == CharactersType.MARCHAND) return true;
        }
        if (availableCharacters.contains(CharactersType.ROI)) {
            bot.pickCharacterCard(availableCharacters, CharactersType.ROI);
            if (bot.getCharacter() == CharactersType.ROI) return true;
        }
        if (availableCharacters.contains(CharactersType.ARCHITECTE)) {

            bot.pickCharacterCard(availableCharacters, CharactersType.ARCHITECTE);
            return bot.getCharacter() == CharactersType.ARCHITECTE;
        }
        /*
        bot.setCharacter(availableCharacters.get(0));
        availableCharacters.remove(0);

         */
        return false;
    }

    public List<DistrictsType> pickDistrictCardBatisseur(List<DistrictsType> listDistrict, DeckDistrict deck, RobotRichardo bot) {
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        for (DistrictsType district : listDistrict) {
            if (bot.getCharacter().getType().equals(district.getType())) {
                listDistrictToBuild.add(district);
            }
            if (listDistrictToBuild.size() == bot.getNumberOfCardsChosen()) return listDistrictToBuild;
        }
        if (listDistrictToBuild.isEmpty()) {
            bot.setBatisseur(false);
            listDistrictToBuild = bot.pickDistrictCard(listDistrict, deck);
            bot.setBatisseur(true);
            return listDistrictToBuild;
        }
        while (listDistrictToBuild.size() < bot.getNumberOfCardsChosen())
            listDistrictToBuild.add(listDistrict.remove(0));
        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }
}

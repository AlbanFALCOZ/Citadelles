package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;

import java.util.List;

public class RobotRichardo extends Robot{


    public RobotRichardo(String name) {
        super(name);
    }

    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        bots.remove(this) ;
        for(Robot bot : bots){
            if(bot.getNumberOfDistrictInCity() > 5){
                pickCondottiere(availableCharacters);
                    break ;
                }
                else if(bot.getGolds() > 5){
                    pickAssassin(availableCharacters);
                    break ;
                }
            }
        if (this.getGolds() > 6){
            pickArchitecte(availableCharacters);
        } else if (this.getNumberOfDistrictInHand() == 0){
            pickMagicien(availableCharacters);
        }
        else if (this.getGolds() < 2){
            pickMarchand(availableCharacters);
        }
        }






    public void pickCondottiere(List<CharactersType> availableCharacters) {
        if (availableCharacters.contains("Condottiere")) {
            this.setCharacter(CharactersType.CONDOTTIERE);
            availableCharacters.remove(CharactersType.CONDOTTIERE);

        }
    }

    public void pickAssassin(List<CharactersType> availableCharacters) {
        if (availableCharacters.contains("Assassin")) {
            this.setCharacter(CharactersType.ASSASSIN);
            availableCharacters.remove(CharactersType.ASSASSIN);
        }
    }
    public void pickArchitecte(List<CharactersType> availableCharacters) {
        if (availableCharacters.contains("Architecte")) {
            this.setCharacter(CharactersType.ARCHITECTE);
            availableCharacters.remove(CharactersType.ARCHITECTE);
        }
    }
    public void pickMagicien(List<CharactersType> availableCharacters) {
        if (availableCharacters.contains("Magicien")) {
            this.setCharacter(CharactersType.MAGICIEN);
            availableCharacters.remove(CharactersType.MAGICIEN) ;
        }
    }

    public void pickMarchand(List<CharactersType> availableCharacters){
        if(availableCharacters.contains("Marchand")){
            this.setCharacter((CharactersType.MARCHAND));
            availableCharacters.remove(CharactersType.MARCHAND);
        }
    }



    @Override
    public String tryBuild() {
        return null;
    }

    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        return null;
    }

    @Override
    public int generateChoice() {
        return 0;
    }










}

package fr.cotedazur.univ.polytech.startingpoint.richardo;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;


import java.util.*;

public class RobotRichardo extends Robot {
    public static final String NOBLE = "noble";
    public static final String RELIGIOUS = "religieux";
    public  static final String MILITARY = "militaire";
    public static final String COMMERCIAL = "marchand";
    private StrategyBatisseur strategyBatisseur;

    public StrategyAgressif getStrategyAgressif() {
        return strategyAgressif;
    }

    private StrategyAgressif strategyAgressif;

    private StrategyOpportuniste strategyOpportuniste;

    private boolean opportuniste = false;

    private boolean agressif = false;
    private boolean batisseur = false;


    public void setAgressif(boolean agressif) {
        this.agressif = agressif;
    }

    public void setBatisseur(boolean batisseur) {
        this.batisseur = batisseur;
    }

    public void setOpportuniste(boolean opportuniste) {
        this.opportuniste = opportuniste;
    }

    public void setListCharacters(List<CharactersType> listCharacters) {
        this.listCharacters = listCharacters;
    }

    public void setListOfCharacters(DeckCharacters listOfCharacters) {
        this.listOfCharacters = listOfCharacters;
    }

    public void setAvailableCharacters(List<CharactersType> availableCharacters) {
        this.availableCharacters = availableCharacters;
    }

    public boolean getAgressive(){
        return agressif ;
    }





    public boolean isBatisseur() {
        return batisseur;
    }

    public boolean isOpportuniste() {
        return opportuniste;
    }

    public List<CharactersType> getListCharacters() {
        return listCharacters;
    }

    public DeckCharacters getListOfCharacters() {
        return listOfCharacters;
    }

    public List<CharactersType> getAvailableCharacters() {
        return availableCharacters;
    }


    private List<CharactersType> listCharacters = new ArrayList<>(Arrays.asList(CharactersType.values())) ;

    private DeckCharacters listOfCharacters = new DeckCharacters();
    private List<CharactersType> availableCharacters;

    public RobotRichardo(String name) {
        super(name);
        strategyBatisseur = new StrategyBatisseur();
        strategyAgressif = new StrategyAgressif();
        strategyOpportuniste= new StrategyOpportuniste();

    }

    @Override
    public String tryBuild() {
        if (batisseur) return strategyBatisseur.tryBuildBatisseur(this);
        return strategyBatisseur.buildDistrictAndRetrieveItsName(this);
    }


    @Override
    public int generateChoice() {
        if(this.getGolds()<6) {
            return 1 ;
        }
        else {
            return 0 ;
        }
    }


    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        ActionOfBotDuringARound action = new ActionOfBotDuringARound(this,true);
        this.availableCharacters = new ArrayList<>(availableCharacters) ;
        if (scenarioArchitecte(bots) && availableCharacters.size() == 5) {
            if (availableCharacters.contains(CharactersType.ARCHITECTE) && availableCharacters.contains(CharactersType.ASSASSIN)) {
                pickCharacterCard(availableCharacters,CharactersType.ASSASSIN);
                action.printScenarioArchitecte();
                return;
            }
            if (availableCharacters.contains(CharactersType.ARCHITECTE)) {
                pickCharacterCard(availableCharacters,CharactersType.ARCHITECTE);
                action.printScenarioArchitecte();
                return;
            }
        }


        this.strategyBatisseur.isBatisseur(this);
        if(!this.batisseur){
          this.strategyAgressif.isAgressif(bots , this);
        }

        if (batisseur) {
            strategyBatisseur.pickBatisseur(availableCharacters, this);
            batisseur = false ;


            /*
        } else if (opportuniste) {
            strategyOpportuniste.pickOpportuniste(this);

             */
        } else if (agressif) {
            strategyAgressif.pickAgressif(availableCharacters, bots, this);
            agressif = false ;

        } else {
            setCharacter(availableCharacters.get(0));
            availableCharacters.remove(0);
        }

    }



    public boolean thereIsA(CharactersType character, List<CharactersType> availableCharacters) {
        return (this.hasCrown && availableCharacters.contains(character));
    }



    public void pickCharacterCard(List<CharactersType> availableCharacters, CharactersType character) {
        if (availableCharacters.contains(character)) {
            this.setCharacter(character);
            availableCharacters.remove(character);
        }

    }





    public int countDistrictsByType(String type) {
        return (int) getCity().stream()
                .filter(district -> district.getType().equals(type))
                .count();
    }

    public int countDistrictsInHandByType(String type) {
        return (int) getDistrictInHand().stream()
                .filter(district -> district.getType().equals(type))
                .count();
    }



    @Override
    public List<DistrictsType> pickDistrictCard(List<DistrictsType> listDistrict, DeckDistrict deck) {
        if (batisseur && (character == CharactersType.ROI || character == CharactersType.MARCHAND) ) return strategyBatisseur.pickDistrictCardBatisseur( listDistrict,deck, this);
        listDistrict.sort(compareByCost().reversed());
        List<DistrictsType> listDistrictToBuild = new ArrayList<>();
        int costOfDistrictToBeBuilt = 0;
        int indice = 0;
        int i = 0;
        while (i < listDistrict.size()) {
            if (listDistrict.get(i).getCost() - costOfDistrictToBeBuilt <= getGolds()) {
                costOfDistrictToBeBuilt += listDistrict.get(i).getCost();
                listDistrictToBuild.add(listDistrict.remove(i));
                i--;
                indice++;
                if (indice == getNumberOfCardsChosen()) break;

            }
            i++;
        }
        while (listDistrictToBuild.size() < getNumberOfCardsChosen()) {
            listDistrictToBuild.add(listDistrict.remove(listDistrict.size() - 1));
        }
        for (DistrictsType districtNonChosen : listDistrict) {
            deck.addDistrictToDeck(districtNonChosen);
        }
        return listDistrictToBuild;
    }

    @Override
    public Robot chooseVictimForAssassin(List<Robot> bots,int numberOfTheCharacterToKill){
        Robot victim = null;
        if (scenarioArchitecte(bots)) victim = this.strategyAgressif.chooseVictimForAssassin(bots,7,this);
        else victim = this.strategyAgressif.chooseVictimForAssassin(bots , numberOfTheCharacterToKill , this) ;
        return victim ;
    }

    @Override
    public int getNumberOfCharacterToKill(List<Robot> bots) {
        if (scenarioArchitecte(bots)) return 7;
        for (Robot bot : bots) {
            if (thereIsA(CharactersType.VOLEUR, getAvailableCharacters())) {
                return 2;

            } else if (thereIsA(CharactersType.CONDOTTIERE, getAvailableCharacters()) || strategyAgressif.hasMaxDistricts(bots, this)) {
                return 8;

            } else {
                {
                    if (bot.getNumberOfDistrictInHand() <= 1 || getNumberOfDistrictInHand() == 3) {
                        return 3;
                    }
                }
            }
        }
        return super.getNumberOfCharacterToKill(bots);
    }

    @Override
    public Robot chooseVictimForCondottiere(List<Robot> bots){
        Robot victim = this.strategyAgressif.chooseVictimForCondottiere(bots , this );
        return victim ;
    }

    @Override
    public Robot chooseVictimForMagicien(List<Robot> bots){
        Robot victim = this.strategyAgressif.chooseVictimForMagicien(bots , this ) ;
        return victim ;
    }


    public boolean scenarioArchitecte(List<Robot> bots) {
        for (Robot bot: bots) {
            if (bot.getNumberOfDistrictInHand() >= 1 && bot.getGolds() >= 4 && bot.getNumberOfDistrictInCity() >=5 && !bot.equals(this)) return true;
        }
        return false;
    }



}
package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.characters.DeckCharacters;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;


import java.util.*;

public class RobotRichardo extends Robot {
    public static final String NOBLE = "noble";
    public static final String RELIGIOUS = "religieux";
    public  static final String MILITARY = "militaire";
    public static final String COMMERCIAL = "marchand";
    private StrategyBatisseur strategyBatisseur;

    private StrategyAgressif strategyAgressif;

    private StrategyOpportuniste strategyOpportuniste;

    private boolean opportuniste = false;



    public int getCond() {
        return cond;
    }

    public int getAssas() {
        return assas;
    }

    public int getMarch() {
        return march;
    }

    public int getArch() {
        return arch;
    }

    private int cond = 0;
    private int assas = 0;
    private int march = 0;
    private int arch = 0;

    private boolean agressif = false;
    private boolean batisseur = false;

    public void setCond(int cond) {
        this.cond = cond;
    }

    public void setAssas(int assas) {
        this.assas = assas;
    }

    public void setMarch(int march) {
        this.march = march;
    }

    public void setArch(int arch) {
        this.arch = arch;
    }

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


    public boolean isAgressif() {
        return agressif;
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
        if (batisseur) {
            strategyBatisseur.pickBatisseur(availableCharacters, this);
            /*
        } else if (opportuniste) {
            strategyOpportuniste.pickOpportuniste(this);

             */
        } else if (agressif) {
            strategyAgressif.pickAgressif(availableCharacters, bots, this);
            agressif =false ;
        } else {
            setCharacter(availableCharacters.get(0));
            availableCharacters.remove(0);
        }
    }



    public boolean thereIsA(CharactersType character, List<CharactersType> availableCharacters) {
        if (this.hasCrown) {
            listCharacters.remove(availableCharacters) ;
            return listCharacters.contains(character);
        }
        return false;
    }



    public void pickCharacterCard(List<CharactersType> availableCharacters, CharactersType character) {
        if (availableCharacters.contains(character)) {
            this.setCharacter(character);
            availableCharacters.remove(character);
        }

    }


    @Override
    public Robot chooseVictimForMagicien(List<Robot> bots){
        Robot victim = bots.get(0);
        int numberOfDistrictsInHand = victim.getNumberOfDistrictInHand();
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInHand() >= numberOfDistrictsInHand && bot.getCharacter()!= CharactersType.MAGICIEN) victim = bot;
        }
        return victim;
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



    public boolean hasMaxDistricts(List<Robot> bots) {
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() > this.getNumberOfDistrictInCity()) {
                return false;
            }
        }
        return true ;
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



}
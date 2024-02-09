package fr.cotedazur.univ.polytech.startingpoint.richardo;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DeckDistrict;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import fr.cotedazur.univ.polytech.startingpoint.robots.Robot;

import java.util.ArrayList;
import java.util.List;

public class RobotRichardo extends Robot {
    public static final String RELIGIOUS = "religieux";

    public static final String MILITARY = "militaire";

    private final StrategyBatisseur strategyBatisseur;
    private final StrategyAgressif strategyAgressif;
    private final StrategyOpportuniste strategyOpportuniste;
    private boolean opportuniste = false;
    private boolean agressif = false;
    private boolean batisseur = false;
    private List<CharactersType> availableCharacters;

    public RobotRichardo(String name) {
        super(name);
        strategyBatisseur = new StrategyBatisseur();
        strategyAgressif = new StrategyAgressif();

        strategyOpportuniste = new StrategyOpportuniste();


    }

    public StrategyAgressif getStrategyAgressif() {
        return strategyAgressif;
    }

    public void setAgressif(boolean agressif) {
        this.agressif = agressif;
    }

    public boolean getAgressive() {
        return agressif;
    }

    public boolean isBatisseur() {
        return batisseur;
    }

    public void setBatisseur(boolean batisseur) {
        this.batisseur = batisseur;
    }

    public boolean isOpportuniste() {
        return opportuniste;
    }


    public void setOpportuniste(boolean opportuniste) {
        this.opportuniste = opportuniste;
    }

    public List<CharactersType> getAvailableCharacters() {
        return availableCharacters;
    }

    public void setAvailableCharacters(List<CharactersType> availableCharacters) {
        this.availableCharacters = availableCharacters;
    }

    //On construit le premier district possible
    public String buildDistrictAndRetrieveItsName() {
        for (int i = 0; i < this.getDistrictInHand().size(); i++) {
            DistrictsType district = this.getDistrictInHand().get(i);
            if (district.getCost() <= this.getGolds() && !this.getCity().contains(district)) {
                district.powerOfDistrict(this, 1);
                this.getCity().add(district);
                this.setGolds(this.getGolds() - district.getCost());
                this.getDistrictInHand().remove(i);
                return "a new " + district.getName();
            }
        }
        return "nothing";
    }


    @Override
    public String tryBuild() {
        if (batisseur) return strategyBatisseur.tryBuildBatisseur(this);
        if (opportuniste) return strategyOpportuniste.tryBuildOpportuniste(this);
        return buildDistrictAndRetrieveItsName();
    }


    @Override
    public int generateChoice() {

        if (this.getGolds() < 6) {
            return 1;
        } else {
            return 0;

        }
    }


    @Override
    public void pickCharacter(List<CharactersType> availableCharacters, List<Robot> bots) {
        this.availableCharacters = new ArrayList<>(availableCharacters);


        this.strategyAgressif.isAgressif(bots, this);
        if (!this.agressif) {
            this.strategyBatisseur.isBatisseur(this);
        }
        if (!this.batisseur) {
            strategyOpportuniste.isOpportuniste(this);

        }
        if (agressif) {
            if (strategyAgressif.pickAgressif(availableCharacters, bots, this)) return;

        } else if (opportuniste) {
            if (strategyOpportuniste.pickOpportuniste(availableCharacters, this)) return;

        } else if (batisseur && (strategyBatisseur.pickBatisseur(availableCharacters, this))) {
            return;

        }


        ActionOfBotDuringARound action = new ActionOfBotDuringARound(this, true);

        if (scenarioArchitecte(bots) && availableCharacters.size() == 5) {
            if (availableCharacters.contains(CharactersType.ARCHITECTE) && availableCharacters.contains(CharactersType.ASSASSIN)) {
                pickCharacterCard(availableCharacters, CharactersType.ASSASSIN);
                action.printScenarioArchitecte();
                return;
            }
            if (availableCharacters.contains(CharactersType.ARCHITECTE)) {
                pickCharacterCard(availableCharacters, CharactersType.ARCHITECTE);
                action.printScenarioArchitecte();
                return;
            }
        } else if (scenarioRoi(bots)) {
            if (availableCharacters.contains(CharactersType.ROI)) {
                pickCharacterCard(availableCharacters, CharactersType.ROI);
                return;
            } else {
                setAgressif(true);
                setBatisseur(false);
                setOpportuniste(false);
            }

        }


        setCharacter(availableCharacters.get(0));
        availableCharacters.remove(0);


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
        if (batisseur && (character == CharactersType.ROI || character == CharactersType.MARCHAND))
            return strategyBatisseur.pickDistrictCardBatisseur(listDistrict, deck, this);
        if (opportuniste) return strategyOpportuniste.pickDistrictCardOpportuniste(listDistrict, deck, this);
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
    public Robot chooseVictimForAssassin(List<Robot> bots, int numberOfTheCharacterToKill) {
        if (scenarioArchitecte(bots)) return this.strategyAgressif.chooseVictimForAssassin(bots, 7, this);
        else return this.strategyAgressif.chooseVictimForAssassin(bots, numberOfTheCharacterToKill, this);
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
    public Robot chooseVictimForCondottiere(List<Robot> bots) {

        Robot victim = this.strategyAgressif.chooseVictimForCondottiere(bots, this);
        return victim;
    }

    @Override
    public Robot chooseVictimForMagicien(List<Robot> bots) {
        Robot victim = this.strategyAgressif.chooseVictimForMagicien(bots, this);
        return victim;
    }

    @Override
    public CharactersType chooseVictimForVoleur(List<Robot> bots) {
        if (opportuniste) {
            return strategyOpportuniste.chooseVictimForVoleur(bots, this);
        }

        return super.chooseVictimForVoleur(bots);
    }


    public boolean scenarioArchitecte(List<Robot> bots) {
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInHand() >= 1 && bot.getGolds() >= 4 && bot.getNumberOfDistrictInCity() >= 5 && !bot.equals(this))
                return true;
        }
        return false;
    }


    public boolean scenarioRoi(List<Robot> bots) {
        for (Robot bot : bots) {
            if (bot.getNumberOfDistrictInCity() == 6) {
                return true;
            }
        }
        return false;
    }


}
package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerTest {

    @Test
    void districtToDestroy() {
        RobotRandom destructeur = new RobotRandom("destructeur");
        RobotRandom victime = new RobotRandom("victime");
        Power power = new Power(destructeur.getName(), new ActionOfBotDuringARound(destructeur));
        destructeur.setCharacter(CharactersType.CONDOTTIERE);
        victime.setCharacter(CharactersType.MARCHAND);
        victime.setGolds(30);
        victime.addDistrict(DistrictsType.TAVERNE);
        victime.addDistrict(DistrictsType.PALAIS);
        victime.addDistrict(DistrictsType.PRISON);

        for (int i = 0; i < victime.getNumberOfDistrictInHand(); i++){
            victime.tryBuild();
        }

        destructeur.setGolds(1);

        assertEquals(1, power.condottiere(destructeur, victime));




    }

    @Test
    void condottiere() {
    }
}
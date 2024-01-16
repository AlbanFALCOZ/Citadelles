package fr.cotedazur.univ.polytech.startingpoint.robots;

import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.districts.DistrictsType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerTest {

    @Test
    void canDestroyDistrict() {
        RobotRandom destructor = new RobotRandom("destructor");
        RobotRandom victim = new RobotRandom("victim");
        Power power = new Power(destructor, new ActionOfBotDuringARound(destructor));
        destructor.setCharacter(CharactersType.CONDOTTIERE);
        victim.setCharacter(CharactersType.MAGICIEN);
        victim.addDistrict(DistrictsType.TAVERNE);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PALAIS);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PRISON);
        victim.tryBuild();

        assertTrue(power.canDestroyDistrict(victim, DistrictsType.TAVERNE));
        destructor.setGolds(1);
        assertFalse(power.canDestroyDistrict(victim, DistrictsType.PRISON));
        assertFalse(power.canDestroyDistrict(victim, DistrictsType.PALAIS));
        destructor.setGolds(5);
        assertFalse(power.canDestroyDistrict(victim, DistrictsType.MANOIR));
        




    }

    @Test
    void condottiere() {
        RobotRandom destructor = new RobotRandom("destructor");
        RobotRandom victim = new RobotRandom("victim");
        Power power = new Power(destructor, new ActionOfBotDuringARound(destructor));
        destructor.setCharacter(CharactersType.CONDOTTIERE);
        victim.setCharacter(CharactersType.MARCHAND);
        victim.setGolds(30);
        victim.addDistrict(DistrictsType.TAVERNE);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PALAIS);
        victim.tryBuild();
        victim.addDistrict(DistrictsType.PRISON);
        victim.tryBuild();

        destructor.setGolds(5);
        power.condottiere(victim);
        assertEquals(1, destructor.getGolds());
        power.condottiere(victim);
        assertEquals(1, destructor.getGolds());
        power.condottiere(victim);
        assertEquals(1, destructor.getGolds());

        victim.setCharacter(CharactersType.EVEQUE);
        destructor.setGolds(5);
        power.condottiere(victim);
        assertEquals(5, destructor.getGolds());
    }

    @Test
    public void tryDestroyDonjon() {
        RobotRandom destructor = new RobotRandom("destructor");
        RobotRandom victim = new RobotRandom("victim");
        Power power = new Power(destructor, new ActionOfBotDuringARound(destructor));
        destructor.setCharacter(CharactersType.CONDOTTIERE);
        victim.setCharacter(CharactersType.MARCHAND);
        victim.setGolds(30);
        victim.addDistrict(DistrictsType.DONJON);
        victim.tryBuild();

        destructor.setGolds(5);
        power.condottiere(victim);
        assertEquals(3, destructor.getGolds());
        assertEquals(0,victim.getNumberOfDistrictInCity());
    }

    @Test
    public void marchand() {
        RobotRandom marchand = new RobotRandom("marchand");
        Power power = new Power(marchand,new ActionOfBotDuringARound(marchand));
        marchand.setCharacter(CharactersType.MARCHAND);
        assertEquals(2,marchand.getGolds());
        power.marchand();
        assertEquals(3,marchand.getGolds());
        marchand.winGoldsByTypeOfBuildings();
        assertEquals(3,marchand.getGolds());
        marchand.addDistrict(DistrictsType.TAVERNE);
        marchand.addGold(1);//Pour compenser la construction de la taverne
        marchand.addDistrict(DistrictsType.ECHOPPE);
        marchand.addGold(2);//Pour compenser la construction de l'échoppe
        marchand.tryBuild();
        marchand.tryBuild();
        marchand.winGoldsByTypeOfBuildings();
        assertEquals(5,marchand.getGolds());
    }

    @Test
    void testAssassin() {
        RobotRandom assassin = new RobotRandom("Assassin");
        RobotRandom victim = new RobotRandom("Victim");
        Power power = new Power(assassin,new ActionOfBotDuringARound(assassin));
        assassin.setCharacter(CharactersType.ASSASSIN);
        victim.setCharacter(CharactersType.MARCHAND);
        assertFalse(victim.getIsAssassinated());
        power.assassin(victim);
        assertTrue(victim.getIsAssassinated());
    }



}
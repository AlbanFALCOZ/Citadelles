package fr.cotedazur.univ.polytech.startingpoint.robots;


import fr.cotedazur.univ.polytech.startingpoint.characters.CharactersType;
import fr.cotedazur.univ.polytech.startingpoint.game.ActionOfBotDuringARound;

public class RobotDestroyer extends RobotWithChoice{
    Power power;
    ActionOfBotDuringARound action;

    public RobotDestroyer(String name) {
        super(name);
        power = new Power(this, action);
    }




}

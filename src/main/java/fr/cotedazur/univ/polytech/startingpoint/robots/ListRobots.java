package fr.cotedazur.univ.polytech.startingpoint.robots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ListRobots extends ArrayList<Robot> {

    private List<Robot> sortedRobots = new ArrayList<>();

    public List<Robot> getSortedRobots() {
        return sortedRobots;
    }

    public List<Robot> sortRobots() {
        List<Robot> sortedList = new ArrayList<>(this);
        Collections.sort(sortedList, Comparator.comparingInt(bot -> bot.getCharacter().getNumber()));
        return sortedRobots;
    }

}

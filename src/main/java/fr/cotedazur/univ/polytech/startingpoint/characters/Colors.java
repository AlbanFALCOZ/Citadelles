package fr.cotedazur.univ.polytech.startingpoint.characters;

import java.util.Random;

public enum Colors {
    GREEN("\u001B[32m"),
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    PURPLE("\u001B[35m"),
    GRAY("\u001B[37;1m"),
    WHITE( "\u001B[37m"),
    RESET("\u001B[0m"),


    ;

    public String getColorDisplay() {
        return colorDisplay;
    }

    String colorDisplay ;
    Colors(String colorDisplay){
        this.colorDisplay = colorDisplay ;
    }

    public static Colors getRandomColorCode() {
        Random random = new Random();
        Colors[] allowedColors = {
                Colors.BLUE,
                Colors.GREEN,
                Colors.RED,
                Colors.YELLOW
        };
        int randomIndex = random.nextInt(allowedColors.length);
        return allowedColors[randomIndex];
    }





}

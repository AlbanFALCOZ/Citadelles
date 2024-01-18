package fr.cotedazur.univ.polytech.startingpoint.characters;

public enum Colors {
    GREEN("\u001B[32m"),
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    PURPLE("\u001B[35m"),
    GRAY("\u001B[37;1m"),
    RESET("\u001B[0m")

    ;

    public String getColorDisplay() {
        return colorDisplay;
    }

    String colorDisplay ;
    Colors(String colorDisplay){
        this.colorDisplay = colorDisplay ;
    }



}

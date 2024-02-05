package fr.cotedazur.univ.polytech.startingpoint.arguments;

import com.beust.jcommander.Parameter;

public class CitadelleArguments {
    @Parameter(names = {"--2thousands"}, description = "Joue 2000 parties")
    private boolean _2Thousands;

    @Parameter(names = {"--demo"}, description = "Affiche une partie démo")
    private boolean demoMode;

    @Parameter(names = {"--csv"}, description = "Update les statistiques dans le fichier stats/gamestats.csv")
    private String csvFilePath;

    // Ajoutez d'autres paramètres selon les besoins de votre application

    public boolean _2ThousandsGame() {
        return _2Thousands;
    }

    public boolean isDemoMode() {
        return demoMode;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

}
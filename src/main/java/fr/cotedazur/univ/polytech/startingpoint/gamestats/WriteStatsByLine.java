package fr.cotedazur.univ.polytech.startingpoint.gamestats;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class WriteStatsByLine {
    public static void writeDataLineByLine(String[][] data)
    {

        File file = new File("stats/gamestats.csv");
        try {
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = {"Nom", "Nombre de parties gagnées", "Pourcentage de parties gagnées(%)", "Nombre d'égalités", "Pourcentage d'égalités(%)", "Nombre de parties perdues", "Pourcentage de parties perdues(%)", "Score moyen"};
            writer.writeNext(header, false);
            writer.writeAll(java.util.Arrays.asList(data), false);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
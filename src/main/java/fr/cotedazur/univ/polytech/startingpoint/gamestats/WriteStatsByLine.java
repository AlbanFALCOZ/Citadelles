package fr.cotedazur.univ.polytech.startingpoint.gamestats;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteStatsByLine {

    public static void writeDataLineByLine(String data)
    {

        File file = new File("stats/gamestats.csv");
        try {
            FileWriter outputfile = new FileWriter(file, true);

            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeNext(data.split("\n"), false);

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
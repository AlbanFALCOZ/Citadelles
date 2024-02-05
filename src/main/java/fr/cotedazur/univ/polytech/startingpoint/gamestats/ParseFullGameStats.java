package fr.cotedazur.univ.polytech.startingpoint.gamestats;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class ParseFullGameStats {
    public static void parseFullStats() throws Exception {

        File file = new File("stats/gamestats.csv");

        List<String[]> allRows;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {

            allRows = reader.readAll();
        }

        for (String[] row : allRows) {
            System.out.println(Arrays.toString(row));
        }
    }
}

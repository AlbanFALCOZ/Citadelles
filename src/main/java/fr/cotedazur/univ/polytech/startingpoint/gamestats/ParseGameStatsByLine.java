package fr.cotedazur.univ.polytech.startingpoint.gamestats;

import com.opencsv.*;

import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;

public class ParseGameStatsByLine {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {

        URL fileUrl = ParseGameStatsByLine.class.getClassLoader().getResource("data.csv");

        //Build reader instance
        //Read data.csv
        //Default separator is comma
        //Start reading from line number 2 (line numbers start from zero)
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(new FileReader(fileUrl.getFile()))
                .withSkipLines(1)
                .withCSVParser(csvParser)
                .build();

        //Read CSV line by line and use the string array as you want
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            if (nextLine != null) {
                //Verifying the read data here
                System.out.println(Arrays.toString(nextLine));
            }
        }
    }
}

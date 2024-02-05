package fr.cotedazur.univ.polytech.startingpoint.gamestats;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriteStatsByLineTest {

    @Test
    void writeDataLineByLine() throws IOException {
        String data1 = "Test data line 1";
        String data2 = "Test data line 2";
        WriteStatsByLine.writeDataLineByLine(data1);
        WriteStatsByLine.writeDataLineByLine(data2);

        File file = new File("stats/gamestats.csv");
        assertTrue(file.exists(), "File exists.");


        List<String> lines = Files.readAllLines(Paths.get("stats/gamestats.csv"));
        assertTrue(lines.contains("Test data line 1"), "File should contain the first line of data.");
        assertTrue(lines.contains("Test data line 2"), "File should contain the second line of data.");

    }
}
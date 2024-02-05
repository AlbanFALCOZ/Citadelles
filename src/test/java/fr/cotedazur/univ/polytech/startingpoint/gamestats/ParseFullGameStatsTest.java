package fr.cotedazur.univ.polytech.startingpoint.gamestats;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ParseFullGameStatsTest {

    @Test
    void parseFullStats() {
        // Prepare test data
        File file = new File("stats/gamestats.csv");
        WriteStatsByLine.writeDataLineByLine("Test data line 1");

        try {
            ParseFullGameStats.parseFullStats();
        } catch (Exception e) {
            fail("Exception should not be thrown.");
        }

    }
}
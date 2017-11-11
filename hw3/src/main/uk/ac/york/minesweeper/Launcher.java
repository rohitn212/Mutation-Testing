package uk.ac.york.minesweeper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        readConfig("configFile.txt");
    }

    private static void readConfig(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
    }
}
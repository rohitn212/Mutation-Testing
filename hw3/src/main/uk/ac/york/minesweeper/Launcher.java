package uk.ac.york.minesweeper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Launcher {
    public static void main(String[] args) throws FileNotFoundException {
        readConfig("configFile.txt");
    }

    private static void readConfig(String filename) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
    }
}
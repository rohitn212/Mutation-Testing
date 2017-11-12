package uk.ac.york.minesweeper;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class TemplateClass {
    static class Instrument {
        private int line;
        private String type;
        private ArrayList<Pair<String, Object>> pair;

        Instrument(int line, String type, ArrayList<Pair<String, Object>> pair) {
            this.setLine(line);
            this.setType(type);
            this.setPair(pair);
        }

        public int getLine() {
            return line;
        }

        void setLine(int line) {
            this.line = line;
        }

        public String getType() {
            return type;
        }

        void setType(String type) {
            this.type = type;
        }

        public ArrayList<Pair<String, Object>> getPair() {
            return pair;
        }

        void setPair(ArrayList<Pair<String, Object>> pair) {
            this.pair = pair;
        }

        // prints the instrumented code 
        public void printInstrument() {
            System.out.println("line: " + getLine());
            System.out.println("type: " + getType());
            System.out.print("Pair: ");
            ArrayList<Pair<String, Object>> arr = getPair();
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(arr.get(i).toString());
            }
        }
    }
    public static ArrayList<Instrument> instrumList = new ArrayList<>();

    // Creates a deep copy of the instrum list
    public static ArrayList<Instrument> copyInstrumList() {
        ArrayList<Instrument> newInstrumList = new ArrayList<>();
        for (Instrument instr: instrumList) newInstrumList.add(instr);
        return newInstrumList;
    }

    // Generates the instrumented code
    public static void instrum(int line, String type, Pair<String, Object>...args) {
        ArrayList<Pair<String, Object>> pairList = new ArrayList<>();
        Collections.addAll(pairList, args);
        Instrument i = new Instrument(line, type, pairList);
        instrumList.add(i);
    }
}
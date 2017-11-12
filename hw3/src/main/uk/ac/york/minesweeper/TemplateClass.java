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

        public void printInstrument() {
            System.out.println("line: " + getLine());
            System.out.println("type: " + getType());
            System.out.println("Pair: \n");
            ArrayList<Pair<String, Object>> arr = getPair();
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(arr.get(i).toString());
            }
        }
    }
    public static ArrayList<Instrument> instrumList = new ArrayList<>();

    public static void instrum(int line, String type, Pair<String, Object>...args) {
        ArrayList<Pair<String, Object>> pairList = new ArrayList<>();
        Collections.addAll(pairList, args);
        Instrument i = new Instrument(line, type, pairList);
        instrumList.add(i);
    }
}
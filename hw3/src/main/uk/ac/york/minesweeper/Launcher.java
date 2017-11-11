package uk.ac.york.minesweeper;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.File;
import java.util.ArrayList;

public class Launcher {
    public String className;
    public File configFile;
    public ArrayList<String> configList = new ArrayList<String>();

    public Launcher(String className, File configFile) {
        this.className = className;
        this.configFile = configFile;
    }

    public void parseConfigFile() {

    }

    public void someShit(ClassPool cp) {
        CtClass c = null;
        try {
            c = cp.get(this.className);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        ClassPool cp = new ClassPool();
    }
}
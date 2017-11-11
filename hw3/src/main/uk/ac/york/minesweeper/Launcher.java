package uk.ac.york.minesweeper;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javassist.*;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.ClassFile;

import java.io.File;
import java.io.IOException;
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

    public void some(ClassPool cp) {
        CtClass c = null;
        try {
            c = cp.get(this.className);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        // AMC
        for (CtField field : c.getFields()) {
            int mod = field.getModifiers();
            if (mod == Modifier.PRIVATE) field.setModifiers(Modifier.PUBLIC);
            else field.setModifiers(Modifier.PRIVATE);
        }

        for (CtMethod method : c.getMethods()) {
            int mod = method.getModifiers();
            if (mod == Modifier.PRIVATE) method.setModifiers(Modifier.PUBLIC);
            else method.setModifiers(Modifier.PRIVATE);
        }

        // JSD
        for (CtField field : c.getFields()) {
            int mod = field.getModifiers();
            if (mod == Modifier.STATIC) field.setModifiers(mod ^ Modifier.STATIC);
        }

        for (CtMethod method : c.getMethods()) {
            int mod = method.getModifiers();
            if (mod == Modifier.STATIC) method.setModifiers(mod ^ Modifier.STATIC);
        }

        // JSI
        for (CtField field : c.getFields()) {
            int mod = field.getModifiers();
            if (mod != Modifier.STATIC) field.setModifiers(Modifier.STATIC);
        }

        for (CtMethod method : c.getMethods()) {
            int mod = method.getModifiers();
            if (mod != Modifier.STATIC) method.setModifiers(Modifier.STATIC);
        }

        // AOR
        for (CtMethod method : c.getMethods()) {
            
        }

    }


    public static void main(String[] args) {
        ClassPool cp = ClassPool.getDefault();
    }
}
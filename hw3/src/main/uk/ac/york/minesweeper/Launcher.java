package uk.ac.york.minesweeper;

import javassist.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Launcher {
    public String className;
    public File configFile;
    public ArrayList<String> configList = new ArrayList<String>();

    public Launcher(String className, File configFile) {
        this.className = className;
        this.configFile = configFile;
    }

    public void mutationAMC(CtClass c) {
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
    }

    public void mutationIOD(CtClass c) {
        for (CtMethod method: c.getMethods()) {
            if (c != method.getDeclaringClass()) {
                try {
                    c.removeMethod(method);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void mutationJDC(CtClass c) {
        for (CtConstructor constructor : c.getConstructors()) {
            if (constructor.isConstructor()) {
                try {
                    c.removeConstructor(constructor);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void mutationJSD(CtClass c) {
        for (CtField field : c.getFields()) {
            int mod = field.getModifiers();
            field.setModifiers(mod & ~Modifier.STATIC);
        }
        for (CtMethod method : c.getMethods()) {
            int mod = method.getModifiers();
            method.setModifiers(mod & ~Modifier.STATIC);
        }
    }

    public void mutationJSI(CtClass c) {
        for (CtField field : c.getFields()) {
            int mod = field.getModifiers();
            if (mod != (mod | Modifier.STATIC)) field.setModifiers(Modifier.STATIC);
        }
        for (CtMethod method : c.getMethods()) {
            int mod = method.getModifiers();
            if (mod != (mod | Modifier.STATIC)) method.setModifiers(Modifier.STATIC);
        }
    }

    public void mutationOMD(CtClass c) {
        for (CtMethod method : c.getMethods()) {
            String methodName = method.getName();
            for (CtMethod otherMethod : c.getMethods()) {
                if (methodName.equalsIgnoreCase(otherMethod.getName())) {
                    try {
                        c.removeMethod(otherMethod);
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void parseConfigFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(""));
        while(sc.hasNextLine()) {
            String line = "";
            
        }
    }

    public void writeToFile(String newFileName, CtClass c) {
        try {
            c.writeFile("src/" + newFileName);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClassPool cp = ClassPool.getDefault();
        CtClass c = null;
        Launcher l = new Launcher(TemplateClass.class.getName(), new File("configFile.txt"));
        try {
            c = cp.get(l.className);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        l.mutationJSI(c);
        l.writeToFile("newclasslol", c);
    }
}
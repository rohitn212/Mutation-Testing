package uk.ac.york.minesweeper;

import javassist.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Launcher {

    public class MutationInfo {
        public String mutation = null;
        public String mutationTestName = null;
        public String className = null;

        public MutationInfo(String mutation, String mutationTestName,
                            String className) {
            this.mutation = mutation;
            this.mutationTestName = mutationTestName;
            this.className = className;
        }
    }

    // tested
    public void mutationAMC(CtClass c) {
        for (CtField field : c.getFields())
            field.setModifiers(Modifier.setPrivate(field.getModifiers()));
        for (CtMethod method : c.getMethods())
            method.setModifiers(Modifier.setPrivate(method.getModifiers()));
    }

    // tested
    public void mutationIOD(CtClass c) {
        for (CtMethod method : c.getMethods()) {
            if (c != method.getDeclaringClass()) {
                try {
                    c.removeMethod(method);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void mutationIPC(CtClass c) {
        for (CtConstructor constructor : c.getConstructors()) {
            if (constructor.isConstructor()) {
                try {
                    if (constructor.callsSuper()) {

                    }
                } catch (CannotCompileException e) {
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

    // tested
    public void mutationJSD(CtClass c) {
        for (CtField field : c.getFields())
            field.setModifiers(field.getModifiers() & ~Modifier.STATIC);
        for (CtMethod method : c.getMethods())
            method.setModifiers(method.getModifiers() & ~Modifier.STATIC);
    }

    // tested
    public void mutationJSI(CtClass c) {
        for (CtField field : c.getFields())
            field.setModifiers(field.getModifiers() | Modifier.STATIC);
        for (CtMethod method : c.getMethods())
            method.setModifiers(method.getModifiers() | Modifier.STATIC);
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


    public static MutationInfo[] parseConfigFile(File configFile) {
        // todo
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

    public static void main(String[] args) throws CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass c = null;
        Launcher l = new Launcher();
        final int noOfMutations = 5;
        MutationInfo[] mutationArr = new MutationInfo[noOfMutations];
        mutationArr = parseConfigFile(new File("configFile.txt"));
        Thread[] threadArr = new Thread[noOfMutations];
        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread();
            try {
                c = cp.get(mutationArr[i].className);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
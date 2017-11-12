package uk.ac.york.minesweeper;

import javassist.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Launcher {
    public final int noOfMutations = 5;

    public static class MutationInfo {
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

    public static MutationInfo[] parseConfigFile(File configFile, int noOfMutations) {
        Scanner s = null;
        MutationInfo[] mutationArr = new MutationInfo[noOfMutations];
        int count = 0;
        try {
            s = new Scanner(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (s.hasNext()) {
            String[] mutationInfo = s.next().split(",");
            mutationArr[count].mutation = mutationInfo[0];
            mutationArr[count].mutationTestName = mutationInfo[1];
            mutationArr[count].className = mutationInfo[2];
            count++;
        }
        return mutationArr;
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
        Launcher l = new Launcher();
        JUnitCore junit = new JUnitCore();
        MutationInfo[] mutationArr = parseConfigFile(new File("configFile.txt"), l.noOfMutations);
        Thread[] threadArr = new Thread[l.noOfMutations];
        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread();
            try {
                c = cp.get(mutationArr[i].className);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            try {
                junit.run(Request.method(Class.forName("MutationTest"),
                        mutationArr[i].mutationTestName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
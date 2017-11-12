package uk.ac.york.minesweeper;

import javafx.geometry.Pos;
import javassist.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Launcher {
    public final int noOfMutations = 4;

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

    public void callMutation(String mutationName, CtClass c) {
        switch(mutationName) {
            case "AMC":
                this.mutationAMC(c);
                break;
            case "IOD":
                this.mutationIOD(c);
                break;
            case "JDC":
                this.mutationJDC(c);
                break;
            case "JSD":
                this.mutationJSD(c);
                break;
            case "JSI":
                this.mutationJSI(c);
                break;
            default:
                throw new InputMismatchException();
        }
    }

    public void compareInstrumLists(ArrayList<TemplateClass.Instrument> oldInstrumList,
                                    ArrayList<TemplateClass.Instrument> newInstrumList) {
        if (oldInstrumList == null || newInstrumList == null) throw new NullPointerException();
        int count = 0;
        while (count < oldInstrumList.size() && count < newInstrumList.size()) {
            if (oldInstrumList.get(count) != newInstrumList.get(count)) {
                System.out.println("Change detected");
                System.out.println("premutation values: \n");
                oldInstrumList.get(count).printInstrument();
                System.out.println("postmutation values: \n");
                newInstrumList.get(count).printInstrument();
            }
            count++;
        }
    }

    // tested
    public void mutationAMC(CtClass c) {
        for (CtField field : c.getDeclaredFields())
            field.setModifiers(Modifier.setPrivate(field.getModifiers()));
        for (CtMethod method : c.getDeclaredMethods())
            method.setModifiers(Modifier.setPrivate(method.getModifiers()));
    }

    // tested
    public void mutationIOD(CtClass c) {
        for (CtMethod method : c.getDeclaredMethods()) {
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
        for (CtConstructor constructor : c.getDeclaredConstructors()) {
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
        for (CtField field : c.getDeclaredFields())
            field.setModifiers(field.getModifiers() & ~Modifier.STATIC);
        for (CtMethod method : c.getDeclaredMethods())
            method.setModifiers(method.getModifiers() & ~Modifier.STATIC);
    }

    // tested
    public void mutationJSI(CtClass c) {
        for (CtField field : c.getDeclaredFields())
            field.setModifiers(field.getModifiers() | Modifier.STATIC);
        for (CtMethod method : c.getMethods())
            method.setModifiers(method.getModifiers() | Modifier.STATIC);
    }

    public void mutationOMD(CtClass c) {
        for (CtMethod method : c.getDeclaredMethods()) {
            String methodName = method.getName();
            for (CtMethod otherMethod : c.getDeclaredMethods()) {
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
            mutationArr[count] = new MutationInfo(mutationInfo[0], mutationInfo[1],
                    mutationInfo[2]);
            count++;
        }
        return mutationArr;
    }

    public void writeToClass(String newFileName, CtClass c) {
        try {
//            System.out.println(c.getName());
            c.setName(newFileName);
//            System.out.println(c.getName());
            c.toClass();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClassPool cp = ClassPool.getDefault();
        CtClass c;
        Launcher l = new Launcher();
        JUnitCore junit = new JUnitCore();
        MutationInfo[] mutationArr = parseConfigFile(new File("configFile.txt"), l.noOfMutations);
        Thread[] threadArr = new Thread[l.noOfMutations];
        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread();
            try {
                c = cp.get(mutationArr[i].className);
                junit.run(Request.method(Class.forName(PreMutationTests.class.getName()),
                        mutationArr[i].mutationTestName));
                ArrayList<TemplateClass.Instrument> oldInstrumList = TemplateClass.instrumList;

                TemplateClass.instrumList.clear();
                l.callMutation(mutationArr[i].mutation, c);
                l.writeToClass(mutationArr[i].mutation + "Mutation", c);

                junit.run(Request.method(Class.forName(PostMutationTests.class.getName()),
                        mutationArr[i].mutationTestName));
                ArrayList<TemplateClass.Instrument> newInstrumList = TemplateClass.instrumList;
                l.compareInstrumLists(oldInstrumList, newInstrumList);
                threadArr[i].join();
            } catch (ClassNotFoundException | NotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
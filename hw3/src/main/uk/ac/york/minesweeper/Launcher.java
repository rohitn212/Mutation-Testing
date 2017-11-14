package uk.ac.york.minesweeper;

import javassist.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Launcher {
    public final int noOfMutations = 8;

    // class that stores each mutation
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

    public void callMutation(String mutationName, CtClass c) throws ClassNotFoundException {
        switch(mutationName) {
            case "AMC":
                this.mutationAMC(c);
                break;
            case "IOD":
                this.mutationIOD(c);
                break;
            case "IOR":
                this.mutationIOR(c);
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
            case "OMD":
                this.mutationOMD(c);
                break;
            case "OMR":
                this.mutationOMR(c);
                break;
            case "PMD":
                this.mutationPMD(c);
                break;
            default:
                throw new InputMismatchException();
        }
    }

    public boolean deepCompare(TemplateClass.Instrument i1, TemplateClass.Instrument i2) {
        if (i1.getPair().size() != i2.getPair().size()) {
            return false;
        }
        else {
            for (int i=0; i<i1.getPair().size(); i++) {
                if (i1.getPair().get(i).getKey().equals(i2.getPair().get(i).getKey())
                    && i1.getPair().get(i).getValue().equals(i2.getPair().get(i).getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void compareInstrumLists(ArrayList<TemplateClass.Instrument> oldInstrumList,
                                    ArrayList<TemplateClass.Instrument> newInstrumList) {
        if (oldInstrumList == null || newInstrumList == null) throw new NullPointerException();
        int count = 0;

        // todo: deep compare instead of reference compare
        while (count < oldInstrumList.size() && count < newInstrumList.size()) {
            if (!deepCompare(oldInstrumList.get(count), newInstrumList.get(count))) {
                System.out.println("\nChange detected");
                System.out.println("premutation values: ");
                oldInstrumList.get(count).printInstrument();
                System.out.println("\npostmutation values: ");
                newInstrumList.get(count).printInstrument();
            }
            count++;
        }
    }

    // Tested
    public void mutationAMC(CtClass c) {
        for (CtField field : c.getDeclaredFields())
            field.setModifiers(Modifier.setPrivate(field.getModifiers()));
        for (CtMethod method : c.getDeclaredMethods())
            method.setModifiers(Modifier.setPrivate(method.getModifiers()));
    }

    // todo:testing
    public void mutationIOD(CtClass c) {
//        try {
//            HashSet<String> set = new HashSet<>();
//            if (c.getSuperclass() != null) {
//                for (CtMethod method: c.getSuperclass().getDeclaredMethods())
//                    set.add(method.getName());
//                for (CtMethod method: c.getDeclaredMethods()) {
//                    if (set.contains(method.getName()))
//                        c.removeMethod(method);
//                }
//            }
//
//            /*
//            CtClass c2 = c.getSuperclass();
//            for (CtMethod m1 : c.getDeclaredMethods()) {
//                for (CtMethod m2 : c2.getDeclaredMethods()) {
//                    if (m1.equals(m2)) {
//                        c.removeMethod(m1);
//                    }
//                }
//            }
//            */
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        }
    }


    // todo:testing
    public void mutationIOR(CtClass c) {
        try {
            StringBuilder newName = new StringBuilder("newMethodName");
            HashSet<String> set = new HashSet<>();
            int count = 0;
            for (CtMethod method: c.getSuperclass().getDeclaredMethods())
                set.add(method.getName());
            for (CtMethod method: c.getDeclaredMethods()) {
                if (set.contains(method.getName()))
                    c.getSuperclass().getDeclaredMethod(method.getName()).
                            setName(newName.append(++count).toString());
            }
            /*
            CtClass c2 = c.getSuperclass();
            for (CtMethod m1 : c.getDeclaredMethods()) {
                for (CtMethod m2 : c2.getMethods()) {
                    if (m1.equals(m2)) {
                        c2.getDeclaredMethod(m2.getName()).setName("newMethodName");
                    }
                }
            }
            */
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }


    // Tested
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

    // Tested
    public void mutationJSD(CtClass c) {
        for (CtField field : c.getDeclaredFields())
            field.setModifiers(field.getModifiers() & ~Modifier.STATIC);
        for (CtMethod method : c.getDeclaredMethods())
            method.setModifiers(method.getModifiers() & ~Modifier.STATIC);
    }

    // Tested
    public void mutationJSI(CtClass c) {
        for (CtField field : c.getDeclaredFields())
            field.setModifiers(field.getModifiers() | Modifier.STATIC);
        for (CtMethod method : c.getMethods())
            method.setModifiers(method.getModifiers() | Modifier.STATIC);
    }

    public void mutationOMD(CtClass c) {
        HashSet<String> set = new HashSet<>();
        for (CtMethod method : c.getDeclaredMethods()) {
            if (!set.add(method.getName())) {
                try {
                    c.removeMethod(method);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Tested
    public void mutationOMR(CtClass c) {
        HashSet<String> set = new HashSet<String>();
        for (CtMethod method : c.getDeclaredMethods()) {
            try {
                if (method.getParameterTypes().length == 0)
                    if (!set.add(method.getName()))
                        method.setBody("return " + method.getName() + "();");
            } catch (NotFoundException | CannotCompileException e) {
                e.printStackTrace();
            }
        }
    }

    // todo:testing
    public void mutationPMD(CtClass c) {
//        try {
//            for (CtField field : c.getDeclaredFields()) {
//                if (field.getType().getSuperclass() != null)
//                    field.setType(field.getType().getSuperclass());
//            }
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        }
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
            c.setName(newFileName);
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
                ArrayList<TemplateClass.Instrument> oldInstrumList = TemplateClass.copyInstrumList();
                TemplateClass.instrumList.clear();
                l.callMutation(mutationArr[i].mutation, c);
                l.writeToClass(mutationArr[i].mutation + "Mutation", c);
                junit.run(Request.method(Class.forName(PostMutationTests.class.getName()),
                        mutationArr[i].mutationTestName));
                l.compareInstrumLists(oldInstrumList, TemplateClass.instrumList);
                threadArr[i].join();
            } catch (ClassNotFoundException | NotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
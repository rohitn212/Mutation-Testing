package uk.ac.york.minesweeper;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kobebryant1624 on 08/10/17.
 */
public class Parser {

    public static CompilationUnit getAST() {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);
        String unitName = "MinesweeperFrame.java";
        parser.setUnitName(unitName);
        String[] sources = {"src/main/uk/ac/york/minesweeper/"};
        String[] classpath = {};
        parser.setEnvironment(classpath, sources, new String[]{"UTF-8"}, true);
        parser.setSource(readFileToString(sources[0] + "MinesweeperFrame.java").toCharArray());
        return (CompilationUnit) parser.createAST(null);
    }

    public static void main(String[] args) {
        CompilationUnit cu = getAST();
    }


    public static String readFileToString(String str) {
        String retStr = "";
        return retStr;
    }
}


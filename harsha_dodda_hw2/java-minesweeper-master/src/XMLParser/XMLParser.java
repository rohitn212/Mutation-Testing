import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XMLParser {

    public static void parse(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("sourcefile");
        for(int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if(nNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element lElement = (Element)nNode;
            NodeList lList = lElement.getElementsByTagName("line");
            String className = lElement.getAttribute("name");
            if(className.equals("Images.java")) {
                break;
            }
            for(int j = 0; j < lList.getLength(); j++) {
                Node lNode = lList.item(j);
                if(nNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element listElement = (Element)lNode;
                int nrValue = Integer.parseInt(listElement.getAttribute("nr"));
                int ciValue = Integer.parseInt(listElement.getAttribute("ci"));
                if(ciValue > 0) {
                    String fileName = xmlFile.toString().replace("src/XMLFiles/", "").replace(".xml", "").replace("_jacocoTestReport", "");
                    String result = nrValue + " " + fileName + " " + className;
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter("mapInput.txt", true))) {
                        bw.write(result);
                        bw.newLine();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
        File xmlFolder = new File("src/XMLFiles");
        File[] xmlFiles = xmlFolder.listFiles();
        for (File f: xmlFiles) {
            parse(f);
        }

    }
}
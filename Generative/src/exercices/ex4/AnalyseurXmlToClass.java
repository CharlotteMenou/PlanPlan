package exercices.ex4;

import exercices.ex4.parfeur.A;
import exercices.ex4.parfeur.B;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnalyseurXmlToClass {

    private List<A> aInstances;
    private List<B> bInstances;

    public AnalyseurXmlToClass() {
        this.aInstances = new ArrayList<>();
        this.bInstances = new ArrayList<>();
    }

    public void parseXMLFile(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();

            analyseAElements(document);

            analyseBElements(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void analyseAElements(Document document) {
        NodeList aList = document.getElementsByTagNameNS("http://www.example.com", "A");

        for (int i = 0; i < aList.getLength(); i++) {
            Node aNode = aList.item(i);

            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                Element aElement = (Element) aNode;

                A aInstance = new A();
                aInstance.id = aElement.getAttribute("id");
                aInstance.value = aElement.getAttribute("value");

                // Récupère les B contenus dans A
                NodeList bList = aElement.getElementsByTagNameNS("http://www.example.com", "A.theBs.B");
                for (int j = 0; j < bList.getLength(); j++) {
                    Node bNode = bList.item(j);

                    if (bNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bElement = (Element) bNode;
                        B bInstance = new B();
                        bInstance.name = bElement.getAttribute("name");
                        bInstance.val = bElement.getAttribute("val");
                        aInstance.theBs.add(bInstance);

                        bInstances.add(bInstance);
                    }
                }

                aInstances.add(aInstance);
            }
        }
    }

    private void analyseBElements(Document document) {
        NodeList bList = document.getElementsByTagNameNS("http://www.example.com", "B");

        for (int i = 0; i < bList.getLength(); i++) {
            Node aNode = bList.item(i);

            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                Element aElement = (Element) aNode;

                B bInstance = new B();
                bInstance.name = aElement.getAttribute("name");
                bInstance.val = aElement.getAttribute("val");

                bInstances.add(bInstance);
            }
        }
    }

    public List<A> getAInstances() {
        return aInstances;
    }

    public List<B> getbInstances() {
        return bInstances;
    }

    public static void main(String[] args) {
        AnalyseurXmlToClass analyseur = new AnalyseurXmlToClass();
        analyseur.parseXMLFile("src/exercices/ex4/test.xml");

        // Affichage des instances de A
        System.out.println("Instances de A :");
        for (A a : analyseur.getAInstances()) {
            System.out.println("A -> ID: " + a.id + ", \n\tValue: " + a.value);
            System.out.println("\ttheBS : [ ");
            for (B b : a.theBs) {
                System.out.println("\t\tB -> Name: " + b.name + ", \n\t\t\tVal: " + b.val);
            }
            System.out.println("\t]");
        }

        // Affichage des instances de B
        System.out.println("\nInstances de B :");
        for (B b : analyseur.getbInstances()) {
            System.out.println("B -> Name: " + b.name + ", \n\tVal: " + b.val);
        }
    }
}

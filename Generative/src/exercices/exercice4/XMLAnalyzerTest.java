package exercices.exercice4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

public class XMLAnalyzerTest {
    private GenericXMLAnalyzer analyzer;
    private static final String XML_DIR = "src/exercices/exercice4/"; // Dossier contenant vos fichiers XML

    public static void main(String[] args) {
        XMLAnalyzerTest test = new XMLAnalyzerTest();
        test.testFile("voiture.xml");  // Pour tester le fichier voiture.xml
    }

    public XMLAnalyzerTest() {
        this.analyzer = new GenericXMLAnalyzer();
    }

    public void testFile(String filename) {
        try {
            System.out.println("\nTesting file: " + filename);
            System.out.println("------------------------");

            // Charger le fichier XML
            File xmlFile = new File(XML_DIR + filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // Obtenir l'élément racine et tous ses enfants directs
            Element root = doc.getDocumentElement();
            NodeList children = root.getChildNodes();

            // Analyser chaque élément
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i) instanceof Element) {
                    Element element = (Element) children.item(i);
                    Object result = analyzer.analyze(element);
                    displayResult(result);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (Exception e) {
            System.err.println("Error processing file " + filename);
            e.printStackTrace();
        }
    }

    private void displayResult(Object result) {
        System.out.println("\nCreated instance of: " + result.getClass().getSimpleName());

        // Afficher tous les champs et leurs valeurs
        for (Field field : result.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(result);
                System.out.println(field.getName() + ": " + formatValue(value));
            } catch (IllegalAccessException e) {
                System.err.println("Cannot access field: " + field.getName());
            }
        }
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof List) {
            List<?> list = (List<?>) value;
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(list.get(i).toString());
            }
            return sb.append("]").toString();
        }
        return value.toString();
    }
}

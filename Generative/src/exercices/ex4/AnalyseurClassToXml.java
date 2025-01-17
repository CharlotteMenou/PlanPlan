package exercices.ex4;

import java.util.List;
import javax.xml.parsers.*;
        import javax.xml.transform.*;
        import javax.xml.transform.dom.*;
        import javax.xml.transform.stream.*;

import exercices.ex4.parfeur.A;
import exercices.ex4.parfeur.B;
import org.w3c.dom.*;

public class AnalyseurClassToXml {
    private List<A> aInstances;
    private List<B> bInstances;

    public AnalyseurClassToXml(List<A> aInstances, List<B> bInstances) {
        this.aInstances = aInstances;
        this.bInstances = bInstances;
    }

    public void generateXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Créer l'élément racine
            Element rootElement = document.createElementNS("http://www.example.com", "root");
            document.appendChild(rootElement);

            // Générer les éléments A
            for (A aInstance : aInstances) {
                Element aElement = document.createElement("parfeur:A");
                aElement.setAttribute("id", aInstance.id);
                aElement.setAttribute("value", aInstance.value);

                // Créer un conteneur pour les B de A
                Element theBsElement = document.createElement("parfeur:A.theBs");

                // Ajouter les B contenus dans A
                for (B bInstance : aInstance.theBs) {
                    Element bElement = document.createElement("parfeur:A.theBs.B");
                    bElement.setAttribute("name", bInstance.name);
                    bElement.setAttribute("val", bInstance.val);
                    theBsElement.appendChild(bElement);
                }

                aElement.appendChild(theBsElement);
                rootElement.appendChild(aElement);
            }

            // Générer les éléments B indépendants
            for (B bInstance : bInstances) {
                if (!isContainedInAnyA(bInstance)) {
                    Element bElement = document.createElement( "parfeur:B");
                    bElement.setAttribute("name", bInstance.name);
                    bElement.setAttribute("val", bInstance.val);
                    rootElement.appendChild(bElement);
                }
            }

            // Transformer le document en XML et l'afficher
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isContainedInAnyA(B b) {
        for (A a : aInstances) {
            if (a.theBs.contains(b)) {
                return true;
            }
        }
        return false;
    }
}
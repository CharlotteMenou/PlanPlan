package parfeur;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class XMLReader {
    private Document doc;

    public XMLReader(Document document) {
        this.doc = document;
    }

    public static XMLReader fromFile(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(filePath));
        return new XMLReader(doc);
    }

    public List<Object> read() throws Exception {
        List<Object> results = new ArrayList<>();

        Element rootElement = doc.getDocumentElement();

        NodeList children = rootElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (element.getLocalName().equals("A")) {
                    Object obj = constructElement(element);
                    if (obj != null) {
                        results.add(obj);
                    }
                }
            }
        }
        return results;
    }

    private Object constructElement(Element element) throws Exception {
        String className = element.getLocalName();
        Class<?> cls = Class.forName("parfeur." + className);
        Object instance = cls.getDeclaredConstructor().newInstance();

        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            String fieldName = attr.getNodeName();
            String value = attr.getNodeValue();
            setField(instance, fieldName, value);
        }

        if (className.equals("A")) {
            constructChildren(element, instance);
        }

        return instance;
    }

    private void constructChildren(Element aElement, Object aInstance) throws Exception {
        NodeList children = aElement.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;

                if (childElement.getLocalName().equals("A.theBs")) {
                    constructTheBs(childElement, aInstance);
                }
            }
        }
    }

    private void constructTheBs(Element theBsElement, Object aInstance) throws Exception {

        Field theBsField = aInstance.getClass().getDeclaredField("theBs");
        theBsField.setAccessible(true);
        List<Object> theBs = (List<Object>) theBsField.get(aInstance);


        NodeList bElements = theBsElement.getChildNodes();
        for (int i = 0; i < bElements.getLength(); i++) {
            Node node = bElements.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element bElement = (Element) node;

                if (bElement.getLocalName().equals("B")) {

                    Object bInstance = constructElement(bElement);
                    if (bInstance != null) {
                        theBs.add(bInstance);
                    }
                }
            }
        }
    }

    private void setField(Object obj, String fieldName, String value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        try {
            XMLReader reader = XMLReader.fromFile("src/parfeur/test.xml");
            List<Object> objects = reader.read();// Transforme les nœuds en objets.

            int nbA = 0;
            int nbB = 0;

            System.out.println(objects);

            for (Object obj : objects) {
                if (obj instanceof A) {
                    A a = (A) obj;
                    String varName = "a" + (++nbA);
                    System.out.println("\nA " + varName + " = new A(); \n"+varName+".setId(\""+a.getId()+"\");");
                    System.out.println(varName + ".setValue(\"" + a.getValue() + "\");");

                    for (B b : a.getTheBs()) {
                        String bVarName = "b" + (++nbB);
                        System.out.println("\nB " + bVarName + " = new B();");
                        System.out.println(bVarName + ".setName(\"" + b.getName() + "\");");
                        System.out.println(bVarName + ".setVal(" + b.getVal() + ");");
                        System.out.println(varName + ".getTheBs().add(" + bVarName + ");");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
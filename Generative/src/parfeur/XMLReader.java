package parfeur;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {
    public static void main(String[] args) throws Exception {
        File xmlFile = new File("src/parfeur/ex4.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        Element rootElement = doc.getDocumentElement();
        List<Object> instances = new ArrayList<>();

        NodeList children = rootElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                parseElement((Element) node, null, instances);
            }
        }

        for (Object obj : instances) {
            System.out.println(obj);
        }
    }

    private static void parseElement(Element element, Object parent, List<Object> instances) throws Exception {
        String tagName = element.getLocalName();
        if (tagName.contains(".")) {
            handleCollection(element, parent);
            return;
        }

        // Create class instance
        String className = "parfeur." + tagName;
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        if (instances != null) instances.add(instance);

        createAttributes(element, clazz, instance);

        checkChildNode(element, instance);

        if (parent != null) {
            addChildToParent(parent, instance);
        }
    }

    private static void checkChildNode(Element element, Object instance) throws Exception {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                parseElement((Element) node, instance, null);
            }
        }
    }

    private static void createAttributes(Element element, Class<?> clazz, Object instance) throws NoSuchFieldException, IllegalAccessException {
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();

            Field field = clazz.getDeclaredField(attrName);
            field.set(instance, attrValue);
        }
    }

    private static void handleCollection(Element element, Object parent) throws Exception {
        if (parent == null) return;

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                parseElement((Element) node, parent, null);
            }
        }
    }

    private static void addChildToParent(Object parent, Object child) throws Exception {
        Class<?> parentClass = parent.getClass();
        for (Field field : parentClass.getDeclaredFields()) {
            if (List.class.isAssignableFrom(field.getType())) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                Class<?> itemType = (Class<?>) listType.getActualTypeArguments()[0];
                if (itemType.isInstance(child)) {
                    field.setAccessible(true);
                    List<Object> list = (List<Object>) field.get(parent);
                    list.add(child);
                }
            }
        }
    }
}

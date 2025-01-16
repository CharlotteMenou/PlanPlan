package exercices.exercice4;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GenericXMLAnalyzer {
    private static final String BASE_PACKAGE = "exercices.exercice4.";

    public Object analyze(Element element) throws Exception {
       // 1. Obtenir le nom de classe à partir de la balise XML avec le package complet
        String className = element.getTagName().replace("parfeur:", "");
        String fullClassName = BASE_PACKAGE + className;  // Ajouter le package

        // Maintenant on utilise le nom complet de la classe
        Class<?> cls = Class.forName(fullClassName);
        Object instance = cls.getDeclaredConstructor().newInstance();

        // 2. Traiter tous les attributs XML
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            String fieldName = attr.getNodeName();
            String value = attr.getNodeValue();

            // Trouver et configurer le champ correspondant
            setFieldValue(instance, fieldName, value);
        }

        // 3. Traiter tous les éléments enfants
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                Element childElement = (Element) child;
                String fieldName = childElement.getTagName().replace(className + ".", "").replace("parfeur:", "");

                // Si c'est une collection
                if (childElement.hasChildNodes()) {
                    List<Object> childObjects = new ArrayList<>();
                    NodeList subChildren = childElement.getChildNodes();

                    for (int j = 0; j < subChildren.getLength(); j++) {
                        if (subChildren.item(j) instanceof Element) {
                            Object childObj = analyze((Element) subChildren.item(j));
                            childObjects.add(childObj);
                        }
                    }

                    setFieldValue(instance, fieldName, childObjects);
                }
            }
        }

        return instance;
    }

    private void setFieldValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        // Conversion automatique des types
        if (field.getType() == int.class || field.getType() == Integer.class) {
            field.set(instance, Integer.parseInt(value.toString()));
        } else if (field.getType() == List.class) {
            field.set(instance, value);
        } else {
            field.set(instance, value);
        }
    }
}
package exercices.exercice1;

import java.lang.reflect.Modifier;

public class Exercice1Test {
    static class Inner1 {
    }
    static interface innerInterf1 {
    }
    private class Inner2 {
        class InnerInner1 implements innerInterf1, interf1 {
        }
        class InnerInner2 extends InnerInner1 {
        }
    }
    public static String classRepresentation(Class<?> cls) {
        StringBuilder result = new StringBuilder();
        buildClassRepresentation(cls, result, 0); // Appeler avec un niveau d'indentation initial
        return result.toString();
    }

    private static void buildClassRepresentation(Class<?> cls, StringBuilder result, int indentLevel) {
        String indent = "    ".repeat(indentLevel); // Générer l'indentation

        // Récupérer les modificateurs
        int modifiers = cls.getModifiers();
        String modifierStr = Modifier.toString(modifiers);
        if (!modifierStr.isEmpty()) {
            result.append(indent).append(modifierStr).append(" ");
        }

        // Vérifier si c'est une interface ou une classe
        if (cls.isInterface()) {
            result.append("interface ").append(cls.getSimpleName());
        } else {
            // Gérer la superclasse ou les interfaces
            Class<?> superClass = cls.getSuperclass();
            if (superClass != null && !superClass.getSimpleName().equals("Object")) {
                result.append("class ").append(cls.getSimpleName())
                        .append(" extends ").append(superClass.getSimpleName());
            } else {
                result.append("class ").append(cls.getSimpleName());
            }
        }

        // Gérer les interfaces implémentées
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            result.append(" implements ");
            for (int i = interfaces.length - 1; i >= 0 ; i--) {
                result.append(interfaces[i].getSimpleName());
                if (i > 0) {
                    result.append(", ");
                }
            }
        }

        result.append(" {\n");

        // Gérer les classes internes avec une indentation supplémentaire
        Class<?>[] innerClasses = cls.getDeclaredClasses();
        for (int i = innerClasses.length - 1; i >= 0 ; i--) {

            buildClassRepresentation(innerClasses[i], result, indentLevel + 1);
        }

        result.append(indent).append("}\n"); // Fermeture de la classe ou de l'interface
    }

    public static void main(String[] args) {
        String codeRepresentation = classRepresentation(Exercice1Test.class);
        System.out.println(codeRepresentation);
    }

}


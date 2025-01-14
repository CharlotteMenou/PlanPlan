package exercices.exercice2;

import exercices.exercice1.interf1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class Exercice2 {
    static class Inner1 {
        private int value;
        public String name;
    }

    static interface innerInterf1 {
    }

    private class Inner2 {
        protected double score;
        private List<Exercice2> exercice2List;

        class InnerInner1 implements innerInterf1, interf1 {
            private String description;
        }

        class InnerInner2 extends InnerInner1 {
            public int count;
        }
    }

    public static String classRepresentation(Class<?> cls) {
        StringBuilder result = new StringBuilder();
        buildClassRepresentation(cls, result, 0);
        return result.toString();
    }

    private static void buildClassRepresentation(Class<?> cls, StringBuilder result, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        int modifiers = cls.getModifiers();
        String modifierStr = Modifier.toString(modifiers);
        if (!modifierStr.isEmpty()) {
            result.append(indent).append(modifierStr).append(" ");
        }

        if (cls.isInterface()) {
            result.append("interface ").append(cls.getSimpleName());
        } else {
            Class<?> superClass = cls.getSuperclass();
            if (superClass != null && !superClass.getSimpleName().equals("Object")) {
                result.append("class ").append(cls.getSimpleName())
                        .append(" extends ").append(superClass.getSimpleName());
            } else {
                result.append("class ").append(cls.getSimpleName());
            }
        }

        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            result.append(" implements ");
            for (int i = interfaces.length - 1; i >= 0; i--) {
                result.append(interfaces[i].getSimpleName());
                if (i > 0) {
                    result.append(", ");
                }
            }
        }

        result.append(" {\n");

        // Ajouter les champs (attributs)
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            result.append(indent).append("    ");
            int fieldModifiers = field.getModifiers();
            String fieldModifierStr = Modifier.toString(fieldModifiers);
            if (!fieldModifierStr.isEmpty()) {
                result.append(fieldModifierStr).append(" ");
            }
            result.append(field.getType().getSimpleName())
                    .append(" ")
                    .append(field.getName())
                    .append(";\n");
        }

        if (fields.length > 0 && cls.getDeclaredClasses().length > 0) {
            result.append("\n");
        }


        Class<?>[] innerClasses = cls.getDeclaredClasses();
        for (int i = innerClasses.length - 1; i >= 0; i--) {
            buildClassRepresentation(innerClasses[i], result, indentLevel + 1);
        }

        result.append(indent).append("}\n");
    }

    public static void main(String[] args) {
        String codeRepresentation = classRepresentation(Exercice2.class);
        System.out.println(codeRepresentation);
    }
}
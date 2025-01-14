package exercices.exercice1;  // Notez la minuscule

import java.lang.reflect.Modifier;

public class Exercice1 {
    static class Inner1 {
    }

    static interface innerInterf1 {
    }
    public interface interf1 {
    }


    private static interface innerInterf2{

    }

    private class Inner2 {
        class InnerInner1 implements innerInterf1, interf1 {
        }

        class InnerInner2 extends InnerInner1 {
        }
    }

    public static void classRepresentation(Class<?> cls) {
        StringBuilder result = new StringBuilder();

        // Package
        Package pkg = cls.getPackage();
        if (pkg != null) {
            result.append("package ").append(pkg.getName().toLowerCase()).append(";\n\n");
        }

        // Structure
        printStructure(cls, result, "");

        System.out.println(result.toString());
    }

    private static void printStructure(Class<?> cls, StringBuilder sb, String indent) {
        String modifiers = Modifier.toString(cls.getModifiers());
        if (!modifiers.isEmpty()) {
            sb.append(indent).append(modifiers).append(" ");
        }

        if (!cls.isInterface()) {
            sb.append("class ");
        }

        sb.append(cls.getSimpleName());

        // Interfaces
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            sb.append(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(interfaces[i].getSimpleName());
            }
        }

        // Superclass (si ce n'est pas Object)
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            sb.append(" extends ").append(superClass.getSimpleName());
        }

        sb.append(" {\n");

        // Classes imbriquées
        for (Class<?> innerClass : cls.getDeclaredClasses()) {
            printStructure(innerClass, sb, indent + "    ");
        }

        sb.append(indent).append("}\n");
    }
}

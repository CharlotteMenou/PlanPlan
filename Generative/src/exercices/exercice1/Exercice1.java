package exercices.exercice1;

import java.lang.reflect.Modifier;

public class Exercice1 {
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

        Class<?> superClass = cls.getSuperclass();
        if(cls.isInterface()){
            result.append(Modifier.toString(cls.getModifiers()) + " ").append(cls.getSimpleName())
                    .append(" {\n");
        }else if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            result.append(Modifier.toString(cls.getModifiers()) + "class ").append(cls.getSimpleName())
                    .append(" extends ").append(superClass.getSimpleName())
                    .append(" {\n");
        } else {
            Class<?>[] interfaces = cls.getInterfaces();
            if (interfaces.length > 0) {
                result.append(Modifier.toString(cls.getModifiers()) + " class ").append(cls.getSimpleName())
                        .append(" implements ");
                for (int i = 0; i < interfaces.length; i++) {
                    result.append(interfaces[i].getSimpleName());
                    if (i < interfaces.length - 1) {
                        result.append(", ");
                    }
                }
                result.append(" {\n");
            } else {
                result.append(Modifier.toString(cls.getModifiers()) + " class ").append(cls.getSimpleName()).append(" {\n");
            }
        }

        Class<?>[] innerClasses = cls.getDeclaredClasses();
        if (innerClasses.length > 0) {
            for (Class<?> innerClass : innerClasses) {
                result.append(classRepresentation(innerClass));
            }
        }

        result.append("}\n");
        return result.toString();
    }

    public static void main(String[] args) {
        String codeRepresentation = classRepresentation(Exercice1.class);
        System.out.println(codeRepresentation);
    }
}
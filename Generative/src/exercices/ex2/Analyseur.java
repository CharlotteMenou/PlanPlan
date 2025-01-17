package exercices.ex2;

import exercices.ex1.Exercice1;
import exercices.ex2.yuml.YuAsso;
import exercices.ex2.yuml.YuClass;
import exercices.ex2.yuml.YuNoAsso;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Analyseur {
    static private String classRepresentation(Class<?> cls, Set<Class<?>> visited, Set<Class<?>> unbounds) {
        StringBuilder clsrep = new StringBuilder();
        YumlVisitorImpl visitor = new YumlVisitorImpl();
        List<YuAsso> associations = new ArrayList<>();

        // remove cls from the unbounds since cls is visited here
        unbounds.remove(cls);

        // add cls as a new visited one
        visited.add(cls);

        int modifiers = cls.getModifiers();

        if (Modifier.isPublic(modifiers)) {
            clsrep.append("public ");
        }

        if (Modifier.isStatic(modifiers)) {
            if ((!cls.isInterface()) || (cls.getDeclaringClass() != null))
                clsrep.append("static ");
        }

        String clskw;
        if (cls.isInterface()) {
            clskw = "interface";
        } else {
            clskw = "class";
            clsrep.append(clskw).append(" ").append(cls.getSimpleName()).append(" ");
            YuClass y1 = new YuClass(cls.getSimpleName());
            YuNoAsso asso = new YuNoAsso(y1);
            associations.add(asso);
        }


        // Manage the superclass (if any)
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            clsrep.append(" extends ").append(superclass.getSimpleName());
            visited.add(superclass);
        }

        // Manage the implemented interfaces (if any)
        Class<?>[] interfaces = cls.getInterfaces();
        for (Class<?> i : interfaces) {
            clsrep.append(" implements ").append(i.getSimpleName()).append(" ");
            visited.add(i);
        }

        clsrep.append("{\n");

        // Manage the arguments of the class
        Field[] arguments = cls.getDeclaredFields();
        for (Field f : arguments) {
            clsrep.append("\n\t");
            f.setAccessible(true);
            if (Modifier.isPrivate(f.getModifiers())) {
                clsrep.append(" private ");
            }
            clsrep.append(f.getGenericType()).append(" ").append(f.getName()).append(";");
        }
        clsrep.append("\n\n");

        // Manage the declared inner classes and interfaces
        Class<?>[] innerClass = cls.getDeclaredClasses();
        for (Class<?> c: innerClass) {
            visited.add(c);
            clsrep.append("\t").append(classRepresentation(c, visited, unbounds));
        }

        clsrep.append("\n}\n");

        for(YuAsso a: associations) {
            a.accept(visitor);
        }

        //return clsrep.toString();
        return visitor.getResult();
    }


    public static void classRepresentation(Class<?> cls) {
        Set<Class<?>> unbounds = new HashSet<>();
        Set<Class<?>> visited = new HashSet<>();

        String result = classRepresentation(cls, visited, unbounds);

        // classRepresentation running may add classes for which a class representation is needed

        System.out.println(result);

        for (Class<?> c : unbounds)

            // Is the class in the right package ?
            if (c.getPackage() == Exercice1.class.getPackage()) {

                // ok, build the string representation for it
                result = classRepresentation(c, visited, unbounds);

                System.out.println(result);
            }
    }

    public interface I1 {

    }

    public class Test1 {

    }

    public static void main(String[] args) {
        classRepresentation(Test.class);
    }
}

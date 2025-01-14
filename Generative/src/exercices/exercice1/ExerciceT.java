package exercices.exercice1;


import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class ExerciceT {

    static private String classRepresentation(Class<?> cls, Set<Class<?>> visited, Set<Class<?>> unbounds) {
        String clsrep = "";

        // remove cls from the unbounds since cls is visited here
        unbounds.remove(cls);

        // add cls as a new visited one
        visited.add(cls);

        int modifiers = cls.getModifiers();

        if (Modifier.isPublic(modifiers)) {
            clsrep = clsrep + "public ";
        }

        if (Modifier.isStatic(modifiers)) {
            if ((!cls.isInterface()) || (cls.getDeclaringClass() != null))
                clsrep = clsrep + "static ";
        }

        String clskw;
        if (cls.isInterface()) {
            clskw = "interface";
        } else {
            clskw = "class";
        }

        clsrep = clsrep + clskw + " " + cls.getSimpleName() + " ";

        // TODO manage the superclass (if any)


        // TODO manage the implemented interfaces (if any)


        clsrep += "{\n";

        // TODO manage the declared inner classes and interfaces


        clsrep += "\n}\n";

        return clsrep;
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

    public static void main(String[] args) {
        classRepresentation(Exercice1.class);
    }

}
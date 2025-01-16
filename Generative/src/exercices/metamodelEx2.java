package exercices;

import java.lang.reflect.Field;

public class metamodelEx2 {
    static void classRepresentation(Class<?> cls) {
        Class<?>[] classes = cls.getDeclaredClasses();
        System.out.println(classes);

        String s = "";
        for (Class<?> c : classes) {
            s = buildRep(c);
        }
        System.out.println(s);
    }

    private static String buildRep(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append(field.getGenericType());
        }
        return sb.toString();
    }

    private static String buildUML(String s) {

        return s;
    }
    public static void main(String[] args) {
        classRepresentation(Exercice2.class);
    }
}

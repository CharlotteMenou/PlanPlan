package exercices;

import exercices.Exercice1;
import exercices.interf1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class metamodelEx1 {
    static void classRepresentation(Class<?> cls) {
        String s = buildRep(cls);
        System.out.println(s);
    }

    private static String buildRep(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append(decodeModifier(cls.getModifiers()));
        if (cls.isInterface()){
            sb.append(" interface ");
        } else {
            sb.append(" class ");
        }
        sb.append(cls.getSimpleName());

        sb.append(buildSuperClass(cls));

        sb.append(buildInterfaces(cls));

        sb.append(" {\n");

        sb.append(buildFields(cls));

        sb.append(buildClass(cls));

        sb.append("\n}\n");
        return sb.toString();
    }

    static String buildFields(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            sb.append("\t");
            sb.append(decodeModifier(field.getModifiers()));
            sb.append(" ");
            sb.append(field.getType().getSimpleName());
            sb.append(" ");
            sb.append(field.getName());
            sb.append(";\n");
        }
        return sb.toString();
    }

    static String buildClass(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        Class<?>[] classes = cls.getDeclaredClasses();
        for (Class<?> c : classes) {
            sb.append(buildRep(c));
        }
        return sb.toString();
    }

    static String buildSuperClass(Class<?> cls){
        StringBuilder sb = new StringBuilder();
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class){
            sb.append(" extends ");
            sb.append(superClass.getSimpleName());
        }
        return sb.toString();
    }

    static String buildInterfaces(Class<?> cls){
        StringBuilder sb = new StringBuilder();
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0){
            sb.append(" implements ");
            for (int i = 0; i < interfaces.length; i++){
                sb.append(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1){
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

    private static String decodeModifier(Integer encodedModifier){
        if (Modifier.isPublic(encodedModifier)) {
            return "public";
        } else if (Modifier.isPrivate(encodedModifier)) {
            return "private";
        } else if (Modifier.isProtected(encodedModifier)) {
            return "protected";
        } else if (Modifier.isStatic(encodedModifier)){
            return "static";
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        classRepresentation(Exercice1.class);
        classRepresentation(interf1.class);
    }
}

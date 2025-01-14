package Test;

import java.util.List;

public class Exercice2 {
    class Room {
    List<Place> places;
    }
    class Place {
    }

    public static String represente(Class<?> cls) {
        String result = "    ".repeat(cls.getName().split("\\$").length - 1); // Indentation pour classes internes

        // Vérifie si c'est une interface ou une classe
        if (cls.isInterface()) {
            result += "interface ";
        } else {
            result += "class ";
        }

        // Ajoute le nom simple de la classe
        result += cls.getSimpleName();

        // Gestion des interfaces
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            result += " implements ";
            for (int i = 0; i < interfaces.length; i++) {
                if (i > 0) result += ", ";
                result += interfaces[i].getSimpleName();
            }
        }

        // Gestion de la classe parente
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            result += " extends " + superClass.getSimpleName();
        }

        result += " {\n";

        // Analyse des champs de la classe
        java.lang.reflect.Field[] fields = cls.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            result += "    " + getFieldRepresentation(field) + ";\n";
        }

        // Analyse récursive des classes internes
        Class<?>[] classes = cls.getDeclaredClasses();
        for (Class<?> cl : classes) {
            result += represente(cl); // Appel récursif
        }

        result += "}\n";
        return result;
    }

    /**
     * Retourne une représentation complète d'un champ, y compris ses types génériques.
     */
    private static String getFieldRepresentation(java.lang.reflect.Field field) {
        StringBuilder fieldRepresentation = new StringBuilder();

        // Ajouter le type du champ
        Class<?> fieldType = field.getType();
        fieldRepresentation.append(fieldType.getSimpleName());

        // Vérifie si le champ a des paramètres génériques
        java.lang.reflect.Type genericType = field.getGenericType();
        if (genericType instanceof java.lang.reflect.ParameterizedType) {
            java.lang.reflect.ParameterizedType parameterizedType = (java.lang.reflect.ParameterizedType) genericType;

            // Ajouter les types génériques
            fieldRepresentation.append("<");
            java.lang.reflect.Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < typeArguments.length; i++) {
                if (i > 0) fieldRepresentation.append(", ");
                fieldRepresentation.append(typeArguments[i].getTypeName().split("")[0]);
            }
            fieldRepresentation.append(">");
        }

        // Ajouter le nom du champ
        fieldRepresentation.append(" ").append(field.getName());

        return fieldRepresentation.toString();
    }


    public static void classRepresentation(Class<?> cls) {
        String result = "package " + cls.getPackage().getName() + ";\n\n";

        result += represente(cls);
        System.out.println(result);
    }

    public static void main(String[] args) {
        classRepresentation(Exercice2.class);
    }
}


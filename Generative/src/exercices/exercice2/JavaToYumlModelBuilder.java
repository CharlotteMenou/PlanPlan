package exercices.exercice2;

import java.lang.reflect.*;
import java.util.*;

class JavaToYumlModelBuilder {
    private Set<Class<?>> processedClasses = new HashSet<>();
    private YumlModel model = new YumlModel();
    private Map<Class<?>, YumlClass> classMap = new HashMap<>();

    public YumlModel buildModel(Class<?>... classes) {
        for (Class<?> cls : classes) {
            processClass(cls);
        }
        return model;
    }

    private void processClass(Class<?> cls) {
        if (processedClasses.contains(cls) ||
                cls.isPrimitive() ||
                cls.isArray() ||
                cls.getName().startsWith("java.") ||  // Ignore JDK classes
                cls.getName().startsWith("javax.") ||
                cls.getName().startsWith("sun.")) {
            return;
        }
        processedClasses.add(cls);

        // Create YUML class
        YumlClass yumlClass = new YumlClass(cls.getSimpleName());
        classMap.put(cls, yumlClass);
        model.addClass(yumlClass);

        // Process fields
        for (Field field : cls.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String visibility = getVisibilitySymbol(field.getModifiers());
                String type = getTypeName(field.getType());
                yumlClass.addAttribute(new YumlAttribute(visibility, field.getName(), type));

                // Process relationships
                processRelationship(cls, field);
            }
        }

        // Process methods
        for (Method method : cls.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers())) {
                processMethod(yumlClass, method);
            }
        }

        // Process inheritance
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            processClass(superClass);
            YumlClass yumlSuperClass = classMap.get(superClass);
            model.addRelation(new YumlRelation(YumlRelation.Type.INHERITANCE, yumlClass, yumlSuperClass));
        }
    }

    private void processMethod(YumlClass yumlClass, Method method) {
        String visibility = getVisibilitySymbol(method.getModifiers());
        YumlMethod yumlMethod = new YumlMethod(
                visibility,
                method.getName(),
                getTypeName(method.getReturnType())
        );

        for (Parameter param : method.getParameters()) {
            yumlMethod.addParameter(new YumlParameter(
                    param.getName(),
                    getTypeName(param.getType())
            ));
        }

        yumlClass.addMethod(yumlMethod);
    }

    private void processRelationship(Class<?> cls, Field field) {
        Class<?> fieldType = field.getType();
        if (!fieldType.isPrimitive() && !fieldType.isArray() &&
                !fieldType.getName().startsWith("java.") &&
                !fieldType.getName().startsWith("javax.") &&
                !fieldType.getName().startsWith("sun.")) {
            processClass(fieldType);
            YumlClass source = classMap.get(cls);
            YumlClass target = classMap.get(fieldType);

            YumlRelation relation = new YumlRelation(
                    YumlRelation.Type.ASSOCIATION,
                    source,
                    target
            );

            if (Collection.class.isAssignableFrom(fieldType)) {
                relation.setTargetMultiplicity("*");
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    Class<?> actualType = (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
                    processClass(actualType);
                    target = classMap.get(actualType);
                    relation.setTarget(target);
                }
            }

            relation.setLabel(field.getName());
            model.addRelation(relation);
        }
    }

    private String getVisibilitySymbol(int modifiers) {
        if (Modifier.isPublic(modifiers)) return "+";
        if (Modifier.isProtected(modifiers)) return "#";
        if (Modifier.isPrivate(modifiers)) return "-";
        return "~";
    }

    private String getTypeName(Class<?> type) {
        if (type.isArray()) {
            return getTypeName(type.getComponentType()) + "[]";
        }
        return type.getSimpleName();
    }
}

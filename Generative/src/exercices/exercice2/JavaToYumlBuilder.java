package exercices.exercice2;

import java.lang.reflect.*;
import java.util.*;

public class JavaToYumlBuilder {
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
                cls.getName().startsWith("java.") ||
                cls.getName().startsWith("javax.")) {
            return;
        }
        processedClasses.add(cls);

        YumlClass yumlClass = new YumlClass(cls.getSimpleName());
        classMap.put(cls, yumlClass);
        model.addClass(yumlClass);

        if (cls.isInterface()) {
            yumlClass.setInterfaceName("I" + cls.getSimpleName());
        }

        processFields(cls);
        processMethods(cls);
        processInheritance(cls);
    }

    private void processFields(Class<?> cls) {
        for (Field field : cls.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                processField(cls, field);
            }
        }
    }

    private void processField(Class<?> cls, Field field) {
        YumlClass yumlClass = classMap.get(cls);
        String visibility = getVisibilitySymbol(field.getModifiers());
        Class<?> fieldType = field.getType();

        yumlClass.addAttribute(new YumlAttribute(visibility, field.getName(), getTypeName(fieldType)));

        if (!fieldType.isPrimitive() && !fieldType.isArray() &&
                !fieldType.getName().startsWith("java.") &&
                !fieldType.getName().startsWith("javax.")) {

            processClass(fieldType);
            YumlClass targetClass = classMap.get(fieldType);

            YumlAssoc association;
            if (Collection.class.isAssignableFrom(fieldType)) {
                association = new YumlAggregation(yumlClass, targetClass);
                association.setTargetCardinality("*");

                // Traitement des types génériques pour les collections
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    Class<?> actualType = (Class<?>) ((ParameterizedType) genericType)
                            .getActualTypeArguments()[0];
                    processClass(actualType);
                    targetClass = classMap.get(actualType);
                }
            } else {
                association = new YumlSimpleAssoc(yumlClass, targetClass);
            }

            association.setLabel(field.getName());
            model.addAssociation(association);
        }
    }

    private void processMethods(Class<?> cls) {
        YumlClass yumlClass = classMap.get(cls);
        for (Method method : cls.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers())) {
                processMethod(yumlClass, method);
            }
        }
    }

    private void processMethod(YumlClass yumlClass, Method method) {
        String visibility = getVisibilitySymbol(method.getModifiers());
        YumlMethod yumlMethod = new YumlMethod(
                visibility,
                method.getName(),
                getTypeName(method.getReturnType())
        );

        // Traitement des paramètres
        for (Parameter param : method.getParameters()) {
            yumlMethod.addParameter(new YumlParameter(
                    param.getName(),
                    getTypeName(param.getType())
            ));
        }

        yumlClass.addMethod(yumlMethod);
    }

    private void processInheritance(Class<?> cls) {
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            processClass(superClass);
            YumlClass yumlSuperClass = classMap.get(superClass);
            YumlInheritance inheritance = new YumlInheritance(classMap.get(cls), yumlSuperClass);
            model.addAssociation(inheritance);
        }
    }

    private String getVisibilitySymbol(int modifiers) {
        if (Modifier.isPublic(modifiers)) return "+";
        if (Modifier.isProtected(modifiers)) return "#";
        if (Modifier.isPrivate(modifiers)) return "-";
        return "~"; // package private
    }

    private String getTypeName(Class<?> type) {
        if (type.isArray()) {
            return getTypeName(type.getComponentType()) + "[]";
        }
        return type.getSimpleName();
    }
}
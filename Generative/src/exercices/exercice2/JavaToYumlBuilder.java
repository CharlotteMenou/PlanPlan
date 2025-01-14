package exercices.exercice2;

import java.lang.reflect.*;
import java.util.*;

/**
 * Constructeur de modèle YUML à partir de classes Java.
 * Cette classe utilise la réflexion pour analyser les classes Java
 * et construire une représentation en modèle YUML.
 */
public class JavaToYumlBuilder {
    // Ensemble des classes déjà traitées pour éviter les boucles infinies
    private Set<Class<?>> processedClasses = new HashSet<>();
    // Le modèle YUML en cours de construction
    private YumlModel model = new YumlModel();
    // Correspondance entre les classes Java et leurs représentations YUML
    private Map<Class<?>, YumlClass> classMap = new HashMap<>();

    /**
     * Construit un modèle YUML à partir d'un ensemble de classes Java
     * @param classes Les classes à analyser
     * @return Le modèle YUML construit
     */
    public YumlModel buildModel(Class<?>... classes) {
        for (Class<?> cls : classes) {
            processClass(cls);
        }
        return model;
    }

    /**
     * Traite une classe Java pour l'ajouter au modèle YUML
     * Ignore les classes déjà traitées, les types primitifs, les tableaux
     * et les classes des packages java.* et javax.*
     */
    private void processClass(Class<?> cls) {
        if (processedClasses.contains(cls) ||
                cls.isPrimitive() ||
                cls.isArray() ||
                cls.getName().startsWith("java.") ||
                cls.getName().startsWith("javax.")) {
            return;
        }
        processedClasses.add(cls);

        // Création de la classe YUML
        YumlClass yumlClass = new YumlClass(cls.getSimpleName());
        classMap.put(cls, yumlClass);
        model.addClass(yumlClass);

        // Traitement spécial pour les interfaces
        if (cls.isInterface()) {
            yumlClass.setInterfaceName("I" + cls.getSimpleName());
        }

        // Traitement des champs, méthodes et héritage
        processFields(cls);
        processMethods(cls);
        processInheritance(cls);
    }

    /**
     * Traite tous les champs non statiques d'une classe
     */
    private void processFields(Class<?> cls) {
        for (Field field : cls.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                processField(cls, field);
            }
        }
    }

    /**
     * Traite un champ spécifique d'une classe
     * Ajoute l'attribut correspondant et crée les associations si nécessaire
     */
    private void processField(Class<?> cls, Field field) {
        YumlClass yumlClass = classMap.get(cls);
        String visibility = getVisibilitySymbol(field.getModifiers());
        Class<?> fieldType = field.getType();

        // Ajout de l'attribut
        yumlClass.addAttribute(new YumlAttribute(visibility, field.getName(), getTypeName(fieldType)));

        // Traitement des associations pour les types non primitifs et non standard
        if (!fieldType.isPrimitive() && !fieldType.isArray() &&
                !fieldType.getName().startsWith("java.") &&
                !fieldType.getName().startsWith("javax.")) {

            processClass(fieldType);
            YumlClass targetClass = classMap.get(fieldType);

            // Création de l'association appropriée
            YumlAssoc association;
            if (Collection.class.isAssignableFrom(fieldType)) {
                // Cas des collections: création d'une agrégation
                association = new YumlAggregation(yumlClass, targetClass);
                association.setTargetCardinality("*");

                // Analyse des types génériques pour les collections
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    Class<?> actualType = (Class<?>) ((ParameterizedType) genericType)
                            .getActualTypeArguments()[0];
                    processClass(actualType);
                    targetClass = classMap.get(actualType);
                }
            } else {
                // Cas standard: création d'une association simple
                association = new YumlSimpleAssoc(yumlClass, targetClass);
            }

            association.setLabel(field.getName());
            model.addAssociation(association);
        }
    }

    /**
     * Traite toutes les méthodes non statiques d'une classe
     */
    private void processMethods(Class<?> cls) {
        YumlClass yumlClass = classMap.get(cls);
        for (Method method : cls.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers())) {
                processMethod(yumlClass, method);
            }
        }
    }

    /**
     * Traite une méthode spécifique et l'ajoute au modèle YUML
     */
    private void processMethod(YumlClass yumlClass, Method method) {
        String visibility = getVisibilitySymbol(method.getModifiers());
        YumlMethod yumlMethod = new YumlMethod(
                visibility,
                method.getName(),
                getTypeName(method.getReturnType())
        );

        // Ajout des paramètres de la méthode
        for (Parameter param : method.getParameters()) {
            yumlMethod.addParameter(new YumlParameter(
                    param.getName(),
                    getTypeName(param.getType())
            ));
        }

        yumlClass.addMethod(yumlMethod);
    }

    /**
     * Traite l'héritage d'une classe
     * Crée une relation d'héritage si la classe a une superclasse autre que Object
     */
    private void processInheritance(Class<?> cls) {
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            processClass(superClass);
            YumlClass yumlSuperClass = classMap.get(superClass);
            YumlInheritance inheritance = new YumlInheritance(classMap.get(cls), yumlSuperClass);
            model.addAssociation(inheritance);
        }
    }

    /**
     * Retourne le symbole de visibilité correspondant aux modificateurs
     * + pour public, # pour protected, - pour private, ~ pour package private
     */
    private String getVisibilitySymbol(int modifiers) {
        if (Modifier.isPublic(modifiers)) return "+";
        if (Modifier.isProtected(modifiers)) return "#";
        if (Modifier.isPrivate(modifiers)) return "-";
        return "~"; // package private
    }

    /**
     * Retourne le nom simplifié d'un type
     * Gère le cas particulier des tableaux
     */
    private String getTypeName(Class<?> type) {
        if (type.isArray()) {
            return getTypeName(type.getComponentType()) + "[]";
        }
        return type.getSimpleName();
    }
}
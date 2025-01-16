package exercices.exercice2;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class YumlVisitor implements Visitor {
    private StringBuilder yumlSpec;
    private Set<String> processedRelations;

    public YumlVisitor() {
        this.yumlSpec = new StringBuilder();
        this.processedRelations = new HashSet<>();
    }

    @Override
    public void visit(ClassInfo classInfo) {
        Class<?> cls = classInfo.getCls();

        // Gérer l'héritage
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            addRelation(String.format("[%s]^-[%s]",
                    cls.getSimpleName(),
                    superClass.getSimpleName()));
        }

        // Gérer les interfaces implémentées
        Class<?>[] interfaces = cls.getInterfaces();
        for (Class<?> iface : interfaces) {
            addRelation(String.format("[%s]^-.-[%s]",
                    cls.getSimpleName(),
                    iface.getSimpleName()));
        }
    }

    @Override
    public void visit(InterfaceInfo interfaceInfo) {
        Class<?> interfaceClass = interfaceInfo.getInterfaceClass();
        // Gérer les relations d'héritage entre interfaces
        Class<?>[] superInterfaces = interfaceClass.getInterfaces();
        for (Class<?> superIface : superInterfaces) {
            addRelation(String.format("[%s]^-.-[%s]",
                    interfaceClass.getSimpleName(),
                    superIface.getSimpleName()));
        }
    }

    @Override
    public void visit(FieldInfo fieldInfo) {
        Field field = fieldInfo.getField();
        Class<?> declaringClass = field.getDeclaringClass();

        // Gérer les attributs de type List
        if (field.getType().getSimpleName().equals("List")) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                if (typeArguments.length > 0) {
                    String targetType = typeArguments[0].getTypeName();
                    targetType = targetType.substring(targetType.lastIndexOf('.') + 1);
                    addRelation(String.format("[%s]*-%s>[%s]",
                            declaringClass.getSimpleName(),
                            field.getName(),
                            targetType));
                }
            }
        }
    }

    private void addRelation(String relation) {
        if (processedRelations.add(relation)) {
            if (yumlSpec.length() > 0) {
                yumlSpec.append(",");
            }
            yumlSpec.append(relation);
        }
    }

    public String getYumlSpec() {
        return yumlSpec.toString();
    }
}
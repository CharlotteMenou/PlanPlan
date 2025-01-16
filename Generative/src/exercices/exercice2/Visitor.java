package exercices.exercice2;

public interface Visitor {
    void visit(ClassInfo classInfo);
    void visit(InterfaceInfo interfaceInfo);
    void visit(FieldInfo fieldInfo);
}
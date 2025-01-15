package exercices.exercice3;

public interface YumlVisitor {
    void visit(YumlModel model);
    void visit(YumlClass yumlClass);
    void visit(YumlAssoc association);
    void visit(YumlAttribute attribute);
    void visit(YumlMethod method);
    void visit(YumlParameter yumlParameter);
}
package exercices.exercice2;

interface YumlVisitor {
    void visit(YumlModel model);
    void visit(YumlClass clazz);
    void visit(YumlAttribute attribute);
    void visit(YumlMethod method);
    void visit(YumlParameter parameter);
    void visit(YumlRelation relation);
}
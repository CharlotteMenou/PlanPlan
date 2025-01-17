package exercices.ex2;

import exercices.ex2.yuml.YuAsso;
import exercices.ex2.yuml.YuClass;
import exercices.ex2.yuml.YuNoAsso;
import exercices.ex2.yuml.YuSimpleAsso;

public interface YumlVisitor {
    void visit(YuClass yuClass);
    void visit(YuNoAsso yuNoAsso);

    void visit(YuSimpleAsso yuSimpleAsso);
}

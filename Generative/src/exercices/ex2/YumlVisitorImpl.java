package exercices.ex2;

import exercices.ex2.yuml.YuClass;
import exercices.ex2.yuml.YuNoAsso;
import exercices.ex2.yuml.YuSimpleAsso;

public class YumlVisitorImpl implements YumlVisitor{

    private String result = "";

    public String getResult() {
        return result;
    }

    @Override
    public void visit(YuClass yuClass) {
        result = result + "[" + yuClass.getName() + "]";
    }

    @Override
    public void visit(YuNoAsso yuNoAsso) {
         yuNoAsso.getTheClass().accept(this);
    }

    @Override
    public void visit(YuSimpleAsso yuSimpleAsso) {
        // TODO
    }
}

package exercices.ex2.yuml;

import exercices.ex2.YumlVisitor;

public class YuNoAsso extends YuAsso{

    private YuClass theClass;

    public YuClass getTheClass() {
        return theClass;
    }

    public void setTheClass(YuClass theClass) {
        this.theClass = theClass;
    }

    public YuNoAsso(YuClass theClass) {
        this.theClass = theClass;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}

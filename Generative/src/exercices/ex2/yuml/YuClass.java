package exercices.ex2.yuml;

import exercices.ex2.YumlVisitor;

public class YuClass {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YuClass(String name) {
        this.name = name;
    }

    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}

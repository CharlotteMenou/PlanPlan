package exercices.ex2.yuml;

import exercices.ex2.YumlVisitor;

public class YuSimpleAsso extends YuAsso {
    private YuClass startClass;
    private YuClass endClass;
    private String startCardinality;
    private String endCardinality;

    public YuClass getStartClass() {
        return startClass;
    }

    public void setStartClass(YuClass startClass) {
        this.startClass = startClass;
    }

    public String getEndCardinality() {
        return endCardinality;
    }

    public void setEndCardinality(String endCardinality) {
        this.endCardinality = endCardinality;
    }

    public String getStartCardinality() {
        return startCardinality;
    }

    public void setStartCardinality(String startCardinality) {
        this.startCardinality = startCardinality;
    }

    public YuClass getEndClass() {
        return endClass;
    }

    public void setEndClass(YuClass endClass) {
        this.endClass = endClass;
    }

    public YuSimpleAsso(YuClass startClass, YuClass endClass) {
        this.startClass = startClass;
        this.endClass = endClass;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}

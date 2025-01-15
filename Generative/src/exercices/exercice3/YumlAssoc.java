package exercices.exercice3;

public abstract class YumlAssoc implements YumlElement {
    protected YumlClass source;
    protected YumlClass target;
    protected String sourceCardinality;
    protected String targetCardinality;
    protected String label;

    public YumlAssoc(YumlClass source, YumlClass target) {
        this.source = source;
        this.target = target;
    }

    public YumlClass getSource() {
        return source;
    }

    public YumlClass getTarget() {
        return target;
    }

    public String getSourceCardinality() {
        return sourceCardinality;
    }

    public String getTargetCardinality() {
        return targetCardinality;
    }

    public String getLabel() {
        return label;
    }

    public void setCardinalities(String source, String target) {
        this.sourceCardinality = source;
        this.targetCardinality = target;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }

    public abstract String getAssociationSymbol();

    public void setTargetCardinality(String s) {
        this.targetCardinality = s;
    }
}

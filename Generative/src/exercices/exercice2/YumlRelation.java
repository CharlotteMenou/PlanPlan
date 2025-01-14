package exercices.exercice2;

class YumlRelation implements YumlElement {
    public void setTarget(YumlClass target) {
        this.target = target;
    }

    public enum Type {
        INHERITANCE, ASSOCIATION, AGGREGATION, COMPOSITION
    }

    private Type type;
    private YumlClass source;
    private YumlClass target;
    private String sourceMultiplicity;
    private String targetMultiplicity;
    private String label;

    public YumlRelation(Type type, YumlClass source, YumlClass target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public Type getType() {
        return type;
    }

    public YumlClass getSource() {
        return source;
    }

    public YumlClass getTarget() {
        return target;
    }

    public String getSourceMultiplicity() {
        return sourceMultiplicity;
    }

    public void setSourceMultiplicity(String sourceMultiplicity) {
        this.sourceMultiplicity = sourceMultiplicity;
    }

    public String getTargetMultiplicity() {
        return targetMultiplicity;
    }

    public void setTargetMultiplicity(String targetMultiplicity) {
        this.targetMultiplicity = targetMultiplicity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}

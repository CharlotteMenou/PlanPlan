package exercices.exercice3;

public class YumlComposition extends YumlAssoc {
    public YumlComposition(YumlClass source, YumlClass target) {
        super(source, target);
    }

    @Override
    public String getAssociationSymbol() {
        return "++->";
    }
}

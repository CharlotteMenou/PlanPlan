package exercices.exercice2;

public class YumlComposition extends YumlAssoc {
    public YumlComposition(YumlClass source, YumlClass target) {
        super(source, target);
    }

    @Override
    public String getAssociationSymbol() {
        return "++->";
    }
}

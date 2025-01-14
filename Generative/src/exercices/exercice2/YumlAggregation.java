package exercices.exercice2;

public class YumlAggregation extends YumlAssoc {
    public YumlAggregation(YumlClass source, YumlClass target) {
        super(source, target);
    }

    @Override
    public String getAssociationSymbol() {
        return "<>->";
    }
}
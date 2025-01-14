package exercices.exercice2;

public class YumlSimpleAssoc extends YumlAssoc {
    public YumlSimpleAssoc(YumlClass source, YumlClass target) {
        super(source, target);
    }

    @Override
    public String getAssociationSymbol() {
        return "->";
    }
}
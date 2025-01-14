package exercices.exercice2;

public class YumlInheritance extends YumlAssoc {
    public YumlInheritance(YumlClass source, YumlClass target) {
        super(source, target);
    }

    @Override
    public String getAssociationSymbol() {
        return "^";
    }
}

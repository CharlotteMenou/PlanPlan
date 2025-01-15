package exercices.exercice3;

public class YumlInheritance extends YumlAssoc {
    public YumlInheritance(YumlClass source, YumlClass target) {
        super(source, target);
    }

    @Override
    public String getAssociationSymbol() {
        return "^";
    }
}

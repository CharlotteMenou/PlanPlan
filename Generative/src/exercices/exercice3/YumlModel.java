package exercices.exercice3;

import java.util.ArrayList;
import java.util.List;

public class YumlModel implements YumlElement {
    private List<YumlClass> classes = new ArrayList<>();
    private List<YumlAssoc> associations = new ArrayList<>();

    public void addClass(YumlClass yumlClass) {
        classes.add(yumlClass);
    }

    public void addAssociation(YumlAssoc association) {
        associations.add(association);
    }

    public List<YumlClass> getClasses() {
        return classes;
    }

    public List<YumlAssoc> getAssociations() {
        return associations;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}
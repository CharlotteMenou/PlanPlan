package exercices.exercice2;

import java.util.ArrayList;
import java.util.List;

class YumlModel implements YumlElement {
    private List<YumlClass> classes = new ArrayList<>();
    private List<YumlRelation> relations = new ArrayList<>();

    public void addClass(YumlClass clazz) {
        classes.add(clazz);
    }

    public void addRelation(YumlRelation relation) {
        relations.add(relation);
    }

    public List<YumlClass> getClasses() {
        return classes;
    }

    public List<YumlRelation> getRelations() {
        return relations;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}
package exercices.exercice2;

import java.util.ArrayList;
import java.util.List;

class YumlClass implements YumlElement {
    private String name;
    private List<YumlAttribute> attributes = new ArrayList<>();
    private List<YumlMethod> methods = new ArrayList<>();

    public YumlClass(String name) {
        this.name = name;
    }

    public void addAttribute(YumlAttribute attribute) {
        attributes.add(attribute);
    }

    public void addMethod(YumlMethod method) {
        methods.add(method);
    }

    public String getName() {
        return name;
    }

    public List<YumlAttribute> getAttributes() {
        return attributes;
    }

    public List<YumlMethod> getMethods() {
        return methods;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}
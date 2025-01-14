package exercices.exercice2;

import java.util.ArrayList;
import java.util.List;

public class YumlClass implements YumlElement {
    private String name;
    private String interfaceName;
    private List<YumlAttribute> attributes = new ArrayList<>();
    private List<YumlMethod> methods = new ArrayList<>();
    private String backgroundColor;

    public YumlClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void addAttribute(YumlAttribute attribute) {
        attributes.add(attribute);
    }

    public List<YumlAttribute> getAttributes() {
        return attributes;
    }

    public void addMethod(YumlMethod method) {
        methods.add(method);
    }

    public List<YumlMethod> getMethods() {
        return methods;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}
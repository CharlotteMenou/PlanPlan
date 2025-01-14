package exercices.exercice2;

class YumlAttribute implements YumlElement {
    private String visibility;
    private String name;
    private String type;

    public YumlAttribute(String visibility, String name, String type) {
        this.visibility = visibility;
        this.name = name;
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}


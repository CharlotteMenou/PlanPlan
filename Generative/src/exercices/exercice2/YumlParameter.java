package exercices.exercice2;

class YumlParameter implements YumlElement {
    private String name;
    private String type;

    public YumlParameter(String name, String type) {
        this.name = name;
        this.type = type;
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


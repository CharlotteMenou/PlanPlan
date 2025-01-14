package exercices.exercice2;

// Classe Equipment pour tester la composition
class Equipment {
    private String name;
    private String type;

    public Equipment(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
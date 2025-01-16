package exercices.exercice4;

public class Option {
    private String nom;
    private int prix;

    // Constructeur par défaut nécessaire pour la réflexion
    public Option() {}

    // Getters pour vérifier les résultats
    public String getNom() { return nom; }
    public int getPrix() { return prix; }
    @Override
    public String toString() {
        return nom + " (" + prix + "€)";
    }
}

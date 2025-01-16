package exercices.exercice4;

import java.util.ArrayList;
import java.util.List;

public class Voiture {
    private String marque;
    private int annee;
    private List<Option> options;

    // Constructeur par défaut nécessaire pour la réflexion
    public Voiture() {
        this.options = new ArrayList<>();
    }

    // Getters pour vérifier les résultats
    public String getMarque() { return marque; }
    public int getAnnee() { return annee; }
    public List<Option> getOptions() { return options; }
}

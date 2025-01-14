package exercices.exercice2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crée les objets
        Place place = new Place("Place", "Address");
        ArrayList fenetre = new ArrayList<String>();
        fenetre.addAll(List.of(new String[]{"Chaîne 1", "Chaîne 2", "Chaîne 3"}));
        Room room = new Room(place, "Room", 10,fenetre);

        // Construit le modèle
        JavaToYumlModelBuilder builder = new JavaToYumlModelBuilder();
        YumlModel model = builder.buildModel(Room.class, Place.class);

        // Génère le texte YUML avec la directive de type
        YumlTextGenerator generator = new YumlTextGenerator();
        model.accept(generator);

        // Affiche le résultat
        System.out.println(generator.getResult());
    }
}
package exercices.exercice2;

public class Main {
    public static void main(String[] args) {
        // Crée les objets
        Place place = new Place("Place", "Address");
        Room room = new Room(place, "Room", 10);

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
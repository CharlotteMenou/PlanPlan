package exercices.exercice3;

public class Main {
    public static void main(String[] args) {
        // Crée le builder
        JavaToYumlBuilder builder = new JavaToYumlBuilder();

        // Construit le modèle avec toutes nos classes
        YumlModel model = builder.buildModel(
                Room.class,
                Place.class,
                Equipment.class,
                BaseRoom.class,
                Bookable.class
        );

        // Génère le texte YUML
        YumlTextGenerator generator = new YumlTextGenerator();
        model.accept(generator);

        // Affiche le résultat
        System.out.println("=== diagramme YUML ===");
        System.out.println(generator.getResult());

        // Test de création d'objets
        Place place = new Place("UBO", "19 rue du centre");
        Room room = new Room(place, "Salle de réunion 1", 20);
        Equipment projector = new Equipment("Porjecteur", "Visuel");
        Equipment whiteboard = new Equipment("Tableau", "Visuel");

        room.addEquipment(projector);
        room.addEquipment(whiteboard);

        System.out.println("\n=== Structure ===");
        System.out.println("Place: " + place.getName() + " à " + place.getAddress());
        System.out.println("Salle: " + room.getName() + " (capacité: " + room.getCapacity() + ")");
        System.out.println("Equipement:");
        for (Equipment eq : room.getEquipment()) {
            System.out.println("- " + eq.getName() + " (" + eq.getType() + ")");
        }
    }
}
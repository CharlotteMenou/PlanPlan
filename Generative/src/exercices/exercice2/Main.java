package exercices.exercice2;

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
        System.out.println("=== YUML Diagram ===");
        System.out.println(generator.getResult());

        // Test de création d'objets
        Place place = new Place("Conference Center", "123 Main St");
        Room room = new Room(place, "Meeting Room A", 20);
        Equipment projector = new Equipment("Projector", "Visual");
        Equipment whiteboard = new Equipment("Whiteboard", "Visual");

        room.addEquipment(projector);
        room.addEquipment(whiteboard);

        System.out.println("\n=== Object Structure ===");
        System.out.println("Place: " + place.getName() + " at " + place.getAddress());
        System.out.println("Room: " + room.getName() + " (capacity: " + room.getCapacity() + ")");
        System.out.println("Equipment:");
        for (Equipment eq : room.getEquipment()) {
            System.out.println("- " + eq.getName() + " (" + eq.getType() + ")");
        }
    }
}
package exercices.exercice2;

class Main {
    public static void main(String[] args) {

        Place place = new Place("Place", "Address");
        Room room = new Room(place, "Room", 10);
        // Build the model
        JavaToYumlModelBuilder builder = new JavaToYumlModelBuilder();
        YumlModel model = builder.buildModel(Room.class, Place.class);

        // Generate YUML text
        YumlTextGenerator generator = new YumlTextGenerator();
        model.accept(generator);
        System.out.println(generator.getResult());
    }
}

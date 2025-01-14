package exercices.exercice2;

import java.util.ArrayList;
import java.util.List;

// Classe Room qui hérite de BaseRoom, implémente Bookable et a une composition avec Equipment
class Room extends BaseRoom implements Bookable {
    private Place place;
    private String name;
    private List<Equipment> equipment;  // Composition

    public Room(Place place, String name, int capacity) {
        super(capacity);
        this.place = place;
        this.name = name;
        this.equipment = new ArrayList<>();
        place.addRoom(this);  // Établit la relation bidirectionnelle
    }

    @Override
    public void book() {
        System.out.println("Room booked!");
    }

    public void addEquipment(Equipment eq) {
        equipment.add(eq);
    }

    public Place getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }
}
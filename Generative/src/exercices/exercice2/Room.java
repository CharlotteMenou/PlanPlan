package exercices.exercice2;

public class Room {
    private Place place;
    private String name;
    private int capacity;

    public Room(Place place, String name, int capacity) {
        this.place = place;
        this.name = name;
        this.capacity = capacity;
    }

    public Place getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}

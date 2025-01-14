package exercices.exercice2;

import java.util.ArrayList;
import java.util.List;

// Classe Place avec agrégation de Room
class Place {
    private String name;
    private String address;
    private List<Room> rooms;  // Agrégation

    public Place(String name, String address) {
        this.name = name;
        this.address = address;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}

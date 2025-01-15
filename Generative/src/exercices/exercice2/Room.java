package exercices.exercice2;

import java.util.ArrayList;

public class Room {
    private Place place;
    private String name;
    private int capacity;

    public ArrayList<String> getFenetre() {
        return fenetre;
    }

    public void setFenetre(ArrayList<String> fenetre) {
        this.fenetre = fenetre;
    }

    private ArrayList<String> fenetre;

    public Room(Place place, String name, int capacity,ArrayList<String> fenetre) {
        this.place = place;
        this.name = name;
        this.capacity = capacity;
        this.fenetre = fenetre ;
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

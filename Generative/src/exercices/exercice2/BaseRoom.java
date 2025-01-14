package exercices.exercice2;

// Classe de base pour tester l'héritage
class BaseRoom {
    protected int capacity;

    public BaseRoom(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
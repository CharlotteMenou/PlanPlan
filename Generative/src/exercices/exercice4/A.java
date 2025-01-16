package exercices.exercice4;

import java.util.ArrayList;
import java.util.List;

public class A {
    private String id;
    private int value;
    private List<B> theBs;  // Notez le nom theBs qui correspond à la balise XML

    public A() {
        theBs = new ArrayList<B>();
    }
}
package exercices.ex4.parfeur;

import java.util.ArrayList;
import java.util.List;

public class A {
    public String id;
    public String value;
    public List<B> theBs;
    public A() {
        theBs = new ArrayList<B>();
    }
}
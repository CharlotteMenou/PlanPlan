package parfeur;

import java.util.ArrayList;
import java.util.List;

public class A {
    String id;
    String value;
    List<B> theBs;
    public A() {
        theBs = new ArrayList<B>();
    }
    @Override
    public String toString() {
        return "A{" +
                "id=" + id +
                ", value=" + value +
                ", theBs=" + theBs +
                '}';
    }
}

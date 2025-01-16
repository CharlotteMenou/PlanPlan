package parfeur;

import java.util.ArrayList;
import java.util.List;

public class A {
    private String id;
    private String value;
    private List<B> theBs;

    public A() {
        theBs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<B> getTheBs() {
        return theBs;
    }

    public void setTheBs(List<B> theBs) {
        this.theBs = theBs;
    }

    @Override
    public String toString() {
        return "A{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", theBs=" + theBs +
                '}';
    }
}
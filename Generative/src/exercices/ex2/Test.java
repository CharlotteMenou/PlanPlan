package exercices.ex2;

import exercices.ex1.Exercice1;

interface interf2 {
}
public class Test {

    private String arg1;
    private String arg2;

    static class Inner1 {
    }
    static interface innerInterf1 {
    }
    private class Inner2 {
        class InnerInner1 implements innerInterf1, interf2 {
        }
        class InnerInner2 extends InnerInner1 {
        }
    }
    public static void classRepresentation(Class<?> cls) {
// TODO
    }
    public static void main(String[] args) {
        classRepresentation(Exercice1.class);
    }
}
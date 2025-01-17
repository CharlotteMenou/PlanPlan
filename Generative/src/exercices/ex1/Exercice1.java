package exercices.ex1;

public class Exercice1 {
    static class Inner1 {
    }
    static interface innerInterf1 {
    }
    private class Inner2 {
        class InnerInner1 implements innerInterf1, interf1 {
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
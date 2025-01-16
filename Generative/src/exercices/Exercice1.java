package exercices;
interface interf1 {
}
public class Exercice1 {
    static class Inner1 {
    }

    static interface innerInterf1 {
    }

    private class Inner2 {
        class InnerInner1 implements innerInterf1 {
        }

        class InnerInner2 extends InnerInner1 {
        }
    }
}

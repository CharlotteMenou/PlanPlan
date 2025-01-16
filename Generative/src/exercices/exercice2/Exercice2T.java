package exercices.exercice2;

import exercices.exercice1.interf1;
import java.util.List;

public class Exercice2T {
    static class Inner1 {
        private int value;
        public String name;
    }

    static interface innerInterf1 {
    }

    private class Inner2 {
        protected double score;
        private List<Exercice2> exercice2List;

        class InnerInner1 implements innerInterf1, interf1 {
            private String description;
        }

        class InnerInner2 extends InnerInner1 {
            public int count;
        }
    }

    public static String generateYuml(Class<?> cls) {

        YumlVisitor yumlVisitor = new YumlVisitor();

        ClassInfo rootElement = new ClassInfo(cls);
        rootElement.accept(yumlVisitor);

        return yumlVisitor.getYumlSpec();
    }

    public static void main(String[] args) {
        String yumlSpec = generateYuml(Exercice2T.class);
        System.out.println(yumlSpec);
    }
}
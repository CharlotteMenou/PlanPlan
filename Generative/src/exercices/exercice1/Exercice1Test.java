package exercices.exercice1;  // Notez la minuscule

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class Exercice1Test {
    @Test
    void testExercice1Representation() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        // System.setOut(new PrintStream(outContent));

        try {
            Exercice1.classRepresentation(Exercice1.class);
            String output = outContent.toString();

            // Tests
            /*assertTrue(output.contains("package exercices"));
            assertTrue(output.contains("public class Exercice1"));
            assertTrue(output.contains("static class Inner1"));
            assertTrue(output.contains("private class Inner2"));
            assertTrue(output.contains("implements innerInterf1, interf1"));*/

            System.out.println("Output réel:");
            System.out.println(output);
        } finally {
            System.out.println(originalOut);
        }
    }
}
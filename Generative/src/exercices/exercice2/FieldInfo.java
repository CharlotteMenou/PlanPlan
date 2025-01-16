package exercices.exercice2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldInfo implements Element {
    private Field field;

    public FieldInfo(Field field) {
        this.field = field;
    }

    public Field getField() { return field; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
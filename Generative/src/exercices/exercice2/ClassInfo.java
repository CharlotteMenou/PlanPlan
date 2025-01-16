package exercices.exercice2;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassInfo implements Element {
    private Class<?> cls;
    private Field[] fields;
    private Class<?>[] innerClasses;

    public ClassInfo(Class<?> cls) {
        this.cls = cls;
        this.fields = cls.getDeclaredFields();
        this.innerClasses = cls.getDeclaredClasses();
    }

    public Class<?> getCls() { return cls; }
    public Field[] getFields() { return fields; }
    public Class<?>[] getInnerClasses() { return innerClasses; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        // Visiter les champs
        for (Field field : fields) {
            new FieldInfo(field).accept(visitor);
        }
        // Visiter les classes internes
        for (Class<?> innerClass : innerClasses) {
            new ClassInfo(innerClass).accept(visitor);
        }
    }
}
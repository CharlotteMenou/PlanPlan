package exercices.exercice2;

public class InterfaceInfo implements Element {
    private Class<?> interfaceClass;

    public InterfaceInfo(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public Class<?> getInterfaceClass() { return interfaceClass; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
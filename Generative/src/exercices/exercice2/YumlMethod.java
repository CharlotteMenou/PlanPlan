package exercices.exercice2;

import java.util.ArrayList;
import java.util.List;

public class YumlMethod implements YumlElement {
    private String visibility;
    private String name;
    private List<YumlParameter> parameters = new ArrayList<>();
    private String returnType;

    public YumlMethod(String visibility, String name, String returnType) {
        this.visibility = visibility;
        this.name = name;
        this.returnType = returnType;
    }

    public void addParameter(YumlParameter parameter) {
        parameters.add(parameter);
    }

    public String getVisibility() {
        return visibility;
    }

    public String getName() {
        return name;
    }

    public List<YumlParameter> getParameters() {
        return parameters;
    }

    public String getReturnType() {
        return returnType;
    }

    @Override
    public void accept(YumlVisitor visitor) {
        visitor.visit(this);
    }
}
package exercices.exercice2;

import java.util.ArrayList;
import java.util.List;

public class YumlTextGenerator implements YumlVisitor {
    private StringBuilder builder;

    public YumlTextGenerator() {
        builder = new StringBuilder();
        // Ajout de la directive de type au début
        builder.append("// {type:class}\n");
    }

    @Override
    public void visit(YumlModel model) {
        // Visite chaque classe
        for (YumlClass clazz : model.getClasses()) {
            clazz.accept(this);
            builder.append("\n");
        }
        // Visite chaque relation
        for (YumlRelation relation : model.getRelations()) {
            relation.accept(this);
            builder.append("\n");
        }
    }

    @Override
    public void visit(YumlClass clazz) {
        builder.append("[").append(clazz.getName());

        // Ajoute les attributs
        if (!clazz.getAttributes().isEmpty()) {
            builder.append("|");
            for (YumlAttribute attr : clazz.getAttributes()) {
                attr.accept(this);
                builder.append(";");
            }
        }

        // Ajoute les méthodes
        if (!clazz.getMethods().isEmpty()) {
            builder.append("|");
            for (YumlMethod method : clazz.getMethods()) {
                method.accept(this);
                builder.append(";");
            }
        }

        builder.append("]");
    }

    @Override
    public void visit(YumlAttribute attribute) {
        builder.append(attribute.getVisibility())
                .append(attribute.getName())
                .append(":")
                .append(attribute.getType());
    }

    @Override
    public void visit(YumlMethod method) {
        builder.append(method.getVisibility())
                .append(method.getName())
                .append("(");

        // Ajoute les paramètres
        List<YumlParameter> params = method.getParameters();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) builder.append(",");
            params.get(i).accept(this);
        }

        builder.append("):")
                .append(method.getReturnType());
    }

    @Override
    public void visit(YumlParameter parameter) {
        builder.append(parameter.getType());
    }

    @Override
    public void visit(YumlRelation relation) {
        builder.append("[").append(relation.getSource().getName()).append("]");

        if (relation.getLabel() != null) {
            builder.append(relation.getLabel());
        }

        builder.append("->");

        if (relation.getTargetMultiplicity() != null) {
            builder.append(relation.getTargetMultiplicity());
        }

        builder.append("[").append(relation.getTarget().getName()).append("]");
    }

    public String getResult() {
        return builder.toString();
    }
}
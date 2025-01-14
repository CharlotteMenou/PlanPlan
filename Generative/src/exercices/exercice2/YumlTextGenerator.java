package exercices.exercice2;

import java.util.List;

public class YumlTextGenerator implements YumlVisitor {
    private StringBuilder builder = new StringBuilder();

    public YumlTextGenerator() {
        builder.append("// {type:class}\n");
    }

    @Override
    public void visit(YumlModel model) {
        for (YumlClass yumlClass : model.getClasses()) {
            yumlClass.accept(this);
            builder.append("\n");
        }

        for (YumlAssoc assoc : model.getAssociations()) {
            assoc.accept(this);
            builder.append("\n");
        }
    }

    @Override
    public void visit(YumlClass yumlClass) {
        builder.append("[");

        if (yumlClass.getInterfaceName() != null) {
            builder.append("≪").append(yumlClass.getInterfaceName()).append("≫;");
        }

        builder.append(yumlClass.getName());

        if (!yumlClass.getAttributes().isEmpty()) {
            builder.append("|");
            for (YumlAttribute attr : yumlClass.getAttributes()) {
                attr.accept(this);
                builder.append(";");
            }
        }

        if (!yumlClass.getMethods().isEmpty()) {
            builder.append("|");
            for (YumlMethod method : yumlClass.getMethods()) {
                method.accept(this);
                builder.append(";");
            }
        }

        if (yumlClass.getBackgroundColor() != null) {
            builder.append(" {bg:").append(yumlClass.getBackgroundColor()).append("}");
        }

        builder.append("]");
    }

    @Override
    public void visit(YumlAssoc association) {
        builder.append("[").append(association.getSource().getName()).append("]");

        if (association.getSourceCardinality() != null) {
            builder.append(association.getSourceCardinality());
        }

        if (association.getLabel() != null) {
            builder.append(association.getLabel());
        }

        builder.append(association.getAssociationSymbol());

        if (association.getTargetCardinality() != null) {
            builder.append(association.getTargetCardinality());
        }

        builder.append("[").append(association.getTarget().getName()).append("]");
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
        // Pour les paramètres, on affiche uniquement leur type
        builder.append(parameter.getType());
    }

    public String getResult() {
        return builder.toString();
    }
}
package exercices.exercice2;

import java.util.List;

class YumlTextGenerator implements YumlVisitor {
    private StringBuilder builder = new StringBuilder();
    private String currentClass = "";

    public String getResult() {
        return builder.toString();
    }

    @Override
    public void visit(YumlModel model) {
        for (YumlClass clazz : model.getClasses()) {
            clazz.accept(this);
            builder.append("\n");
        }
        for (YumlRelation relation : model.getRelations()) {
            relation.accept(this);
            builder.append("\n");
        }
    }

    @Override
    public void visit(YumlClass clazz) {
        currentClass = clazz.getName();
        builder.append("[").append(currentClass);

        if (!clazz.getAttributes().isEmpty()) {
            builder.append("|");
            for (YumlAttribute attr : clazz.getAttributes()) {
                attr.accept(this);
                builder.append(";");
            }
        }

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

        switch (relation.getType()) {
            case INHERITANCE:
                builder.append("^-");
                break;
            case ASSOCIATION:
                if (relation.getLabel() != null) {
                    builder.append(relation.getLabel());
                }
                builder.append("->");
                break;
            case AGGREGATION:
                builder.append("+->");
                break;
            case COMPOSITION:
                builder.append("++->");
                break;
        }

        if (relation.getTargetMultiplicity() != null) {
            builder.append(relation.getTargetMultiplicity());
        }

        builder.append("[").append(relation.getTarget().getName()).append("]");
    }
}

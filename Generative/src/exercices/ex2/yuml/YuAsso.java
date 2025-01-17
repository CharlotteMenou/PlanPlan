package exercices.ex2.yuml;

import exercices.ex2.YumlVisitor;
import org.junit.platform.engine.TestDescriptor;

public abstract class YuAsso {
    public abstract void accept(YumlVisitor visitor);
}

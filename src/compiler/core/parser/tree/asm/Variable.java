package compiler.core.parser.tree.asm;

/**
 * Created with IntelliJ IDEA.
 */
public class Variable {

    private String name;
    private Scope scope;
    private Object value;

    public Variable(String name, Scope scope) {
        this.name = name;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public Scope getScope() {
        return scope;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

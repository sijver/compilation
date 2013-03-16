package compiler.core.parser.tree.asm;

/**
 * Created with IntelliJ IDEA.
 */
//Class for description of variables
public class Variable {

    private String name;    //Variable name
    private Scope scope;    //Scope of the variable (is variable global or local, etc...)
    private Object value;   //Value of the variable

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

package compiler.core.parser.tree.asm;

/**
 * Created with IntelliJ IDEA.
 */
public class Scope {

    private Scope parentScope = null;

    public Scope() {
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }
}

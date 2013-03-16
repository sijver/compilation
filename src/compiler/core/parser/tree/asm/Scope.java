package compiler.core.parser.tree.asm;

/**
 * Created with IntelliJ IDEA.
 */
//Scope of program (main scope, function scope)
public class Scope {

    private Scope parentScope = null;   //Which scope is the parent scope. If main scope -> no parent scope

    public Scope() {
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }
}

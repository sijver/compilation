package compiler.core.scanner.finiteautomaton;

/**
 * Created with IntelliJ IDEA.
 */
//Class intended for token description
public class Token {

    private LexicalUnit lexicalUnit;    //Lexical unit token represents
    private String returnValue; //Value of token (if exists)
    private int registerUsing = -1; //Which register of processor token is using

    public Token(LexicalUnit lexicalUnit, String returnValue) {
        this.lexicalUnit = lexicalUnit;
        this.returnValue = returnValue;
    }

    public LexicalUnit getLexicalUnit() {
        return lexicalUnit;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public int getRegisterUsing() {
        return registerUsing;
    }

    public void setRegisterUsing(int registerUsing) {
        this.registerUsing = registerUsing;
    }

    @Override
    public String toString(){
        return lexicalUnit.toString() + " = " + returnValue;
    }
}

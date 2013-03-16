package compiler.core.scanner.finiteautomaton;

/**
 * Created with IntelliJ IDEA.
 */
//Class which is intended for description of accepting state
public class AcceptState {

    private int stateNum;   //state num (id)
    private LexicalUnit tokenReturnType;    //which type of token does accept state return
    private boolean hasReturnValue; //Does the accept state have to return some value

    public AcceptState(int stateNum, LexicalUnit tokenReturnType, boolean hasReturnValue) {
        this.stateNum = stateNum;
        this.tokenReturnType = tokenReturnType;
        this.hasReturnValue = hasReturnValue;
    }

    public int getStateNum() {
        return stateNum;
    }

    public LexicalUnit getTokenReturnType() {
        return tokenReturnType;
    }

    public boolean hasReturnValue() {
        return hasReturnValue;
    }

}

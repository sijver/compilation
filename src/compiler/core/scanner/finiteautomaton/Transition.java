package compiler.core.scanner.finiteautomaton;

/**
 * Created with IntelliJ IDEA.
 */
//Class represents transition from one to another state of DFA
public class Transition {

    private int fromState;  //"From" state
    private int toState;    //"Into" state
    private String regularExpression;   //Regex which satisfies the transition

    public Transition(int fromState, int toState, String regularExpression) {
        this.fromState = fromState;
        this.toState = toState;
        this.regularExpression = regularExpression;
    }

    public int getFromState() {
        return fromState;
    }

    public void setFromState(int fromState) {
        this.fromState = fromState;
    }

    public int getToState() {
        return toState;
    }

    public void setToState(int toState) {
        this.toState = toState;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

}

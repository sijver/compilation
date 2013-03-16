package compiler.core.scanner.finiteautomaton;

import compiler.core.scanner.finiteautomaton.perl.PerlAutomatonSettings;
import compiler.core.utils.FileLoader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 */
//Class represents the DFA and can be used for perl token recognition
public class FiniteAutomaton {

    private int currentState;   //state in which DFA right now
    private List<AcceptState> acceptStatesList; //List of accepting states of DFA
    private int startState; //Start state of DFA
    private List<Transition> transitionsList;   //list of transitions between states of DFA
    private List<Integer> backTransitions;  //back transitions of DFA
    private String commentSymbol;   //Comment symbol of Perl
    private Set<Integer> stringStates;  //State in which we are reading the string

    public FiniteAutomaton() {
        acceptStatesList = new LinkedList<AcceptState>();
        transitionsList = new LinkedList<Transition>();
        backTransitions = new LinkedList<Integer>();
    }


    //For debug. DFA
    public static void main(String[] args) throws IOException {
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton();
        finiteAutomaton.setStartState(PerlAutomatonSettings.getStartState());
        finiteAutomaton.setAcceptStatesList(PerlAutomatonSettings.getAcceptStatesList());
        finiteAutomaton.setTransitionsList(PerlAutomatonSettings.getTransitions());
        finiteAutomaton.setBackTransitions(PerlAutomatonSettings.getBackTransitions());
        finiteAutomaton.setCommentSymbol(PerlAutomatonSettings.getCommentSymbol());
        finiteAutomaton.setStringStates(PerlAutomatonSettings.getStringStates());
        List<Token> tokensList = finiteAutomaton.getScannedTokens(FileLoader.readTheFile("C:\\Users\\Mark\\Desktop\\perl.txt"));
        for (Token t : tokensList) {
            System.out.println(t.getLexicalUnit() + "   " + t.getReturnValue());
        }

    }

    public void setAcceptStatesList(List<AcceptState> acceptStatesList) {
        this.acceptStatesList = acceptStatesList;
    }

    public void setTransitionsList(List<Transition> transitionsList) {
        this.transitionsList.clear();
        this.transitionsList.addAll(transitionsList);
    }

    public void setBackTransitions(List<Integer> backTransitions) {
        this.backTransitions = backTransitions;
    }

    public void setStartState(int startState) {
        this.startState = startState;
        currentState = startState;
    }

    public void setCommentSymbol(String commentSymbol) {
        this.commentSymbol = commentSymbol;
    }

    public void setStringStates(Set<Integer> stringStates) {
        this.stringStates = stringStates;
    }

    //Does appropriate state has possible transition to the next state
    private boolean hasNextState(int numOfState) {
        for (Transition t : transitionsList) {
            if (t.getFromState() == numOfState) {
                return true;
            }
        }
        return false;
    }

    //Simulation of DFA for Perl
    public List<Token> getScannedTokens(String stringToScan) {
        List<Token> tokensList = new LinkedList<Token>();   //List of all tokens
        StringBuilder currentString = new StringBuilder();  //Current string (to create a value of token)
        Scanner scanner = new Scanner(stringToScan);
        boolean isBackTransition = false;
        while (scanner.hasNextLine()) { //Line by line script processing
            if (stringStates.contains(currentState)) {    //Miss linebreak inside the string
                currentString.append("\n");
            }
            String line = scanner.nextLine();   //line of perl script
            for (int i = 0; i < line.length(); i++) {   //for each symbol
                do {    //read symbol again if backtransition
                    String character = line.substring(i, i + 1);    //current symbol
                    if (character.equals(commentSymbol) && !stringStates.contains(currentState)) {  //next line if comment
                        i = line.length();
                        break;
                    }
                    currentString.append(character);
                    for (Transition t : transitionsList) {  //move to the next transition
                        if (t.getFromState() == currentState && Pattern.matches(t.getRegularExpression(), character)) {
                            isBackTransition = backTransitions.contains(transitionsList.indexOf(t));
                            if (character.equals(" ")) {
                                isBackTransition = false;
                            }
                            currentState = t.getToState();
                            for (AcceptState acceptState : acceptStatesList) {  //Check whether new state is accepting
                                if (acceptState.getStateNum() == currentState) {
                                    if (acceptState.hasReturnValue()) {
                                        if (acceptState.getTokenReturnType() == LexicalUnit.STRING) {   //String accepting
                                            if (currentString.subSequence(0, 1).equals("q")) {
                                                currentString = new StringBuilder(currentString.toString().replaceFirst("q^", ""));
                                            } else if (currentString.subSequence(0, 1).equals("'")) {
                                                currentString = new StringBuilder(currentString.toString().replaceFirst("'", ""));
                                            } else {
                                                currentString = new StringBuilder(currentString.toString().replaceFirst("\"", ""));
                                            }
                                        }
                                        tokensList.add(new Token(acceptState.getTokenReturnType(), currentString.toString().substring(0, currentString.length() - 1)));
                                    } else {
                                        tokensList.add(new Token(acceptState.getTokenReturnType(), null));
                                    }
                                    currentString.delete(0, currentString.length());
                                    break;
                                }
                            }
                            if (!hasNextState(currentState)) {  //if it's not next state -> to the start state
                                currentState = startState;
                            }
                            break;
                        }
                    }
                } while (isBackTransition);
            }
        }
        //back transitions in end of file can lead to mistakes. This part fixes such possibilities
        for (int backTransition : backTransitions) {
            if (transitionsList.get(backTransition).getFromState() == currentState) {
                int toState = transitionsList.get(backTransition).getToState();
                for (AcceptState acceptState : acceptStatesList) {
                    if (acceptState.getStateNum() == toState) {
                        if (acceptState.hasReturnValue()) {
                            tokensList.add(new Token(acceptState.getTokenReturnType(), currentString.toString()));
                        } else {
                            tokensList.add(new Token(acceptState.getTokenReturnType(), null));
                        }
                    }
                }
            }
        }
        tokensList.add(new Token(LexicalUnit.getStackBottomSymbol(), null));
        return tokensList;
    }


}

package compiler.core.scanner.finiteautomaton.perl;

import compiler.core.scanner.finiteautomaton.AcceptState;
import compiler.core.scanner.finiteautomaton.LexicalUnit;
import compiler.core.scanner.finiteautomaton.Transition;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 */
//Class which is intended for storing of parameters and characteristics of DFA for Perl
public class PerlAutomatonSettings {

    private static List<Transition> transitionsList;    // Transitions between states of DFA
    private static List<AcceptState> acceptStatesList;  // List of states which are accepting and what each of them accepts
    private static List<Integer> backTransitionList;   // Transitions in which we have to look for previous read symbol to add it to token
    private static int startState = 0;  // Number of state which is starting
    private static Set<Integer> stringStates;   // States in which we are reading String
    private static String commentSymbol = "#";  // Symbol of comment start

    //Creates the list of all tranisitions of DFA
    private static void createTransitions() {
        transitionsList = new LinkedList<Transition>();
        transitionsList.add(new Transition(0, 1, "[(]"));
        transitionsList.add(new Transition(0, 2, "[)]"));
        transitionsList.add(new Transition(0, 3, "[+]"));
        transitionsList.add(new Transition(0, 4, "[-]"));
        transitionsList.add(new Transition(0, 5, "[*]"));
        transitionsList.add(new Transition(0, 6, "[/]"));
        transitionsList.add(new Transition(0, 7, "[.]"));
        transitionsList.add(new Transition(0, 8, "[|]"));
        transitionsList.add(new Transition(8, 9, "[|]"));
        transitionsList.add(new Transition(0, 10, "[{]"));
        transitionsList.add(new Transition(0, 11, "[}]"));
        transitionsList.add(new Transition(0, 12, "[=]"));
        transitionsList.add(new Transition(12, 13, "[=]"));
        transitionsList.add(new Transition(12, 14, "[^=]"));
        transitionsList.add(new Transition(0, 15, "[!]"));
        transitionsList.add(new Transition(15, 16, "[=]"));
        transitionsList.add(new Transition(15, 17, "[^=]"));
        transitionsList.add(new Transition(0, 18, "[>]"));
        transitionsList.add(new Transition(18, 19, "[=]"));
        transitionsList.add(new Transition(18, 20, "[^=]"));
        transitionsList.add(new Transition(0, 21, "[<]"));
        transitionsList.add(new Transition(21, 22, "[=]"));
        transitionsList.add(new Transition(21, 23, "[^=]"));
        transitionsList.add(new Transition(0, 24, "[,]"));
        transitionsList.add(new Transition(0, 25, "[;]"));
        transitionsList.add(new Transition(0, 26, "[']"));
        transitionsList.add(new Transition(26, 26, "[^']"));
        transitionsList.add(new Transition(26, 27, "[']"));
        transitionsList.add(new Transition(0, 28, "[\"]"));
        transitionsList.add(new Transition(28, 28, "[^\"]"));
        transitionsList.add(new Transition(28, 27, "[\"]"));
        transitionsList.add(new Transition(0, 29, "[q]"));
        transitionsList.add(new Transition(29, 30, "[\\^]"));
        transitionsList.add(new Transition(30, 30, "[^\\^]"));
        transitionsList.add(new Transition(30, 27, "[\\^]"));
        transitionsList.add(new Transition(0, 31, "[$]"));
        transitionsList.add(new Transition(31, 32, "[a-zA-Z_]"));
        transitionsList.add(new Transition(32, 32, "[a-zA-Z0-9_]"));
        transitionsList.add(new Transition(32, 33, "[^a-zA-Z0-9_]"));
        transitionsList.add(new Transition(0, 34, "[&]"));
        transitionsList.add(new Transition(34, 35, "[&]"));
        transitionsList.add(new Transition(34, 69, "[^&]"));
        transitionsList.add(new Transition(69, 36, "[a-zA-Z_]"));
        transitionsList.add(new Transition(36, 36, "[a-zA-Z0-9_]"));
        transitionsList.add(new Transition(36, 37, "[^a-zA-Z0-9_]"));
        transitionsList.add(new Transition(0, 38, "[s]"));
        transitionsList.add(new Transition(38, 39, "[u]"));
        transitionsList.add(new Transition(39, 40, "[b]"));
        transitionsList.add(new Transition(40, 36, "[a-zA-Z_]"));
        transitionsList.add(new Transition(0, 41, "[i]"));
        transitionsList.add(new Transition(41, 42, "[f]"));
        transitionsList.add(new Transition(0, 43, "[e]"));
        transitionsList.add(new Transition(43, 44, "[l]"));
        transitionsList.add(new Transition(44, 45, "[s]"));
        transitionsList.add(new Transition(45, 46, "[e]"));
        transitionsList.add(new Transition(45, 47, "[i]"));
        transitionsList.add(new Transition(47, 48, "[f]"));
        transitionsList.add(new Transition(0, 49, "[r]"));
        transitionsList.add(new Transition(49, 50, "[e]"));
        transitionsList.add(new Transition(50, 51, "[t]"));
        transitionsList.add(new Transition(51, 52, "[u]"));
        transitionsList.add(new Transition(52, 53, "[r]"));
        transitionsList.add(new Transition(53, 54, "[n]"));
        transitionsList.add(new Transition(0, 55, "[u]"));
        transitionsList.add(new Transition(55, 56, "[n]"));
        transitionsList.add(new Transition(56, 57, "[l]"));
        transitionsList.add(new Transition(57, 58, "[e]"));
        transitionsList.add(new Transition(58, 59, "[s]"));
        transitionsList.add(new Transition(59, 60, "[s]"));
        transitionsList.add(new Transition(0, 62, "\\d"));
        transitionsList.add(new Transition(62, 62, "\\d"));
        transitionsList.add(new Transition(62, 63, "[.]"));
        transitionsList.add(new Transition(63, 64, "\\d"));
        transitionsList.add(new Transition(64, 64, "\\d"));
        transitionsList.add(new Transition(64, 65, "[eE]"));
        transitionsList.add(new Transition(62, 65, "[eE]"));
        transitionsList.add(new Transition(65, 66, "[+-]"));
        transitionsList.add(new Transition(66, 67, "\\d"));
        transitionsList.add(new Transition(67, 67, "\\d"));
        transitionsList.add(new Transition(65, 67, "\\d"));
        transitionsList.add(new Transition(67, 68, "[^\\d]"));
        transitionsList.add(new Transition(62, 68, "[^\\deE.]"));
        transitionsList.add(new Transition(0, 70, "[n]"));
        transitionsList.add(new Transition(70, 71, "[o]"));
        transitionsList.add(new Transition(71, 72, "[t]"));
        transitionsList.add(new Transition(43, 13, "[q]"));
        transitionsList.add(new Transition(70, 16, "[e]"));
        transitionsList.add(new Transition(0, 73, "[l]"));
        transitionsList.add(new Transition(0, 74, "[g]"));
        transitionsList.add(new Transition(73, 23, "[t]"));
        transitionsList.add(new Transition(73, 22, "[e]"));
        transitionsList.add(new Transition(74, 20, "[t]"));
        transitionsList.add(new Transition(74, 19, "[e]"));
        transitionsList.add(new Transition(0, 75, "[\\[]"));
        transitionsList.add(new Transition(0, 76, "[\\]]"));
    }

    // Returns all transitions created
    public static List<Transition> getTransitions() {
        if (transitionsList == null) {
            createTransitions();
        }
        return transitionsList;
    }

    // Returns start state
    public static int getStartState() {
        return startState;
    }

    //Creates list of accepting states
    private static void createAcceptStatesList() {
        acceptStatesList = new LinkedList<AcceptState>();
        acceptStatesList.add(new AcceptState(1, LexicalUnit.LPAR, false));
        acceptStatesList.add(new AcceptState(2, LexicalUnit.RPAR, false));
        acceptStatesList.add(new AcceptState(3, LexicalUnit.PLUS, false));
        acceptStatesList.add(new AcceptState(4, LexicalUnit.MINUS, false));
        acceptStatesList.add(new AcceptState(5, LexicalUnit.TIMES, false));
        acceptStatesList.add(new AcceptState(6, LexicalUnit.DIVIDE, false));
        acceptStatesList.add(new AcceptState(7, LexicalUnit.CONCAT_MARK, false));
        acceptStatesList.add(new AcceptState(9, LexicalUnit.LAZY_OR, false));
        acceptStatesList.add(new AcceptState(10, LexicalUnit.LBRACE, false));
        acceptStatesList.add(new AcceptState(11, LexicalUnit.RBRACE, false));
        acceptStatesList.add(new AcceptState(13, LexicalUnit.EQUALS, false));
        acceptStatesList.add(new AcceptState(14, LexicalUnit.ASSIGN_MARK, false));
        acceptStatesList.add(new AcceptState(16, LexicalUnit.DIFFERENT, false));
        acceptStatesList.add(new AcceptState(17, LexicalUnit.HIGH_NOT, false));
        acceptStatesList.add(new AcceptState(19, LexicalUnit.GREATER_EQUALS, false));
        acceptStatesList.add(new AcceptState(20, LexicalUnit.GREATER, false));
        acceptStatesList.add(new AcceptState(22, LexicalUnit.LOWER_EQUALS, false));
        acceptStatesList.add(new AcceptState(23, LexicalUnit.LOWER, false));
        acceptStatesList.add(new AcceptState(24, LexicalUnit.COMMA, false));
        acceptStatesList.add(new AcceptState(25, LexicalUnit.SEMICOLON, false));
        acceptStatesList.add(new AcceptState(27, LexicalUnit.STRING, true));
        acceptStatesList.add(new AcceptState(33, LexicalUnit.VAR, true));
        acceptStatesList.add(new AcceptState(69, LexicalUnit.CALL_MARK, false));
        acceptStatesList.add(new AcceptState(35, LexicalUnit.LAZY_AND, false));
        acceptStatesList.add(new AcceptState(37, LexicalUnit.IDENTIFIER, true));
        acceptStatesList.add(new AcceptState(40, LexicalUnit.SUB, false));
        acceptStatesList.add(new AcceptState(42, LexicalUnit.IF, false));
        acceptStatesList.add(new AcceptState(46, LexicalUnit.ELSE, false));
        acceptStatesList.add(new AcceptState(48, LexicalUnit.ELSEIF, false));
        acceptStatesList.add(new AcceptState(54, LexicalUnit.RETURN, false));
        acceptStatesList.add(new AcceptState(60, LexicalUnit.UNLESS, false));
        acceptStatesList.add(new AcceptState(68, LexicalUnit.INTEGER, true));
        acceptStatesList.add(new AcceptState(72, LexicalUnit.LOW_NOT, false));
        acceptStatesList.add(new AcceptState(75, LexicalUnit.LSQUARE, false));
        acceptStatesList.add(new AcceptState(76, LexicalUnit.RSQUARE, false));
    }

    //Returns list of accepting states
    public static List<AcceptState> getAcceptStatesList() {
        if (acceptStatesList == null) {
            createAcceptStatesList();
        }
        return acceptStatesList;
    }

    // Creates "back transitions" (see variable description)
    private static void createBackTransitions() {
        backTransitionList = new LinkedList<Integer>();
        backTransitionList.add(13);
        backTransitionList.add(19);
        backTransitionList.add(22);
        backTransitionList.add(38);
        backTransitionList.add(41);
        backTransitionList.add(44);
        backTransitionList.add(80);
        backTransitionList.add(81);

    }

    //returns back transitions
    public static List<Integer> getBackTransitions() {
        if (backTransitionList == null) {
            createBackTransitions();
        }
        return backTransitionList;
    }

    //Creates list of string states
    private static void createStringStates() {
        stringStates = new HashSet<Integer>();
        stringStates.add(26);
        stringStates.add(28);
        stringStates.add(30);
    }

    //Returns the list of string states
    public static Set<Integer> getStringStates() {
        if (stringStates == null) {
            createStringStates();
        }
        return stringStates;
    }

    //Returns comment symbol
    public static String getCommentSymbol() {
        return commentSymbol;
    }

}

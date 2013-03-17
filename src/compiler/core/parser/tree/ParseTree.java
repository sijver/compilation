package compiler.core.parser.tree;

import compiler.core.parser.Nonterminal;
import compiler.core.parser.perl.ParsingTable;
import compiler.core.parser.perl.PerlGrammar;
import compiler.core.scanner.finiteautomaton.FiniteAutomaton;
import compiler.core.scanner.finiteautomaton.LexicalUnit;
import compiler.core.scanner.finiteautomaton.Token;
import compiler.core.scanner.finiteautomaton.perl.PerlAutomatonSettings;
import compiler.core.utils.FileLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
//Class for constructing parse tree and for working with it
public class ParseTree {

    private List<Token> inputTokensList;    //Input tokens for constructing the tree
    private ParsingTable parsingTable;  //Parsing table which is using for tree constructing
    private List<Integer> outputRulesList;  //The sequence of rules which were used to construct the tree
    private LinkedList<Object> stack;   //Stack of the parser
    private LinkedList<TreeNode> treeStack; //Stack with tree nodes which is appropriate to the parser stack content
    private TreeNode treeRoot;  //The root node of the tree

    private String parsingErrorMessage;

    public ParseTree(List<Token> inputTokensList) {
        this.inputTokensList = inputTokensList;
        outputRulesList = new LinkedList<Integer>();
        initParsingTable();
        initStack();
    }

    //Initialization of parsing table for Perl translating
    private void initParsingTable() {
        parsingTable = new ParsingTable(Nonterminal.values().length, LexicalUnit.values().length);
        parsingTable.fillTheParsingTable();
    }

    //Stack initialization
    private void initStack() {
        stack = new LinkedList<Object>();
        stack.addFirst(LexicalUnit.getStackBottomSymbol());
        stack.addFirst(Nonterminal.getStartSymbol());

        treeRoot = new TreeNode(Nonterminal.getStartSymbol(), -1);
        treeStack = new LinkedList<TreeNode>();
        treeStack.addFirst(treeRoot);
    }

    //Parsing method. Constructs the tree. Returns false if error
    public boolean doParsing() {
        boolean isSymbolRead;
        for (Token symbol : inputTokensList) {  //Loop for each input token
            isSymbolRead = false;   // Shows is the symbol has being read and we can work with the next input symbol
            while (!isSymbolRead) {
                if (stack.getFirst().getClass() == LexicalUnit.class) { // Top-most symbol of the stack is the LexicalUnit
                    if (symbol.getLexicalUnit() == stack.getFirst()) {  //Check whether stack top symbol is the same as the current input token
                        if(stack.removeFirst() != LexicalUnit.EOF){ //Set tree node value of not end of file
                            treeStack.removeFirst().setNodeValue(symbol);
                        }

//                        Debug info
//                        System.out.println("read " + symbol.getLexicalUnit());
//                        System.out.println(Arrays.toString(stack.toArray()));

                        isSymbolRead = true;    //Symbol has been read and we can process the next one
                    } else {    //If stack top lexical unit is not the same as the current token -> then parsing error
                        parsingErrorMessage = "Parsing error! Top-most symbol of the stack is not equal to the input stream symbol! (" + stack.getFirst() + " " + symbol.getLexicalUnit() + ")";
                        return false;
                    }
                } else {    // Top-most symbol of the stack is the Nonterminal
                    int ruleNumber = parsingTable.getRuleFromCell((Nonterminal) stack.getFirst(), symbol.getLexicalUnit()); //Get parsing table rule for top stack symbol and current input token
                    if (ruleNumber != -1) { //Check whether rule is valid (is existing)
                        outputRulesList.add(ruleNumber);
                        stack.removeFirst();    //Remove top of the stack

                        TreeNode parentNode = treeStack.removeFirst();

                        List<Object> addToStack = PerlGrammar.getRuleByNumber(ruleNumber).getRightSideRule();   //Rule's objects to add to stack
                        for (int i = addToStack.size() - 1; i >= 0; i--) {  //For each right side rule unit that must be added to stack (in vice versa order)
                            if (addToStack.get(i) != null) {    //If not lambda, do...
                                stack.addFirst(addToStack.get(i));  //Add unit from rule to stack

                                TreeNode newNode = new TreeNode(addToStack.get(i), ruleNumber); //Create tree node
                                newNode.setParentNode(parentNode);  //Define tree node parent
                                treeStack.addFirst(newNode);    //Add tree node to stack
                            }
                        }
                    } else {    //No such rule in parsing table. Parsing error
                        parsingErrorMessage = "Parsing error! No such rule! (" + stack.getFirst() + " " + symbol.getLexicalUnit() + ")";
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<Integer> getOutputRulesList() {
        return outputRulesList;
    }

    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    public String getParsingErrorMessage() {
        return parsingErrorMessage;
    }

    //For debug. Test of the parser and code generation
    public static void main(String[] args) throws IOException {
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton();
        finiteAutomaton.setStartState(PerlAutomatonSettings.getStartState());
        finiteAutomaton.setAcceptStatesList(PerlAutomatonSettings.getAcceptStatesList());
        finiteAutomaton.setTransitionsList(PerlAutomatonSettings.getTransitions());
        finiteAutomaton.setBackTransitions(PerlAutomatonSettings.getBackTransitions());
        finiteAutomaton.setCommentSymbol(PerlAutomatonSettings.getCommentSymbol());
        finiteAutomaton.setStringStates(PerlAutomatonSettings.getStringStates());

        ParseTree parseTree = new ParseTree(finiteAutomaton.getScannedTokens(FileLoader.readTheFile("C:\\Users\\Mark\\Desktop\\perl.txt")));

        parseTree.doParsing();

        System.out.println(Arrays.toString(parseTree.getOutputRulesList().toArray()));
        System.out.println(parseTree.getOutputRulesList().size());
        System.out.println(parseTree.treeRoot.getTreeNodes());
        System.out.println(parseTree.treeRoot.getTreeText());

        System.out.println(parseTree.treeRoot.generateCode());

        System.out.println(parseTree.treeRoot.getTreeNodes());
        System.out.println(parseTree.treeRoot.getTreeText());

    }
}

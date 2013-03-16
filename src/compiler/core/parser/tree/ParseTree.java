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
public class ParseTree {

    private List<Token> inputTokensList;
    private ParsingTable parsingTable;
    private List<Integer> outputRulesList;
    private LinkedList<Object> stack;
    private LinkedList<TreeNode> treeStack;
    private TreeNode treeRoot;

    public ParseTree(List<Token> inputTokensList) {
        this.inputTokensList = inputTokensList;
        outputRulesList = new LinkedList<Integer>();
        initParsingTable();
        initStack();
    }

    private void initParsingTable() {
        parsingTable = new ParsingTable(Nonterminal.values().length, LexicalUnit.values().length);
        parsingTable.fillTheParsingTable();
    }

    private void initStack() {
        stack = new LinkedList<Object>();
        stack.addFirst(LexicalUnit.getStackBottomSymbol());
        stack.addFirst(Nonterminal.getStartSymbol());

        treeRoot = new TreeNode(Nonterminal.getStartSymbol(), -1);
        treeStack = new LinkedList<TreeNode>();
        treeStack.addFirst(treeRoot);
    }

    public void doParsing() {
        boolean isSymbolRead;
        for (Token symbol : inputTokensList) {
            isSymbolRead = false;   // Shows is the symbol has being read and we can work with the next input symbol
            while (!isSymbolRead) {
                if (stack.getFirst().getClass() == LexicalUnit.class) {
                    if (symbol.getLexicalUnit() == stack.getFirst()) {
                        if(stack.removeFirst() != LexicalUnit.EOF){
                            treeStack.removeFirst().setNodeValue(symbol);
                        }

//                        Debug info
//                        System.out.println("read " + symbol.getLexicalUnit());
//                        System.out.println(Arrays.toString(stack.toArray()));

                        isSymbolRead = true;
                    } else {
                        System.out.println("Parsing error! Top-most symbol of the stack is not equal to the input stream symbol!");
                        System.exit(1);
                    }
                } else {    // Top-most symbol of the stack is the Nonterminal
                    int ruleNumber = parsingTable.getRuleFromCell((Nonterminal) stack.getFirst(), symbol.getLexicalUnit());
                    if (ruleNumber != -1) {
                        outputRulesList.add(ruleNumber);
                        stack.removeFirst();

                        TreeNode parentNode = treeStack.removeFirst();

                        List<Object> addToStack = PerlGrammar.getRuleByNumber(ruleNumber).getRightSideRule();
                        for (int i = addToStack.size() - 1; i >= 0; i--) {
                            if (addToStack.get(i) != null) {
                                stack.addFirst(addToStack.get(i));

                                TreeNode newNode = new TreeNode(addToStack.get(i), ruleNumber);
                                newNode.setParentNode(parentNode);
                                treeStack.addFirst(newNode);
                            }
                        }
                    } else {
                        System.out.println("Parsing error! No such rule! " + stack.getFirst() + " " + symbol.getLexicalUnit());
                        System.exit(1);
                    }
                }
            }
        }
    }

    public List<Integer> getOutputRulesList() {
        return outputRulesList;
    }

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

package compiler.core.parser.tree;


import compiler.core.parser.Nonterminal;
import compiler.core.parser.tree.asm.CodeGenerator;
import compiler.core.parser.tree.asm.GenerationRule;
import compiler.core.scanner.finiteautomaton.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
public class TreeNode {

    private Object nodeValue;

    private LinkedList<TreeNode> childrenNodes;

    private TreeNode parentNode = null;

    private int rule = -1;

    private CodeGenerator codeGenerator;

    public TreeNode(Object nodeValue, int rule) {
        this.nodeValue = nodeValue;
        this.rule = rule;
        childrenNodes = new LinkedList<TreeNode>();
        codeGenerator = new CodeGenerator();
    }

    public boolean hasChildren() {
        return !childrenNodes.isEmpty();
    }

    public boolean isCompressable() {
        for (TreeNode child : childrenNodes) {
            if (child.hasChildren()) {
                return false;
            }
        }
        return true;
    }

    public void removeChildrenNodes() {
        for (TreeNode child : childrenNodes) {
            child.removeParent();
        }
        childrenNodes.clear();
    }

    public void removeParent() {
        parentNode.childrenNodes.remove(this);
        parentNode = null;
    }

    public void addChildNode(TreeNode childNode) {
        childNode.parentNode = this;
        childrenNodes.addFirst(childNode);
    }

    public void setParentNode(TreeNode parentNode) {
        parentNode.addChildNode(this);
    }

    public TreeNode getParentNode() {
        return parentNode;
    }

    public Object getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(Object nodeValue) {
        this.nodeValue = nodeValue;
    }

    public LinkedList<TreeNode> getChildrenNodes() {
        return childrenNodes;
    }

    public void setChildrenNodes(LinkedList<TreeNode> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    public int getRule() {
        return rule;
    }

    public int getTreeNodes() {
        int sum = 0;
        for (TreeNode child : childrenNodes) {
            sum += child.getTreeNodes();
        }
        sum++;
        return sum;
    }

    public String getTreeText() {
        String a = "";
        a = a.concat(nodeValue.toString() + " " + rule + "\n");
        for (TreeNode child : childrenNodes) {
            a = a.concat("-" + child.getBranchText("|"));
        }
        return a;
    }

    private String getBranchText(String s) {
        String a = "";
        a = a.concat(nodeValue.toString() + " " + rule + "\n");
        for (TreeNode child : childrenNodes) {
            a = a.concat(s + "-" + child.getBranchText("|" + s));
        }
        return a;
    }

    public StringBuilder generateCode() {
        StringBuilder returnCode = new StringBuilder();
        if (!isCompressable()) {
            for (TreeNode child : childrenNodes) {
                returnCode.append(child.generateCode());
            }
        }
        if (isCompressable()) {
            if (childrenNodes.size() == 1) {
                this.nodeValue = childrenNodes.getFirst().nodeValue;
                removeChildrenNodes();
            }
            if (childrenNodes.size() > 1) {
                System.out.println("there must be computing");
                List<Token> commandTokens = new LinkedList<Token>();
                for (TreeNode node : childrenNodes) {
                    if (node.nodeValue.getClass() == Token.class) {
                        commandTokens.add((Token) node.nodeValue);
                    }
                    node.parentNode = null;
                }
                if (commandTokens.size() == 1) {
                    this.nodeValue = commandTokens.get(0);
                } else {
                    GenerationRule generationRule = codeGenerator.generateOperationCode(commandTokens);
                    returnCode.append(generationRule.getGeneratedCode());
                    this.nodeValue = generationRule.getCompressIntoToken();
                }
            }
            if (childrenNodes.isEmpty() && nodeValue.getClass() == Nonterminal.class) {
                removeParent();
            }
            childrenNodes.clear();
        }
        return returnCode;
    }

}

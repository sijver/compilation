package compiler.core.parser.tree.asm;

import compiler.core.scanner.finiteautomaton.Token;

/**
 * Created with IntelliJ IDEA.
 */
//Describes the compression of tree node.
// Shows which kind of node will be after compressing children nodes
// and code which was generated in process of compressing.
public class GenerationRule {

    private Token compressIntoToken;    //Which kind of node will be after compressing children nodes

    private String generatedCode;   //Code which was generated in process of compressing

    public GenerationRule() {
    }

    public Token getCompressIntoToken() {
        return compressIntoToken;
    }

    public void setCompressIntoToken(Token compressIntoToken) {
        this.compressIntoToken = compressIntoToken;
    }

    public int getRegisterUsing() {
        return compressIntoToken.getRegisterUsing();
    }

    public void setRegisterUsing(int registerUsing) {
        compressIntoToken.setRegisterUsing(registerUsing);
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }
}

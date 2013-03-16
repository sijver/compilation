package compiler.core.parser.tree.asm;

import compiler.core.scanner.finiteautomaton.Token;

/**
 * Created with IntelliJ IDEA.
 */
public class GenerationRule {

    private Token compressIntoToken;

    private String generatedCode;

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

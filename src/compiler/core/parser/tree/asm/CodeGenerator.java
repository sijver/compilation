package compiler.core.parser.tree.asm;


import compiler.core.scanner.finiteautomaton.LexicalUnit;
import compiler.core.scanner.finiteautomaton.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
public class CodeGenerator {

    private static int numOfRegisters = 16;

    private Register register;
    private List<Variable> variables;
    private Scope mainScope;

    public CodeGenerator() {
        register = new Register(numOfRegisters);
        variables = new LinkedList<Variable>();
        mainScope = new Scope();
    }

    public GenerationRule generateOperationCode(List<Token> commandTokens) {
        if (commandContainsLexicalUnit(commandTokens, LexicalUnit.PLUS)) {
            return addition(commandTokens);
        }
        GenerationRule genRule = new GenerationRule();
        genRule.setGeneratedCode("A");
        genRule.setCompressIntoToken(new Token(LexicalUnit.MINUS, null));
        return genRule;
    }

    public boolean commandContainsLexicalUnit(List<Token> tokensList, LexicalUnit lexicalUnit) {
        for (Token token : tokensList) {
            if (token.getLexicalUnit() == lexicalUnit) {
                return true;
            }
        }
        return false;
    }

    public GenerationRule addition(List<Token> commandTokens) {
        GenerationRule generationRule = new GenerationRule();
        StringBuilder command = new StringBuilder();
        if (commandTokens.get(0).getLexicalUnit() == LexicalUnit.PLUS) {
            generationRule.setCompressIntoToken(new Token(LexicalUnit.PLUS, null));
            int reg = register.getNextUnusingRegister();
            if (reg != -1) {
                register.setRegisterUsing(reg, true);
                generationRule.setRegisterUsing(reg);
                command.append("add r");
                command.append(reg + ", ");
                Token token = commandTokens.get(1);
                if (token.getLexicalUnit() == LexicalUnit.VAR) {
                    command.append(token.getReturnValue().substring(1));
                } else if(token.getRegisterUsing() == -1){
                    command.append("#" + token.getReturnValue());
                } else {
                    command.append("r" + token.getRegisterUsing());
                    register.setRegisterUsing(token.getRegisterUsing(), false);
                }
            } else {
                System.out.println("ERROR!");
                System.exit(1);
            }
        } else {
            generationRule.setCompressIntoToken(new Token(LexicalUnit.INTEGER, null));
            command.append(", ");
            Token token = commandTokens.get(0);
            if (token.getLexicalUnit() == LexicalUnit.VAR) {
                command.append(token.getReturnValue().substring(1));
            } else if (token.getRegisterUsing() == -1){
                command.append("#" + token.getReturnValue());
            } else {
                command.append("r" + token.getRegisterUsing());
                register.setRegisterUsing(token.getRegisterUsing(), false);
            }
            command.append("\n");
            generationRule.setRegisterUsing(commandTokens.get(1).getRegisterUsing());
        }
        generationRule.setGeneratedCode(command.toString());
        return generationRule;
    }


}

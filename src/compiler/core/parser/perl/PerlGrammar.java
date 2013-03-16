package compiler.core.parser.perl;

import compiler.core.parser.GrammarRule;
import compiler.core.parser.Nonterminal;
import compiler.core.scanner.finiteautomaton.LexicalUnit;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
//Grammar of Perl. Consists of list of all grammar rules.
public class PerlGrammar {

    private static List<GrammarRule> grammarRules;

    private static void createGrammarRules(){
        grammarRules = new LinkedList<GrammarRule>();
        grammarRules.add(new GrammarRule(Nonterminal.PROGRAM, new Object[]{Nonterminal.FUNCTION_LIST, Nonterminal.A}));
        grammarRules.add(new GrammarRule(Nonterminal.PROGRAM, new Object[]{Nonterminal.INSTRUCTION_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.A, new Object[]{Nonterminal.INSTRUCTION_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.A, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.FUNCTION_LIST, new Object[]{Nonterminal.FUNCTION, Nonterminal.V_FUNCTION_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.V_FUNCTION_LIST, new Object[]{Nonterminal.FUNCTION, Nonterminal.V_FUNCTION_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.V_FUNCTION_LIST, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.FUNCTION, new Object[]{LexicalUnit.SUB, LexicalUnit.IDENTIFIER, Nonterminal.FUNCTION_ARGUMENT, LexicalUnit.LBRACE, Nonterminal.INSTRUCTION_LIST, LexicalUnit.RBRACE}));
        grammarRules.add(new GrammarRule(Nonterminal.FUNCTION_ARGUMENT, new Object[]{LexicalUnit.LPAR, Nonterminal.ARGUMENT_LIST, LexicalUnit.RPAR}));
        grammarRules.add(new GrammarRule(Nonterminal.FUNCTION_ARGUMENT, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.ARGUMENT_LIST, new Object[]{Nonterminal.U_ARGUMENT_LIST, Nonterminal.V_ARGUMENT_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.U_ARGUMENT_LIST, new Object[]{LexicalUnit.VAR}));
        grammarRules.add(new GrammarRule(Nonterminal.U_ARGUMENT_LIST, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.V_ARGUMENT_LIST, new Object[]{LexicalUnit.COMMA, LexicalUnit.VAR, Nonterminal.V_ARGUMENT_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.V_ARGUMENT_LIST, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.INSTRUCTION_LIST, new Object[]{Nonterminal.U_INSTRUCTION_LIST, Nonterminal.V_INSTRUCTION_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.U_INSTRUCTION_LIST, new Object[]{LexicalUnit.LBRACE, Nonterminal.INSTRUCTION_LIST, LexicalUnit.RBRACE}));
        grammarRules.add(new GrammarRule(Nonterminal.U_INSTRUCTION_LIST, new Object[]{Nonterminal.INSTRUCTION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_INSTRUCTION_LIST, new Object[]{LexicalUnit.SEMICOLON, Nonterminal.INSTRUCTION, Nonterminal.V_INSTRUCTION_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.V_INSTRUCTION_LIST, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.FUNCTION_CALL, new Object[]{LexicalUnit.CALL_MARK, LexicalUnit.IDENTIFIER, Nonterminal.B}));
        grammarRules.add(new GrammarRule(Nonterminal.B, new Object[]{LexicalUnit.LPAR, Nonterminal.ARGUMENT_CALL_LIST, LexicalUnit.RPAR}));
        grammarRules.add(new GrammarRule(Nonterminal.B, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.ARGUMENT_CALL_LIST, new Object[]{Nonterminal.U_ARGUMENT_CALL_LIST, Nonterminal.V_ARGUMENT_CALL_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.U_ARGUMENT_CALL_LIST, new Object[]{Nonterminal.INSTRUCTION}));
        grammarRules.add(new GrammarRule(Nonterminal.U_ARGUMENT_CALL_LIST, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.V_ARGUMENT_CALL_LIST, new Object[]{LexicalUnit.COMMA, Nonterminal.EXPRESSION, Nonterminal.V_ARGUMENT_CALL_LIST}));
        grammarRules.add(new GrammarRule(Nonterminal.V_ARGUMENT_CALL_LIST, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.INSTRUCTION, new Object[]{Nonterminal.CONDITION}));
        grammarRules.add(new GrammarRule(Nonterminal.INSTRUCTION, new Object[]{Nonterminal.EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.INSTRUCTION, new Object[]{LexicalUnit.RETURN, Nonterminal.EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.INSTRUCTION, new Object[]{LexicalUnit.VAR, LexicalUnit.ASSIGN_MARK, Nonterminal.EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.CONDITION, new Object[]{LexicalUnit.IF, Nonterminal.EXPRESSION, LexicalUnit.LBRACE, Nonterminal.INSTRUCTION_LIST, LexicalUnit.RBRACE, Nonterminal.CONDITION_END}));
        grammarRules.add(new GrammarRule(Nonterminal.CONDITION, new Object[]{LexicalUnit.UNLESS, Nonterminal.EXPRESSION, LexicalUnit.LBRACE, Nonterminal.INSTRUCTION_LIST, LexicalUnit.RBRACE, Nonterminal.CONDITION_END}));
        grammarRules.add(new GrammarRule(Nonterminal.CONDITION_END, new Object[]{LexicalUnit.ELSE, LexicalUnit.LBRACE, Nonterminal.INSTRUCTION_LIST, LexicalUnit.RBRACE, Nonterminal.CONDITION_END}));
        grammarRules.add(new GrammarRule(Nonterminal.CONDITION_END, new Object[]{LexicalUnit.ELSEIF, Nonterminal.EXPRESSION, LexicalUnit.LBRACE, Nonterminal.INSTRUCTION_LIST, LexicalUnit.RBRACE, Nonterminal.CONDITION_END}));
        grammarRules.add(new GrammarRule(Nonterminal.CONDITION_END, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.EXPRESSION, new Object[]{LexicalUnit.LOW_NOT, Nonterminal.T_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.EXPRESSION, new Object[]{Nonterminal.T_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.T_EXPRESSION, new Object[]{Nonterminal.K_EXPRESSION, Nonterminal.V_T_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_T_EXPRESSION, new Object[]{LexicalUnit.LAZY_OR, Nonterminal.K_EXPRESSION, Nonterminal.V_T_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_T_EXPRESSION, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.K_EXPRESSION, new Object[]{Nonterminal.K1_EXPRESSION, Nonterminal.V_K_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K_EXPRESSION, new Object[]{LexicalUnit.LAZY_AND, Nonterminal.K1_EXPRESSION, Nonterminal.V_K_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K_EXPRESSION, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.K1_EXPRESSION, new Object[]{Nonterminal.K2_EXPRESSION, Nonterminal.V_K1_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K1_EXPRESSION, new Object[]{LexicalUnit.EQUALS, Nonterminal.K2_EXPRESSION, Nonterminal.V_K1_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K1_EXPRESSION, new Object[]{LexicalUnit.DIFFERENT, Nonterminal.K2_EXPRESSION, Nonterminal.V_K1_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K1_EXPRESSION, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.K2_EXPRESSION, new Object[]{Nonterminal.K3_EXPRESSION, Nonterminal.V_K2_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K2_EXPRESSION, new Object[]{LexicalUnit.LOWER, Nonterminal.K3_EXPRESSION, Nonterminal.V_K2_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K2_EXPRESSION, new Object[]{LexicalUnit.GREATER, Nonterminal.K3_EXPRESSION, Nonterminal.V_K2_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K2_EXPRESSION, new Object[]{LexicalUnit.LOWER_EQUALS, Nonterminal.K3_EXPRESSION, Nonterminal.V_K2_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K2_EXPRESSION, new Object[]{LexicalUnit.GREATER_EQUALS, Nonterminal.K3_EXPRESSION, Nonterminal.V_K2_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K2_EXPRESSION, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.K3_EXPRESSION, new Object[]{Nonterminal.K4_EXPRESSION, Nonterminal.V_K3_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K3_EXPRESSION, new Object[]{LexicalUnit.PLUS, Nonterminal.K4_EXPRESSION, Nonterminal.V_K3_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K3_EXPRESSION, new Object[]{LexicalUnit.MINUS, Nonterminal.K4_EXPRESSION, Nonterminal.V_K3_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K3_EXPRESSION, new Object[]{LexicalUnit.CONCAT_MARK, Nonterminal.K4_EXPRESSION, Nonterminal.V_K3_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K3_EXPRESSION, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.K4_EXPRESSION, new Object[]{Nonterminal.K5_EXPRESSION, Nonterminal.V_K4_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K4_EXPRESSION, new Object[]{LexicalUnit.TIMES, Nonterminal.K5_EXPRESSION, Nonterminal.V_K4_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K4_EXPRESSION, new Object[]{LexicalUnit.DIVIDE, Nonterminal.K5_EXPRESSION, Nonterminal.V_K4_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.V_K4_EXPRESSION, new Object[]{null}));
        grammarRules.add(new GrammarRule(Nonterminal.K5_EXPRESSION, new Object[]{LexicalUnit.HIGH_NOT, Nonterminal.K6_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.K5_EXPRESSION, new Object[]{Nonterminal.K6_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.K6_EXPRESSION, new Object[]{LexicalUnit.LPAR, Nonterminal.EXPRESSION, LexicalUnit.RPAR}));
        grammarRules.add(new GrammarRule(Nonterminal.K6_EXPRESSION, new Object[]{Nonterminal.SIMPLE_EXPRESSION}));
        grammarRules.add(new GrammarRule(Nonterminal.SIMPLE_EXPRESSION, new Object[]{Nonterminal.FUNCTION_CALL}));
        grammarRules.add(new GrammarRule(Nonterminal.SIMPLE_EXPRESSION, new Object[]{LexicalUnit.LSQUARE, LexicalUnit.VAR, LexicalUnit.RSQUARE}));
        grammarRules.add(new GrammarRule(Nonterminal.SIMPLE_EXPRESSION, new Object[]{LexicalUnit.INTEGER}));
        grammarRules.add(new GrammarRule(Nonterminal.SIMPLE_EXPRESSION, new Object[]{LexicalUnit.STRING}));
    }

    public static List<GrammarRule> getGrammarRules() {
        if(grammarRules == null){
            createGrammarRules();
        }
        return grammarRules;
    }

    public static GrammarRule getRuleByNumber(int ruleNumber){
        if(grammarRules == null){
            createGrammarRules();
        }
        return grammarRules.get(ruleNumber);
    }

}
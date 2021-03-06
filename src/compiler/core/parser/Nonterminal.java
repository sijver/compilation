package compiler.core.parser;

/**
 * Created with IntelliJ IDEA.
 */
//All nonterminals of Perl Grammar
public enum Nonterminal {
    PROGRAM,
    A,
    FUNCTION_LIST,
    V_FUNCTION_LIST,
    FUNCTION,
    FUNCTION_ARGUMENT,
    ARGUMENT_LIST,
    U_ARGUMENT_LIST,
    V_ARGUMENT_LIST,
    INSTRUCTION_LIST,
    U_INSTRUCTION_LIST,
    V_INSTRUCTION_LIST,
    FUNCTION_CALL,
    B,
    ARGUMENT_CALL_LIST,
    U_ARGUMENT_CALL_LIST,
    V_ARGUMENT_CALL_LIST,
    INSTRUCTION,
    CONDITION,
    CONDITION_END,
    EXPRESSION,
    T_EXPRESSION,
    V_T_EXPRESSION,
    K_EXPRESSION,
    V_K_EXPRESSION,
    K1_EXPRESSION,
    V_K1_EXPRESSION,
    K2_EXPRESSION,
    V_K2_EXPRESSION,
    K3_EXPRESSION,
    V_K3_EXPRESSION,
    K4_EXPRESSION,
    V_K4_EXPRESSION,
    K5_EXPRESSION,
    K6_EXPRESSION,
    SIMPLE_EXPRESSION;

    public static Nonterminal getStartSymbol() {
        return PROGRAM;
    }
}

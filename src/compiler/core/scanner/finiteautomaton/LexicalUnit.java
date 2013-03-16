package compiler.core.scanner.finiteautomaton;

/**
 * Created with IntelliJ IDEA.
 */
//List of Lexical units fo perl
public enum LexicalUnit {
    LPAR,
    RPAR,
    LOW_NOT,
    HIGH_NOT,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    ASSIGN_MARK,
    CONCAT_MARK,
    LAZY_AND,
    LAZY_OR,
    EQUALS,
    DIFFERENT,
    GREATER,
    LOWER,
    GREATER_EQUALS,
    LOWER_EQUALS,
    VAR,
    INTEGER,
    STRING,
    ELSE,
    LBRACE,
    RBRACE,
    ELSEIF,
    IF,
    UNLESS,
    RETURN,
    COMMA,
    CALL_MARK,
    SEMICOLON,
    SUB,
    IDENTIFIER,
    LSQUARE,
    RSQUARE,
    EOF;

    //Returns the EOF symbol
    public static LexicalUnit getStackBottomSymbol() {
        return EOF;
    }
}

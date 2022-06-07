public class Token {
    enum Type {
        WHITESPACE,
        COMMA,
        NUMERIC_LITERAL,
        ADDITIVE_OPERATOR,
        MULTIPLICATIVE_OPERATOR,
        PARENTHESIS_OPEN,
        PARENTHESIS_CLOSE,
        ONE_PARM_FUNCTION,
        TWO_PARM_FUNCTION
    }
    Type type;
    double numericValue;
    char operator;
    String function;

    public Token(Type type, double numericValue) {
        this.type = type;
        this.numericValue = numericValue;
    }

    public Token(Type type, char operator) {
        this.type = type;
        this.operator = operator;
    }

    public Token(Type type) {
        this.type = type;
    }

    public Token(Type type, String function) {
        this.type = type;
        this.function = function;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                switch (type) {
                    case WHITESPACE, COMMA -> null;
                    case NUMERIC_LITERAL -> ", numericValue=" + numericValue;
                    case ADDITIVE_OPERATOR, MULTIPLICATIVE_OPERATOR -> ", operator=" + operator;
                    case PARENTHESIS_OPEN -> '(';
                    case PARENTHESIS_CLOSE -> ')';
                    case ONE_PARM_FUNCTION, TWO_PARM_FUNCTION -> ", function=" + function;
                } +
                '}';
    }
}

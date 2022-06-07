public class Parser {
    public static class ParserSyntaxException extends Exception {
        public ParserSyntaxException(String msg) {
            super("Syntax error while parsing: " + msg);
        }
    }
    private final Tokenizer tokenizer;
    private Token lookAhead;
    public Parser(String input) {
        this.tokenizer = new Tokenizer(input);
    }
    private Token eat(Token.Type tokenType) throws ParserSyntaxException {
        Token token = lookAhead;
        if (token == null)
            throw new ParserSyntaxException("Unexpected end of input, expected: " + tokenType);
        if (token.type != tokenType)
            throw new ParserSyntaxException("Unexpected token: " +
                    switch (token.type) {
                        case WHITESPACE -> null;
                        case NUMERIC_LITERAL -> token.numericValue;
                        case ADDITIVE_OPERATOR, MULTIPLICATIVE_OPERATOR -> token.operator;
                        case PARENTHESIS_OPEN -> '(';
                        case PARENTHESIS_CLOSE -> ')';
                        case ONE_PARM_FUNCTION -> token.function;
                    } +
                    ", expected: " + tokenType);
        lookAhead = tokenizer.getNextToken();
        return token;
    }
    public ASTNode parse() {
        try {
            lookAhead = tokenizer.getNextToken();
        } catch (ParserSyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return Expression();
    }
    private ASTNode Expression() {
        return AdditiveExpression();
    }
    private ASTNode PrimaryExpression(){
        return switch (lookAhead.type) {
            case PARENTHESIS_OPEN -> ParenthesizedExpression();
            case ONE_PARM_FUNCTION -> OneParmFunction();
            default -> NumericLiteral();
        };
    }

    private ASTNode OneParmFunction() {
        try {
            Token function = eat(Token.Type.ONE_PARM_FUNCTION);
            return new ASTNode(function, PrimaryExpression(), null);
        } catch (ParserSyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }


    private ASTNode NumericLiteral() {
        try {
            Token token = eat(Token.Type.NUMERIC_LITERAL);
            return new ASTNode(token, null, null);
        } catch (ParserSyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
    private ASTNode AdditiveExpression(){
        ASTNode left = MultiplicativeExpression();
        while (lookAhead != null && lookAhead.type == Token.Type.ADDITIVE_OPERATOR) {
            try {
                Token tokenOp = eat(Token.Type.ADDITIVE_OPERATOR);
                ASTNode right = MultiplicativeExpression();
                left = new ASTNode(tokenOp, left, right);
            } catch (ParserSyntaxException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return left;
    }
    private ASTNode MultiplicativeExpression(){
        ASTNode left = PrimaryExpression();
        while (lookAhead != null && lookAhead.type == Token.Type.MULTIPLICATIVE_OPERATOR) {
            try {
                Token tokenOp = eat(Token.Type.MULTIPLICATIVE_OPERATOR);
                ASTNode right = PrimaryExpression();
                left = new ASTNode(tokenOp, left, right);
            } catch (ParserSyntaxException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return left;
    }
    private ASTNode ParenthesizedExpression() {
        try {
            eat(Token.Type.PARENTHESIS_OPEN);
            ASTNode expression = Expression();
            eat(Token.Type.PARENTHESIS_CLOSE);
            return expression;
        } catch (ParserSyntaxException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}

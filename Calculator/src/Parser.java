public class Parser {
    public static class ParserSyntaxError extends Exception {
        public ParserSyntaxError(String msg) {
            System.out.println("Syntax error while parsing: "+ msg);
        }
    }
    Tokenizer tokenizer;
    String input;
    ASTNode lookAhead;



    public Parser(String input) {
        this.input = input;
        this.tokenizer = new Tokenizer(input);
    }

    ASTNode eat(Tokenizer.Token token) throws ParserSyntaxError {
        ASTNode node = lookAhead;
        if (node == null)
            throw new ParserSyntaxError("Unexpected end of input, expected: " + token);
        if (node.type != type)
            throw new ParserSyntaxError("Unexpected token: " + node.value + ", expected: " + token);
        lookAhead = tokenizer.getNextToken();
        return node;
    }

    ASTNode parse() {
        try {
            lookAhead = tokenizer.getNextToken();
        } catch (ParserSyntaxError e) {
            System.exit(1);
        }
        return Expression();
    }

    ASTNode Expression() {
        ASTNode node;
        switch (lookAhead.type) {
            case NUMERIC_LITERAL:
                node = NumericLiteral();
                break;
            case ADDITIVE_OPERATOR:
                node = AdditiveExpression();
                break;
            default:
                node = null;
        }
        return node;
    }

    ASTNode NumericLiteral() {
        try {
            return eat(Tokenizer.Token.TOKEN_NUMBER);
        } catch (ParserSyntaxError e) {
            System.exit(1);
        }
        return null;
    }

    ASTNode PrmaryExpression() {
        switch (lookAhead.type) {
            case NUMERIC_LITERAL:

        }
    }
    ASTNode AdditiveExpression(){
        ASTNode left = NumericLiteral();
        while (lookAhead.type == Tokenizer.Token.TOKEN_PLUS) {
            try {
                ASTNode plus = eat(Tokenizer.Token.TOKEN_PLUS);
                ASTNode right = NumericLiteral();
                left = new ASTNode(plus.type, left, right);
            } catch (ParserSyntaxError e) {
                System.exit(1);
            }
        }
        return left;
    }
}

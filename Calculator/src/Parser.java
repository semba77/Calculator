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

    ASTNode eat(Tokenizer.Token type) throws ParserSyntaxError {
        ASTNode token = lookAhead;
        if (token == null)
            throw new ParserSyntaxError("Unexpected end of input, expected: "+type);
        if (token.type != type)
            throw new ParserSyntaxError("Unexpected token: " + token.value + ", expected: " + type);
        lookAhead = tokenizer.getNextToken();
        return token;
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
     /*   switch (lookAhead.type) {
            case TOKEN_NUMBER:
                return NumericLiteral();
            case TOKEN_PLUS: */
                return PlusExpression();
         /*   default:
                return null;
        }*/
    }

    ASTNode NumericLiteral() {
        try {
            return eat(Tokenizer.Token.TOKEN_NUMBER);
        } catch (ParserSyntaxError e) {
            System.exit(1);
        }
        return null;
    }

    ASTNode PlusExpression(){
        ASTNode left = NumericLiteral();
        if (lookAhead.type == Tokenizer.Token.TOKEN_PLUS) {
            try {
                ASTNode plus = eat(Tokenizer.Token.TOKEN_PLUS);
                plus.left = left;
                plus.right = NumericLiteral();
                return plus;
            } catch (ParserSyntaxError e) {
                System.exit(1);
            }
        }
        return null;
    }
}

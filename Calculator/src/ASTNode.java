public class ASTNode {

    Token token;
    ASTNode left;
    ASTNode right;

    public ASTNode(Token token, ASTNode left, ASTNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }

    public double eval() throws Exception {
        return switch (token.type) {
            case NUMERIC_LITERAL -> token.numericValue;
            case ADDITIVE_OPERATOR, MULTIPLICATIVE_OPERATOR -> switch (token.operator) {
                case '+' -> left.eval() + right.eval();
                case '-' -> left.eval() - right.eval();
                case '*' -> left.eval() * right.eval();
                case '/' -> left.eval() / right.eval();
                default -> throw new Exception("Invalid token operator" + token.operator);
            };
            case ONE_PARM_FUNCTION -> switch (token.function) {
                case "sin" -> Math.sin(left.eval());
                case "cos" -> Math.cos(left.eval());
                case "tan" -> Math.tan(left.eval());
                case "log" -> Math.log(left.eval());
                case "exp" -> Math.exp(left.eval());
                case "sqrt" -> Math.sqrt(left.eval());
                default -> throw new Exception("Invalid token function" + token.function);
            };
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                token +
                switch (token.type) {
                    case WHITESPACE, NUMERIC_LITERAL -> "";
                    case ADDITIVE_OPERATOR, MULTIPLICATIVE_OPERATOR -> ",left=" + left + ",right=" + right;
                    case PARENTHESIS_OPEN -> null;
                    case PARENTHESIS_CLOSE -> null;
                    case ONE_PARM_FUNCTION -> ",left=" + left;
                }
                + '}';
    }
}

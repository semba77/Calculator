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
            case TWO_PARM_FUNCTION -> switch (token.function) {
                case "min" -> Math.min(left.eval(), right.eval());
                case "max" -> Math.max(left.eval(), right.eval());
                case "nsd" -> nsd((int)left.eval(), (int) right.eval());
                case "nsn" -> nsn((int) left.eval(), (int) right.eval());
                default -> throw new Exception("Invalid token function" + token.function);
            };

            default -> 0;
        };
    }

    private int nsn(int a, int b) {
        for(int i = 1; i <= b; i++) {
            if (i*a % b == 0)
                return Math.abs(i*a);
        }
        return 0;
    }

    private int nsd(int a, int b) {
        if (b==0) return a;
        return nsd(b,a%b);
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                token +
                switch (token.type) {
                    case WHITESPACE, NUMERIC_LITERAL, COMMA -> "";
                    case ADDITIVE_OPERATOR, MULTIPLICATIVE_OPERATOR, TWO_PARM_FUNCTION -> ",left=" + left + ",right=" + right;
                    case PARENTHESIS_OPEN -> null;
                    case PARENTHESIS_CLOSE -> null;
                    case ONE_PARM_FUNCTION -> ",left=" + left;
                }
                + '}';
    }
}

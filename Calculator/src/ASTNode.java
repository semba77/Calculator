public class ASTNode {

    Tokenizer.Token type;
    int value;
    ASTNode left;
    ASTNode right;

    public ASTNode(Tokenizer.Token type, ASTNode left, ASTNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public ASTNode(Tokenizer.Token type, Integer number) {
        this.type = type;
        this.value = number;
        this.left = null;
        this.right = null;
    }

    public int eval() {
        switch (type) {
            case TOKEN_NUMBER:
                return value;
            case TOKEN_PLUS:
                return left.eval() + right.eval();
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                "type=" + type +
                ", value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

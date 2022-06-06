public class ASTNode {

    enum NodeType {
        NUMERIC_LITERAL,
        ADDITIVE_OPERATOR,
        MULTIPLICATION_OPERATOR
    }

    NodeType type;
    int value;
    ASTNode left;
    ASTNode right;

    public ASTNode(NodeType type, ASTNode left, ASTNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public ASTNode(NodeType type, Integer number) {
        this.type = type;
        this.value = number;
        this.left = null;
        this.right = null;
    }

    public int eval() {
        switch (type) {
            case NUMERIC_LITERAL:
                return value;
            case ADDITIVE_OPERATOR:
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

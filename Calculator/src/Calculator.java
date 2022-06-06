public class Calculator {
    public static void main(String[] args) {
        Parser parser = new Parser("  42 + 42");
        ASTNode root = parser.parse();
        System.out.println(root);
        System.out.println(root.eval());
    }
}

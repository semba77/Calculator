public class Calculator {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("sin(3.1415) + log 2");
        ASTNode root = parser.parse();
        System.out.println(root);
        System.out.println(root.eval());
    }
}

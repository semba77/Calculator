public class Calculator {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser("nsn(9,12) + sin(2*3) * 3");
        ASTNode root = parser.parse();
        System.out.println(root);
        System.out.println(root.eval());
    }
}

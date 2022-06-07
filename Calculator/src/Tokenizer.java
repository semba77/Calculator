import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {


    String buffer;
    int cursor;
    private HashMap<Token.Type, String> tokenRegExpHashMap;

    public Tokenizer(String input) {
        init(input);
        tokenRegExpHashMap = new HashMap<Token.Type, String>();
        tokenRegExpHashMap.put(Token.Type.WHITESPACE, "^\\s+");
        tokenRegExpHashMap.put(Token.Type.NUMERIC_LITERAL, "^\\-?\\d+(\\.\\d+)?");
        tokenRegExpHashMap.put(Token.Type.ADDITIVE_OPERATOR, "^[\\+|\\-]");
        tokenRegExpHashMap.put(Token.Type.MULTIPLICATIVE_OPERATOR, "^[\\*|\\/]");
        tokenRegExpHashMap.put(Token.Type.PARENTHESIS_OPEN, "^\\(");
        tokenRegExpHashMap.put(Token.Type.PARENTHESIS_CLOSE, "^\\)");
        tokenRegExpHashMap.put(Token.Type.ONE_PARM_FUNCTION, "^(sin|cos|tan|log|exp|sqrt)");
        tokenRegExpHashMap.put(Token.Type.TWO_PARM_FUNCTION, "^(min|max|nsd|nsn)");
        tokenRegExpHashMap.put(Token.Type.COMMA, "^\\,");
    }

    private void init(String string) {
        this.buffer = string;
        this.cursor = 0;
    }

    private boolean hasMoreTokens() {
        return cursor < buffer.length();
    }


    public Token getNextToken() throws Parser.ParserSyntaxException {
        if (!hasMoreTokens())
            return null;
        String rest = buffer.substring(cursor);
        for (Token.Type tokenType: tokenRegExpHashMap.keySet()) {
            Pattern pattern = Pattern.compile(tokenRegExpHashMap.get(tokenType));
            Matcher matcher = pattern.matcher(rest);
            if (matcher.find()) {
                cursor += matcher.group().length();
                return switch (tokenType) {
                    case WHITESPACE -> getNextToken();
                    case COMMA -> new Token(Token.Type.COMMA);
                    case NUMERIC_LITERAL -> new Token(tokenType, Double.parseDouble(matcher.group()));
                    case ADDITIVE_OPERATOR,MULTIPLICATIVE_OPERATOR -> new Token(tokenType, matcher.group().charAt(0));
                    case PARENTHESIS_OPEN, PARENTHESIS_CLOSE -> new Token(tokenType);
                    case ONE_PARM_FUNCTION,TWO_PARM_FUNCTION -> new Token(tokenType, matcher.group());
                    default -> throw new Parser.ParserSyntaxException("Not yet implemented for token '" + rest.charAt(0) + "' in input: " + rest);
                };
            }
        }
        throw new Parser.ParserSyntaxException("Unknown token '" + rest.charAt(0) + "' in input: " + rest);
    }




}

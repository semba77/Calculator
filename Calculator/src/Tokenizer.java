import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Tokenizer {
    enum Token {
        TOKEN_UNKNOWN,
        TOKEN_WHITESPACE,
        TOKEN_NUMBER,
        TOKEN_PLUS,
        TOKEN_MINUS
    }

    String buffer;
    int cursor;
    private HashMap<Token, String> tokenRegExpHashMap;

    public Tokenizer(String input) {
        this.buffer = input;
        this.cursor = 0;
        tokenRegExpHashMap = new HashMap<Token, String>();
        tokenRegExpHashMap.put(Token.TOKEN_WHITESPACE, "^\\s+");
        tokenRegExpHashMap.put(Token.TOKEN_NUMBER, "^\\d+");
        tokenRegExpHashMap.put(Token.TOKEN_PLUS, "^\\+");
    }

    boolean hasMoreTokens() {
        return cursor < buffer.length();
    }

    ASTNode getNextToken() throws Parser.ParserSyntaxError {
        if (!hasMoreTokens())
            return null;
        String rest = buffer.substring(cursor);
        for (Token tokenType: tokenRegExpHashMap.keySet()) {
            Pattern pattern = Pattern.compile(tokenRegExpHashMap.get(tokenType));
            Matcher matcher = pattern.matcher(rest);
            if (matcher.find()) {
                cursor += matcher.group().length();
                switch (tokenType) {
                    case TOKEN_WHITESPACE:
                        return getNextToken();
                    case TOKEN_NUMBER:
                        return new ASTNode(tokenType, Integer.parseInt(matcher.group()));
                    case TOKEN_PLUS:
                        return new ASTNode(tokenType, null, null);
                    default:
                        throw new Parser.ParserSyntaxError("Not yet implemented for " + rest);
                }
            }
        }
        throw new Parser.ParserSyntaxError("Unknown string in input: " + rest);
    }




}

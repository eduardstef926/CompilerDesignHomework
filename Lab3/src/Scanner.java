import Lab12.HashTable;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Scanner {
    private final String codeProgram;
    private final List<String> tokenList;
    private Lab12.HashTable symbolTable;
    private List<Pair<String, Integer>> pif;
    private int currentPosition; // the current index in the string of the codeProgram
    private int currentLine; // our current line of the program

    public Scanner(String program, List<String> tokenList) {
        this.codeProgram = program;
        this.tokenList = tokenList;
        this.symbolTable = new HashTable(1003);
        this.pif = new ArrayList<>();
        this.currentPosition = 0;
        this.currentLine = 1;
    }

    /***
     * This function will skip spaces and comments
     * @return: it will return true or false if we have one
     */
    private boolean skipSpacesAndComments() {
        boolean changed = false;
        while (currentPosition < codeProgram.length() && Character.isWhitespace(codeProgram.charAt(currentPosition))) {
            if (codeProgram.charAt(currentPosition) == '\n') {
                currentLine++;
                changed = true;
            }
            currentPosition++;
        }
        if (codeProgram.startsWith("//", currentPosition)) {
            changed = true;
            while (currentPosition < codeProgram.length() && codeProgram.charAt(currentPosition) != '\n') {
                currentPosition++;
            }
        }
        return changed;
    }

    /***
     * This function will check if the current character is a string using a regex.
     * @return: true or false if it is one
     */
    private boolean checkStrings()  {
        var strRegex = Pattern.compile("^\"([a-zA-Z0-9 ]*)\"");
        var matcher = strRegex.matcher(codeProgram.substring(currentPosition));
        if (matcher.find()) {
            var token = matcher.group(1);
            pif.add(new Pair(token, -2));
            currentPosition += matcher.end();
            return true;
        }
        return false;
    }

    /***
     * This function will check if the current character is an integer or not
     * @return: true or false if the character is an int
     */
    private boolean checkInt() {
        var intRegex = Pattern.compile("^([+-]?[1-9]\\d*|0)");
        var matcher = intRegex.matcher(codeProgram.substring(currentPosition));
        if (matcher.find()) {
            var token = matcher.group(1);
            if (pif.size() > 0) {
                var lastValueInThePif = pif.get(pif.size() - 1).getValue();
                if ((token.charAt(0) == '+' || token.charAt(0) == '-') && (lastValueInThePif == 0 || lastValueInThePif == -1 || lastValueInThePif == -2)) {
                    return false;
                }
            }
            pif.add(new Pair(token, -1));
            currentPosition += matcher.end();
            return true;
        }
        return false;
    }

    /***
     * This function will check if a character is a token or not
     * @return: true if the character is token
     */
    private boolean checkToken() {
        //we check if we appear in the token
        for (var token : tokenList) {
            if (codeProgram.startsWith(token, currentPosition)) {
                pif.add(new Pair(token, 0));
                currentPosition += token.length();
                return true;
            }
        }
        return false;
    }

    /***
     * This function will check if the character if a string/int/token/constant
     * @throws Exception: throw an exception if it is none of the types above
     */
    public void checkCharacters() throws Exception {
        boolean checker = false;
        if (!checkStrings() && !checkInt() && !checkToken() && !checkConstant()) {
            checker = true;
        }
        if(checker)
            throw new Exception("Invalid token on the line: " + currentLine);
    }

    /***
     * This function will check for constant value and add them to the systemTable
     * and to the pif:
     * @return: we return true if the current value if a constant or not
     */
    private boolean checkConstant() {
        var idRegex = Pattern.compile("^([a-zA-Z_][a-zA-Z0-9_]*)");
        var matcher = idRegex.matcher(codeProgram.substring(currentPosition));
        if (matcher.find()) {
            var token = matcher.group(1);
            symbolTable.add(token);
            pif.add(new Pair("identifier", symbolTable.getPosition(token)));
            currentPosition += matcher.end();
            return true;
        }
        return false;
    }

    /***
     * This is the main function of the program
     * @throws Exception: we get and exception if one of the functions throws and exception
     */
    public void run() throws Exception {
        while (currentPosition < codeProgram.length()) {
            // we skip comments and spaces
            while (true) {
                if (!skipSpacesAndComments())
                    break;
            }
            if (currentPosition == codeProgram.length())
                break;
            checkCharacters();
        }
        printToFiles();
    }

    /***
     * This function will print on to the pif and system table file
     * @throws IOException: if the system does not find the files
     */
    public void printToFiles() throws IOException {
        var writer = new FileWriter("results/ST.out");
        writer.write(symbolTable.toString());
        writer.close();
        writer = new FileWriter("results/PIF.out");
        var str = new StringBuilder();
        for (var word: pif){
            str.append("[").append(word.getKey()).append(":").append(word.getValue()).append("]").append('\n');
        }
        writer.write(str.toString());
        writer.close();
    }
}

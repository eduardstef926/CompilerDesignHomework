import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            // we use the Scanner class in java to put all the data from the file
            // into a string (stringContent is a variable)
            var fileContent = new java.util.Scanner(new File("results/Example.in"));
            var stringContent = new StringBuilder();
            while (fileContent.hasNextLine()) {
                stringContent.append(fileContent.nextLine()).append('\n');
            }
            fileContent.close();
            //we put all the tokens present into a list
            var tokensFromProgram = new java.util.Scanner(new File("results/Token.in"));
            var tokenList = new ArrayList<String>();
            while (tokensFromProgram.hasNextLine()) {
                tokenList.add(tokensFromProgram.nextLine());
            }
            // we then scan the data from the file
            var scanner = new Scanner(stringContent.toString(), tokenList);
            try {
                scanner.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Can't write output");
        }
    }
}

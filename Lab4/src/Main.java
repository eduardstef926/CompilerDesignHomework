import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FiniteAutomation fa = new FiniteAutomation("results/FA.in");
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            printMenu();
            int option = scanner.nextInt();
            if (option == 1){
                System.out.println(fa.computeData());
            } else if (option == 2){
                if(fa.checkIfDeterministic()) {
                    System.out.println("Your sequence: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String sequence = scanner2.nextLine();
                    if (fa.checkSequence(sequence))
                        System.out.println("Sequence is valid");
                    else
                        System.out.println("Invalid sequence");
                } else {
                    System.out.println("FA is not deterministic.");
                }
            } else if (option == 3) {
                runScanner();
            } else {
                done = true;
            }
        }
    }

    public static void printMenu(){
        System.out.println("1. Print data.");
        System.out.println("2. Check if sequence is accepted by DFA.");
        System.out.println("3. Run scanner.");
        System.out.println("0. Exit");
        System.out.println("command:");
    }

    private static void runScanner() {
        try {
            var fileContent = new Scanner(new File("results/Example.in"));
            var stringContent = new StringBuilder();
            while (fileContent.hasNextLine()) {
                stringContent.append(fileContent.nextLine()).append('\n');
            }
            fileContent.close();
            var tokensFromProgram = new Scanner(new File("results/Token.in"));
            var tokenList = new ArrayList<String>();
            while (tokensFromProgram.hasNextLine()) {
                tokenList.add(tokensFromProgram.nextLine());
            }
            var scanner = new MyScanner(stringContent.toString(), tokenList);
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

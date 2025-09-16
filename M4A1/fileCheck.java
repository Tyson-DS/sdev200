
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class fileCheck {
    public static void main(String[] args) throws FileNotFoundException {
        Stack<Character> Bracket = new Stack<>();
        Scanner scanner = new Scanner(new File(args[0]));
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                char C = line.charAt(i);
            if(C == '('|| C == '['||C == '{'){
                Bracket.push(C);
            }
            switch (C){
            case ')': 
            if (!Bracket.isEmpty() && Bracket.peek() == '('){
                Bracket.pop();
            }
            else {
                System.out.println("invalid brackets");
                return;
            };
            break;
            case ']': 
            if (!Bracket.isEmpty() && Bracket.peek() == '['){
                Bracket.pop();
            }
            else {
                System.out.println("invalid brackets");
                return;
            };
            break;
            case '}': 
            if (!Bracket.isEmpty() && Bracket.peek() == '{'){
                Bracket.pop();
            }
            else {
                System.out.println("invalid brackets");
                return;
            };
            break;
            }
            
        }
        }
        scanner.close();
        if (Bracket.isEmpty()) {
    System.out.println("All brackets are balanced!");
} else {
    System.out.println("Some brackets are missing!");
}
        


    }
}

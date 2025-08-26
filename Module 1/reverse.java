import java.util.Scanner;
public class reverse {

    public static int reverseal(int number)
    {
        int reversed = 0;
        while(number != 0)
        {
            int digit = number % 10;
            reversed  = reversed * 10 + digit;
            number = number/ 10;
        }
        return reversed;
    }

    public static boolean  isPalindrome(int num, int reversed)
    {
        if (num == reversed)
        {
            System.out.println("your number is a palindrome");
            return true;
        }
        System.out.println("your number is not a palidrome");
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = scanner.nextInt();
        int reversed = reverseal(num);
        System.out.println("you entered: "+ num);
        System.out.println("Your reversed number is: "+ reversed);
        isPalindrome(num, reversed);
    }
}

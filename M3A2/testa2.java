import java.math.BigInteger;
import java.util.Scanner;

public class testa2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter numerator for the first rational number: ");
        BigInteger num1 = new BigInteger(input.nextLine());
        System.out.print("Enter denominator for the first rational number: ");
        BigInteger den1 = new BigInteger(input.nextLine());
        Rational r1 = new Rational(num1, den1);

        System.out.print("Enter numerator for the first rational number: ");
        BigInteger num2 = new BigInteger(input.nextLine());
        System.out.print("Enter denominator for the first rational number: ");
        BigInteger den2 = new BigInteger(input.nextLine());
        Rational r2 = new Rational(num2, den2);

        System.out.println(r1 + " + " + r2 + " = " + r1.add(r2));
        System.out.println(r1 + " - " + r2 + " = " + r1.subtract(r2));
        System.out.println(r1 + " * " + r2 + " = " + r1.multiply(r2));
        System.out.println(r1 + " / " + r2 + " = " + r1.divide(r2));
    }
}
import java.util.Scanner;

public class testa2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

         System.out.println("Side 1: ");
        double s1 = scanner.nextDouble();
         System.out.println("Side 2: ");
        double s2 = scanner.nextDouble();
         System.out.println("Side 3: ");
        double s3 = scanner.nextDouble();
         System.out.println("Color: ");
        String color = scanner.next();
         System.out.println("Filled (ture/false): ");
        boolean filled = scanner.nextBoolean();

        Triangle triangle = new Triangle(s1, s2, s3, color, filled);
        System.out.println("Area: "+triangle.getArea());
        System.out.println("Perimeter: "+triangle.getPerimeter());
        System.out.println("color: "+triangle.getColor());
        System.out.println("Filled: "+triangle.isFilled());
        

        


    }
    
}

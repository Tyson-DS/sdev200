
import java.util.Scanner;

public class matrix {
    public static double sumMajorDiagonal(double[][] matrix)
    {
        double sum = 0;
        for(int i= 0; i<4; i++) 
        { 
            sum += matrix[i][i];
        }
        System.out.print(sum);
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a 4 by 4 matrix row by row: \n");
        double[][] matrix = new double[4][4];
        for (int i= 0; i<4; i++)
            {for(int j = 0; j<4; j++)
                {
                    matrix[i][j] = scanner.nextDouble();
                }
            }
        
        sumMajorDiagonal(matrix);
    }
}

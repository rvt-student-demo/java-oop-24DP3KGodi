package rvt;
import java.util.Scanner;

public class Chapter_100 {

    public class Program {
    
        public static void main(String[] args) {
    
            Scanner scanner = new Scanner(System.in);
    
            while (true) {
    
                System.out.print("Enter the numerator: ");
                String numeratorInput = scanner.nextLine();
    
                // pārbaude vai lietotājs grib iziet
                if (numeratorInput.length() > 0 &&
                    (numeratorInput.charAt(0) == 'q' || numeratorInput.charAt(0) == 'Q')) {
                    break;
                }
    
                int numerator;
    
                try {
                    numerator = Integer.valueOf(numeratorInput);
                } catch (Exception e) {
                    System.out.println("You entered bad data.");
                    System.out.println("Please try again.");
                    System.out.println();
                    continue;
                }
    
                System.out.print("Enter the divisor: ");
                String divisorInput = scanner.nextLine();
    
                int divisor;
    
                try {
                    divisor = Integer.valueOf(divisorInput);
                } catch (Exception e) {
                    System.out.println("You entered bad data.");
                    System.out.println("Please try again.");
                    System.out.println();
                    continue;
                }
    
                if (divisor == 0) {
                    System.out.println("You can't divide " + numerator + " by 0");
                    System.out.println();
                    continue;
                }
    
                int result = numerator / divisor;
                System.out.println(numerator + " / " + divisor + " is " + result);
                System.out.println();
            }
        }
    }
     
}

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Calcolatore calc = new Calcolatore(scan);
        double result = 0;
        String choice;
        
        EXIT:
        while (true) {
            System.out.println("\n\tMenu\n\t1. Addizione\n\t2. Sottrazione\n\t3. Moltiplicazione\n\t4. Divisione\n\t5. Potenza\n\n\t0. Exit");
            choice = scan.nextLine();
    
            switch (choice) {
                case "1":
                    result = calc.add();
                    break;
    
                case "2":
                    result = calc.sub();
                    break;
    
                case "3":
                    result = calc.mul();
                    break;
    
                case "4":
                    result = calc.div();
                    break;
    
                case "5":
                    result = calc.pow();
                    break;
                
                case "0":
                    break EXIT;
    
                default:
                    System.out.printf("\nL'input '%s' non è valido!\n", choice);
                    break;
            }
    
            System.out.printf("\nRisultato = %f\n", result);
        }
    }
}

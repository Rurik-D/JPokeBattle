import java.util.Scanner;

public class Calcolatore {
    private double n1;
    private double n2;
    private double rad;
    private int exp;
    private Scanner scan;

    public Calcolatore(Scanner scan){
        this.scan = scan;
    }


    private void setValues(){
        System.out.println("Inserire primo valore:\n");
        this.n1 = scan.nextDouble();
        System.out.println("Inserire secondo valore:\n");
        this.n2 = scan.nextDouble();
        
        scan.nextLine();
    };

    private void setPowValues(){
        System.out.println("Inserire radice:\n");
        this.rad = scan.nextDouble();
        System.out.println("Inserire esponente:\n");
        this.exp = scan.nextInt();

        scan.nextLine();
    };

    public double add() {
        setValues();
        return this.n1 + this.n2;
    }

    public double sub() {
        setValues();
        return this.n1 - this.n2;
    }

    public double mul() {
        setValues();
        return this.n1 * this.n2;
    }

    public double div() {
        setValues();
        return this.n1 / this.n2;
    }

    public double pow() {
        setPowValues();
        double result = rad;
        for (int i=1; i<exp; i++) {
            result *= rad;
        }
        return result;
    }

}




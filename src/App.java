import java.math.BigInteger;
import java.util.Scanner;
import prime.Prime;

public class App {
    public static void main(String[] args) throws Exception {

        Prime prime = new Prime(0.00000000000001);

        System.out.println("Prime: " + prime.getPrime(2048));
        
        /*
        Scanner scanner = new Scanner(System.in);
        BigInteger candidate = scanner.nextBigInteger();

        if (prime.isPrime(candidate)) System.out.println(candidate.toString() + " ist eine Primzahl.");
        else System.out.println(candidate.toString() + " ist keine Primzahl.");

        scanner.close();
        */

    }
}

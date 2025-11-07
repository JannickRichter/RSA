import java.math.BigInteger;
import java.util.Scanner;
import prime.Prime;
import key.EncryptionManager;
import key.Key;

public class App {
    public static void main(String[] args) throws Exception {

        Key key = new Key(128);

        BigInteger msg = BigInteger.valueOf(143);
        BigInteger c = EncryptionManager.encrypt(msg, key.getEncrytionKeySet());

        System.out.println("Verschlüsselt: " + c);

        System.out.println("Entschlüsselt: " + EncryptionManager.decrypt(c, key.getFullKeySet()));

        /*Prime prime = new Prime(0.00000000000001);

        System.out.println("Prime: " + prime.getPrime(512));*/
        
        
        /*Scanner scanner = new Scanner(System.in);
        BigInteger candidate = scanner.nextBigInteger();

        if (prime.isPrime(candidate)) System.out.println(candidate.toString() + " ist eine Primzahl.");
        else System.out.println(candidate.toString() + " ist keine Primzahl.");

        scanner.close();
        */

    }
}

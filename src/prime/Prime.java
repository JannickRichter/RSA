package prime;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Prime {

    private double notPrimeCertainty;

    public Prime(double notPrimeCertainty) {
        this.notPrimeCertainty = notPrimeCertainty;
    }

    public Prime() {
        this.notPrimeCertainty = 0.0000001;
    }

    public boolean isPrime(BigInteger prime) {

        BigInteger candidate = prime;

        double certainty = 1;
        String bin = candidate.subtract(BigInteger.ONE).toString(2);

        System.out.println("Candidate: " + candidate.toString());
        System.out.println("Binary - 1: " + bin);

        int t = 0;
        for (int i = bin.length() - 1; i >= 0; i--) {
            if (bin.charAt(i) == '0') t++;
            else break;
        }
        if (t <= 0) return false;

        System.out.println("t: " + t);

        String uBin = bin.substring(0, bin.length() - t);
        if (uBin.isEmpty()) return false;

        BigInteger u = new BigInteger(uBin, 2);

        System.out.println("u: " + u.toString());

        BigInteger a = BigInteger.ONE;

        certainty:
        while (certainty > notPrimeCertainty && a.compareTo(candidate.subtract(BigInteger.TWO)) < 0) {

            a = a.add(BigInteger.ONE);

            System.out.println("a: " + a);

            BigInteger checkMod = a.modPow(u, candidate)
                                    .subtract(BigInteger.ONE)
                                    .mod(candidate);

            if (checkMod.equals(BigInteger.ZERO)) {
                certainty *= 0.25;

                System.out.println("Certainty: " + certainty);
            } else {

                int i = -1;

                while (i < t - 1) {

                    i += 1;

                    System.out.println("i: " + i);

                    BigInteger mod = a.modPow(BigInteger.TWO
                                        .pow(i)
                                        .multiply(u), candidate)
                                        .add(BigInteger.ONE)
                                        .mod(candidate);

                    if (mod.equals(BigInteger.ZERO)) {
                        certainty *= 0.25;
                        continue certainty;
                    }

                }

                if (i >= t - 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public BigInteger getPrime(int bits) {

        BigInteger prime = null;
        SecureRandom secureRandom = new SecureRandom();

        while (prime == null) {

            BigInteger candidate = new BigInteger(bits, secureRandom).setBit(bits - 1).setBit(0);
            if (candidate.compareTo(BigInteger.valueOf(3)) < 0) continue;

            if (isPrime(candidate)) prime = candidate;
        }

        return prime;
    }

}
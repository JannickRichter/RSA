package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeUtils {

    private double notPrimeCertainty;

    public PrimeUtils(double notPrimeCertainty) {
        this.notPrimeCertainty = notPrimeCertainty;
    }

    public PrimeUtils() {
        this.notPrimeCertainty = 0.0000001;
    }

    public boolean isPrime(BigInteger prime) {

        BigInteger candidate = prime;

        if (candidate.equals(BigInteger.TWO)) return true;
        if (candidate.compareTo(BigInteger.TWO) < 0) return false;
        if (!candidate.testBit(0)) return false;
        if (!candidate.gcd(BigInteger.valueOf(3)).equals(BigInteger.ONE)) return false;
        if (!candidate.gcd(BigInteger.valueOf(5)).equals(BigInteger.ONE)) return false;
        if (!candidate.gcd(BigInteger.valueOf(7)).equals(BigInteger.ONE)) return false;
        if (!candidate.gcd(BigInteger.valueOf(11)).equals(BigInteger.ONE)) return false;

        double certainty = 1;
        String bin = candidate.subtract(BigInteger.ONE).toString(2);

        int t = 0;
        for (int i = bin.length() - 1; i >= 0; i--) {
            if (bin.charAt(i) == '0') t++;
            else break;
        }
        if (t <= 0) return false;

        String uBin = bin.substring(0, bin.length() - t);
        if (uBin.isEmpty()) return false;

        BigInteger u = new BigInteger(uBin, 2);

        BigInteger a = BigInteger.ONE;

        certainty:
        while (certainty > notPrimeCertainty && a.compareTo(candidate.subtract(BigInteger.TWO)) < 0) {

            a = a.add(BigInteger.ONE);

            BigInteger aModPow = a.modPow(u, candidate);

            BigInteger checkMod = aModPow.subtract(BigInteger.ONE)
                                            .mod(candidate);

            if (checkMod.equals(BigInteger.ZERO)) {
                certainty *= 0.25;
            } else {

                BigInteger nMinus1 = candidate.subtract(BigInteger.ONE);
                BigInteger x = aModPow;

                if (x.equals(BigInteger.ONE) || x.equals(nMinus1)) {
                    certainty *= 0.25;
                    continue certainty;
                }

                int i = 1;
                boolean hitNm1 = false;

                while (i < t) {
                    x = x.multiply(x).mod(candidate);

                    if (x.equals(nMinus1)) {
                        hitNm1 = true;
                        break;
                    }
                    i++;
                }

                if (hitNm1) {
                    certainty *= 0.25;
                    continue certainty;
                }

                return false;
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
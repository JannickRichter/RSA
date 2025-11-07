package key;

import java.math.BigInteger;
import prime.Prime;

public class Key {

    private BigInteger publicKey = null;
    private BigInteger privateKey = null;

    public BigInteger e = BigInteger.valueOf(65537);

    public Key(int bits) {
        generateKeySet(bits);
    }
    
    public void generateKeySet(int bits) {

        Prime prime_instance = new Prime();
        int bitsPerPrime = (int) bits / 2;

        BigInteger p = prime_instance.getPrime(bitsPerPrime);

        while (this.privateKey == null) {

            BigInteger q = prime_instance.getPrime(bitsPerPrime);

            if (p.subtract(q).abs().compareTo(BigInteger.TWO.pow(bits - 1)) < 0) continue;

            BigInteger n = p.multiply(q);

            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

            this.privateKey = modularInverse(phi);
            this.publicKey = n;

        }
        
    }

    private BigInteger modularInverse(BigInteger phi) {

        BigInteger e = this.e;
        BigInteger phiCopy = phi;

        BigInteger x0 = BigInteger.ONE;
        BigInteger x1 = BigInteger.ZERO;

        BigInteger y0 = BigInteger.ZERO;
        BigInteger y1 = BigInteger.ONE;

        if (!e.gcd(phiCopy).equals(BigInteger.ONE)) return null;

        while (!phi.equals(BigInteger.ZERO)) {
            BigInteger[] qr = e.divideAndRemainder(phi);

            BigInteger q = qr[0];
            BigInteger r = qr[1];

            e = phi;
            phi = r;

            BigInteger nx = x0.subtract(q.multiply(x1));
            BigInteger ny = y0.subtract(q.multiply(y1));

            x0 = x1;
            y0 = y1;
            x1 = nx;
            y1 = ny;
        }
        // Jetzt: a = gcd(e,phi) = 1, und x0 ist das Inverse (kann negativ sein)
        if (!e.equals(BigInteger.ONE)) return null;
        
        BigInteger d = x0.mod(phiCopy);
        return d.signum() < 0 ? d.add(phiCopy) : d;
    }

}
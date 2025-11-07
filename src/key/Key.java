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
        int bitsPerPrime = bits / 2;

        while (this.privateKey == null) {

            BigInteger p = prime_instance.getPrime(bitsPerPrime);
            BigInteger q = prime_instance.getPrime(bitsPerPrime);

            System.out.println("Primzahlen:\np: " + p + "\nq: " + q);

            if (p.subtract(q).abs().bitLength() < bitsPerPrime / 2) continue;

            BigInteger n   = p.multiply(q);
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

            // gcd(e, phi) == 1 prüfen (hier schon abfangen, dann erst inverse)
            if (!e.gcd(phi).equals(BigInteger.ONE)) continue;

            BigInteger d = modularInverse(e, phi);
            if (d == null) continue; // sollte wegen gcd-Check nicht passieren

            this.privateKey = d;
            this.publicKey  = n;

            System.out.println("Exponent e: " + e);
            System.out.println("φ(n): " + phi);
            System.out.println("Public Key: " + n);
            System.out.println("Private Key: " + d);
        }
    }

    private BigInteger modularInverse(BigInteger e, BigInteger phi) {

        BigInteger a = e;
        BigInteger b = phi;

        BigInteger x0 = BigInteger.ONE;
        BigInteger x1 = BigInteger.ZERO;
        BigInteger y0 = BigInteger.ZERO;
        BigInteger y1 = BigInteger.ONE;

        if (!a.gcd(b).equals(BigInteger.ONE)) return null;

        while (!b.equals(BigInteger.ZERO)) {
            BigInteger[] qr = a.divideAndRemainder(b);
            BigInteger q = qr[0], r = qr[1];

            a = b; b = r;

            BigInteger nx = x0.subtract(q.multiply(x1));
            BigInteger ny = y0.subtract(q.multiply(y1));

            x0 = x1; y0 = y1;
            x1 = nx; y1 = ny;
        }

        if (!a.equals(BigInteger.ONE)) return null;

        BigInteger d = x0.mod(phi);
        return d.signum() < 0 ? d.add(phi) : d;
    }

    public BigInteger getPublicKey()     { return publicKey; }

    public BigInteger getPublicExp()   { return e; }

    public BigInteger getPrivateKey()  { return privateKey; }
}
package key;

import java.math.BigInteger;

import utils.PrimeUtils;

public class Key {

    private BigInteger publicKey = null;
    private BigInteger privateKey = null;

    private BigInteger e = BigInteger.valueOf(65537);

    public Key(int bits) {
        generateKeySet(bits);
    }

    public void generateKeySet(int bits) {
        PrimeUtils prime_instance = new PrimeUtils();
        int bitsPerPrime = bits / 2;

        while (this.privateKey == null) {

            BigInteger p = prime_instance.getPrime(bitsPerPrime);
            BigInteger q = prime_instance.getPrime(bitsPerPrime);

            if (p.subtract(q).abs().bitLength() < bitsPerPrime - 1) continue;

            BigInteger n   = p.multiply(q);
            BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

            if (!e.gcd(phi).equals(BigInteger.ONE)) continue;

            BigInteger d = modularInverse(e, phi);
            if (d == null) continue;

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

    public BigInteger getPublicKey() { return publicKey; }

    public BigInteger getPublicExp() { return e; }

    public BigInteger getPrivateKey() { return privateKey; }

    /*public void saveKey(String label) {

        Properties config = new Properties();

        config.setProperty("p", this.p.toString());
        config.setProperty("q", this.q.toString());
        config.setProperty("e", this.e.toString());
        config.setProperty("public", this.publicKey.toString());
        config.setProperty("private", this.privateKey.toString());

        try (OutputStream output = new FileOutputStream(label + ".properties")) {
            config.store(output, label);
        } catch (IOException e) {
            System.out.println("Save Failed : Chech if the label is already used.");
        }
    }

    public void loadKey(String label) {

        Properties config = new Properties();

        try (InputStream input = new FileInputStream(label + ".properties")) {

            config.load(input);

            this.p = new BigInteger(config.getProperty("p"));
            this.q = new BigInteger(config.getProperty("q"));
            this.e = new BigInteger(config.getProperty("e"));
            this.publicKey = new BigInteger(config.getProperty("public"));
            this.privateKey = new BigInteger(config.getProperty("private"));

        } catch (IOException e) {
            System.out.println("Load Failed : Label not found.");
        }

    }*/
}
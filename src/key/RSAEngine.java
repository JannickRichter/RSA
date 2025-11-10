package key;

import java.math.BigInteger;

public class RSAEngine {

    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger e;

    private Key key;

    public RSAEngine(Key key) {
        this.key = key;
    }

    public RSAEngine(int bits) {
        Key key = new Key(bits);
        this.key = key;

        this.publicKey = key.getPublicKey();
        this.privateKey = key.getPrivateKey();
        this.e = key.getPublicExp();
    }


    public static BigInteger encrypt(BigInteger m, Key key) {
        if (m.compareTo(BigInteger.ONE) < 0 && m.compareTo(key.getPublicKey()) > 0) return null;
        return m.modPow(key.getPublicExp(), key.getPublicKey());
    }

    public static BigInteger decrypt(BigInteger c, Key key) {
        if (c.compareTo(BigInteger.ONE) < 0 && c.compareTo(key.getPrivateKey()) > 0) return null;
        return c.modPow(key.getPrivateKey(), key.getPublicKey());
    }

    public BigInteger encrypt(BigInteger m) {
        if (m.compareTo(BigInteger.ONE) < 0 && m.compareTo(this.publicKey) > 0) return null;
        return m.modPow(this.e, this.publicKey);
    }    

    public BigInteger decrypt(BigInteger c) {
        if (c.compareTo(BigInteger.ONE) < 0 && c.compareTo(this.publicKey) > 0) return null;
        return c.modPow(this.privateKey, this.publicKey);
    }

    public Key getKey() {
        return this.key;
    }

    /*private BigInteger encrypt(BigInteger m, BigInteger publicKey, BigInteger e) {
        if (m.compareTo(BigInteger.ONE) < 0 && m.compareTo(publicKey) > 0) return null;
        return m.modPow(e, publicKey);
    }

    private BigInteger decrypt(BigInteger c, BigInteger publicKey, BigInteger privateKey) {
        if (c.compareTo(BigInteger.ONE) < 0 && c.compareTo(publicKey) > 0) return null;
        return c.modPow(privateKey, publicKey);
    }*/
}

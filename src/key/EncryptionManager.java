package key;

import java.math.BigInteger;

public class EncryptionManager {

    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger e;

    private Key key;

    public EncryptionManager(int bits) {
        Key key = new Key(bits);
        this.key = key;

        this.publicKey = key.getPublicKey();
        this.privateKey = key.getPrivateKey();
        this.e = key.getPublicExp();
    }
    
    public EncryptionManager(String label) {
        Key key = new Key(label);
        this.key = key;

        this.publicKey = key.getPublicKey();
        this.privateKey = key.getPrivateKey();
        this.e = key.getPublicExp();
    }

    public EncryptionManager(BigInteger[] keySet) {

        if (keySet.length == 2) {
            this.publicKey = keySet[0];
            this.e = keySet[1];
        } else if (keySet.length == 3) {
            this.publicKey = keySet[0];
            this.e = keySet[1];
            this.privateKey = keySet[2];
        }

    }

    public BigInteger encrypt(BigInteger msg) {
        return msg.modPow(this.e, this.publicKey);
    }

    public static BigInteger encrypt(BigInteger msg, BigInteger[] keySet) {
        return msg.modPow(keySet[1], keySet[0]);
    }

    public static BigInteger encrypt(BigInteger msg, BigInteger publicKey, BigInteger e) {
        return msg.modPow(e, publicKey);
    }

    public BigInteger decrypt(BigInteger cypher) {
        return cypher.modPow(this.privateKey, this.publicKey);
    }

    public static BigInteger decrypt(BigInteger cypher, BigInteger[] fullKeySet) {
        return cypher.modPow(fullKeySet[2], fullKeySet[0]);
    }

    public static BigInteger decrypt(BigInteger cypher, BigInteger publicKey, BigInteger privateKey) {
        return cypher.modPow(privateKey, publicKey);
    }

    public Key getKey() {
        return this.key;
    }

}

package utils;

import java.math.BigInteger;

public class ByteUtils {
    
    public static byte[] bigIntToFixedLength(BigInteger x, int length) {
        byte[] raw = x.toByteArray();

        if (raw.length > length) {
            if (raw[0] == 0x00 && raw.length == length + 1) {
                byte[] tmp = new byte[length];
                System.arraycopy(raw, 1, tmp, 0, length);
                return tmp;
            }
        }

        if (raw.length < length) {
            byte[] padded = new byte[length];

            System.arraycopy(raw, 0, padded, length - raw.length, raw.length);
            return padded;
        }

        return raw;
    }

    public static BigInteger bigIntFromUnsigned(byte[] data) {
        return new BigInteger(1, data);
    }

}

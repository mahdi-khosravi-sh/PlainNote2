package com.sky.plainnote2.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    public static String toMD5(String str) {
        final String ALGORITHM = "MD5";
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(str.getBytes());

            byte[] messageDigest = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2) {
                    h.insert(0, "0");
                }
                hexString.append(h.toString());
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

package com.example.urlapi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingDigest {
    public static String doHashDigest(String id, String secretKey) throws NoSuchAlgorithmException {

        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
        msdDigest.update(id.getBytes(StandardCharsets.UTF_8));
        msdDigest.update(secretKey.getBytes(StandardCharsets.UTF_8));

        StringBuffer hashBuffer = new StringBuffer();
        for (int i = 0; i < msdDigest.digest().length; i++) {
            String s = Integer.toHexString(0xff & msdDigest.digest()[i]);
            s = (s.length() == 1) ? "0" + s : s;
            hashBuffer.append(s);
        }

        return hashBuffer.toString();
    }
}

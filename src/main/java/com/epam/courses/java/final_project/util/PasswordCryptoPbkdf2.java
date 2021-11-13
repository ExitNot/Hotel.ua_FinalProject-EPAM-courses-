package com.epam.courses.java.final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

public class PasswordCryptoPbkdf2 {

    private static final int ITERATIONS = 3;
    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    public static String hashPwd(String pwd) {
        String hashedPwd = null;
        try {
            byte[] salt = createSalt();

            PBEKeySpec spec = new PBEKeySpec(pwd.toCharArray(), salt, ITERATIONS, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = skf.generateSecret(spec).getEncoded();
            hashedPwd = ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-256 algorithm not found", e);
        } catch (InvalidKeySpecException e) {
            log.error("Invalid key specification", e);
        }
        return hashedPwd;
    }

    public static boolean validatePwd(String inputPwd, String storedPwd) {
        int diff = -1;
        try {
            String[] parts = storedPwd.split(":");
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(inputPwd.toCharArray(),
                    salt, ITERATIONS, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            diff = hash.length ^ testHash.length;
            for(int i = 0; i < hash.length && i < testHash.length; i++)
            {
                diff |= hash[i] ^ testHash[i];
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-256 not found", e);
        } catch (InvalidKeySpecException e) {
            log.error("Specification problem during validation", e);
        }
        return diff == 0;
    }

//    Util's

    private static byte[] createSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}

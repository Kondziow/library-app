package com.demo.rest.crypto.component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Pbkdf2PasswordHash {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private static final int ITERATIONS = 10000;

    public Pbkdf2PasswordHash() {
    }

    public String generate(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            byte[] salt = this.generateSalt();
            KeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            byte[] saltPlusHash = new byte[48];
            System.arraycopy(salt, 0, saltPlusHash, 0, 16);
            System.arraycopy(hash, 0, saltPlusHash, 16, 32);

            return Base64.getEncoder().encodeToString(saltPlusHash);
        } catch (Throwable var7) {
            throw var7;
        }
    }

    public boolean verify(char[] password, String hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            byte[] saltPlusHash = Base64.getDecoder().decode(hashedPassword);
            byte[] salt = new byte[16];
            byte[] hash = new byte[32];

            System.arraycopy(saltPlusHash, 0, salt, 0, 16);
            System.arraycopy(saltPlusHash, 16, hash, 0, 32);

            KeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] computedHash = factory.generateSecret(spec).getEncoded();

            return MessageDigest.isEqual(hash, computedHash);
        } catch (Throwable var9) {
            throw var9;
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}


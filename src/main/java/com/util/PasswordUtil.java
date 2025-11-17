package com.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Generate a BCrypt hash for the given plain password
    public static String hashPassword(String plain) {
        if (plain == null) return null;
        // work factor 12 is reasonable default
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    // Verify a plain password against a stored hash.
    // Supports BCrypt (preferred) and SHA-256 hex (MySQL SHA2(...,256)) for compatibility.
    public static boolean verifyPassword(String plain, String hashed) {
        if (plain == null || hashed == null) return false;

        // BCrypt hashes start with $2a$, $2b$ or $2y$
        if (hashed.startsWith("$2a$") || hashed.startsWith("$2b$") || hashed.startsWith("$2y$")) {
            try {
                return BCrypt.checkpw(plain, hashed);
            } catch (Exception ex) {
                return false;
            }
        }

        // If stored hash looks like a SHA-256 hex (64 hex chars), compare using SHA-256
        if (hashed.matches("(?i)^[0-9a-f]{64}$")) {
            String sha = sha256Hex(plain);
            return sha != null && sha.equalsIgnoreCase(hashed);
        }

        // Fallback: direct equals (not recommended)
        return plain.equals(hashed);
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }
}

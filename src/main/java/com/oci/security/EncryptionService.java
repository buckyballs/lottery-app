package com.oci.security;

/**
 * Created by maqsoodi on 1/30/2017.
 */
public interface EncryptionService {
    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}

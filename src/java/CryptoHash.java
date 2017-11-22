/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptoHash {

    private static String linked = "84TM4N";

    public static String cryptSHA256(String text) {
        String codify = text + linked;
        String hex = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Change this to UTF-16 if needed
            md.update(codify.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            hex = String.format("%064x", new BigInteger(1, digest));
            System.out.println(hex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hex;
    }

}

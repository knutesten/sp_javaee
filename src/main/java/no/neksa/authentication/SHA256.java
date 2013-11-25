package no.neksa.authentication;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class SHA256 {
    public String generatePassword(final String newPassword) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(newPassword.getBytes("UTF-8"));
            final BigInteger bigInt = new BigInteger(1, md.digest());
            return bigInt.toString(16);
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}

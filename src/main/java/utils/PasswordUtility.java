package utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtility {

    /** Authenticates a password that could be the same as the encrypted password. To authenticate
     *  these to entries, the salt also needs to be given.
     *
     *  @param attemptedPassword String containing the possible password
     *  @param encryptedPassword Encrypted byte array containing the 'real' password.
     *  @param salt              The salt that was used to encrypt the 'real' password.
     *  @return Status of authentication
     *  @throws InvalidKeySpecException When password cannot be generated.
     *  @throws NoSuchAlgorithmException When password cannot be generated.
     */
    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Encrypt the clear-text password using the same salt that was used to
        // encrypt the original password
        byte[] encryptedAttemptedPassword = createPassword(attemptedPassword, salt);

        // Authentication succeeds if encrypted password that the user entered
        // is equal to the stored hash
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    /** Creates an encrypted password, stored in an byte array for safety reasons. This password is
     *  created by the generated salt.
     *
     *  @param password An String containing an password.
     *  @param salt     The salt that needs to be used to encrypt this password.
     *  @return The encrypted password in an byte array.
     *  @throws InvalidKeySpecException When password cannot be generated.
     *  @throws NoSuchAlgorithmException When password cannot be generated.
     */
    public byte[] createPassword(String password, byte[] salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
        // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
        String algorithm = "PBKDF2WithHmacSHA1";
        // SHA-1 generates 160 bit hashes, so that's what makes sense here
        int derivedKeyLength = 160;
        // Pick an iteration count that works for you. The NIST recommends at
        // least 1,000 iterations:
        // http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
        // iOS 4.x reportedly uses 10,000:
        // http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
        int iterations = 20000;

        if(password != null) {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations,
                    derivedKeyLength);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            return secretKeyFactory.generateSecret(spec).getEncoded();
        } else {
            throw new InvalidKeySpecException();
        }
    }

    /**
     * Generates an random salt entry. The salt can therefore be used to create a safe password.
     *
     * @return Byte array containing the salt.
     * @throws NoSuchAlgorithmException When the salt cannot be created wit the default algorithm.
     */
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        // VERY important to use SecureRandom instead of just Random
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return salt;
    }
}

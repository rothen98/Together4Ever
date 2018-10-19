package services.PasswordEncryption;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Adapter for the JBCrypt library.
 */
public class JBCryptAdapter implements PasswordEncryptor {


    /**
     * Hashes the password using BCrypt.hashpw.
     * @param password Password to be hashed.
     * @param salt Salt to add to the encryption.
     * @return The hashed password.
     */
    @Override
    public String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password,salt);
    }

    /**
     * Checks the inputted password with the hashed string to see if it´s a match.
     * @param password The password to be checked.
     * @param hashedPassword The hashed password to check against.
     * @return True if they match.
     */
    @Override
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password,hashedPassword);
    }

    /**
     * Generates a salt to be used with the encryption.
     * @return The generated salt.
     */
    @Override
    public String generateSalt() {
        return BCrypt.gensalt();
    }
}

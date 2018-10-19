package services.PasswordEncryption;

/**
 * Interface for encrpyting passwords in the application. Removes the direct dependency on
 * the encryption library from the model.
 */
public interface PasswordEncryptor {
    String hashPassword(String password, String salt);
    boolean checkPassword(String password, String hashedPassword);
    String generateSalt();

}

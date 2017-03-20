package helpers.register;

/**
 * Created by johankladder on 20-3-17 (19:34)
 */
public class Password {

    public byte[] salt;

    public byte[] password;

    public Password(byte[] salt, byte[] password) {
        this.salt = salt;
        this.password = password;
    }
}

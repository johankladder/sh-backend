package helpers.register;

public class Password {

    public byte[] salt;

    public byte[] password;

    public Password(byte[] salt, byte[] password) {
        this.salt = salt;
        this.password = password;
    }
}

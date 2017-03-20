package helpers.register;

import helpers.DataField;

/**
 * Created by johankladder on 20-3-17 (19:34)
 */
public class Password {

    @DataField
    public byte[] salt;

    @DataField
    public byte[] password;

    public Password(byte[] salt, byte[] password) {
        this.salt = salt;
        this.password = password;
    }
}

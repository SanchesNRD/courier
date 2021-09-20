package by.epam.courierexchange.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {
    private static final String salt = "$2a$10$RQzI/sqn2tQTud9DKslSJOe";

    public static String encode(final String password){

        return BCrypt.hashpw(password, salt);
    }

    public static boolean matches(final String plainPass, final String hashedPass){
        return plainPass.equals(hashedPass);
    }

    private PasswordEncryption(){}
}

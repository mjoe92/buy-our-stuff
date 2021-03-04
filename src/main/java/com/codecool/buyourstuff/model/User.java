package com.codecool.buyourstuff.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@Setter
public class User extends BaseModel {

    private final int MINLENGTH = 5;
    private final int MAXLENGTH = 30;
    private final String ILLEGAL_CHARS = "'\"\\()[]{}&|?!-;~$<>*%_";

    private final String name;
    private final String password;
    private int cartId;

    public User(String name, String password) {
        if (!isCreationValid(name)) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (!isCreationValid(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        this.name = name;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean isCreationValid(String param) {
        if (param.length() < MINLENGTH) {return false;}
        if (param.length() > MAXLENGTH) {return false;}
        if (containsAny(param, ILLEGAL_CHARS)) {return false;}

        return true;
    }

    private static boolean containsAny(String compareMe, String to) {
        return StringUtils.containsAny(compareMe, to);
    }

}

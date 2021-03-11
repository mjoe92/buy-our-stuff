package com.codecool.buyourstuff.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

@Getter
@Setter
public class User extends BaseModel {

    private static final int MINLENGTH = 5;
    private static final int MAXLENGTH = 30;
    private static final String ILLEGAL_CHARS = "'\"\\()[]{}&|?!-;~$<>*%_";

    @NonNull
    private final String name;
    @NonNull
    private final String password;
    private int cartId;

    public User(@NonNull String name, @NonNull String password) {
        if (!isCreationValid(name)) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (!isCreationValid(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        this.name = name;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public User(@NonNull @JsonProperty("name") String name, @NonNull @JsonProperty("password") String password, @JsonProperty("json") boolean json) {
        this.name = name;
        this.password = password;
    }

    public boolean isJSON() {
        return true;
    }

    private boolean isCreationValid(String str) {
        if (str.length() < MINLENGTH) {return false;}
        if (str.length() > MAXLENGTH) {return false;}
        if (containsAny(str, ILLEGAL_CHARS)) {return false;}

        return true;
    }

    private static boolean containsAny(String compareMe, String to) {
        return StringUtils.containsAny(compareMe, to);
    }

}

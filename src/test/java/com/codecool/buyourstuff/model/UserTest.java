package com.codecool.buyourstuff.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private final String NAME_DEFAULT = "BE2team1";
    private final String PW_DEFAULT = "";

    @Test
    void usernameLengthBelowMinimumThrows() {
        assertThrows(IllegalArgumentException.class, () -> new User("foob", PW_DEFAULT));
    }

    @Test
    void usernameLengthAboveMaximumThrows() {
        assertThrows(IllegalArgumentException.class, () -> new User("Pekwachnamaykoskwaskwaypinwanik", PW_DEFAULT));
    }

    @Test
    void usernameContainsInvalidChar() {
        assertThrows(IllegalArgumentException.class, () -> new User("I'm invalid", PW_DEFAULT));
    }

    @Test
    void passwordLengthBelowMinimumThrows() {
        assertThrows(IllegalArgumentException.class, () -> new User(NAME_DEFAULT,"foob"));
    }

    @Test
    void passwordLengthAboveMaximumThrows() {
        assertThrows(IllegalArgumentException.class, () -> new User(NAME_DEFAULT,"Pekwachnamaykoskwaskwaypinwanik"));
    }

    @Test
    void passwordContainsInvalidChar() {
        assertThrows(IllegalArgumentException.class, () -> new User(NAME_DEFAULT,"I'm invalid"));
    }

}
